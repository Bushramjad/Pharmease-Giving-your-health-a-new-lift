package com.example.pharmease.login

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.pharmease.Drawer
import com.example.pharmease.R
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.login.*
import kotlinx.android.synthetic.main.login.email
import kotlinx.android.synthetic.main.login.facebook
import kotlinx.android.synthetic.main.login.login_button
import kotlinx.android.synthetic.main.login.password
import kotlinx.android.synthetic.main.signup_1.*


class Login : Fragment() {

    private var mAuth: FirebaseAuth? = null
    lateinit var gso: GoogleSignInOptions
    lateinit var mGoogleSignInClient: GoogleSignInClient
    val RC_SIGN_IN: Int = 1
    var firebaseAuth : FirebaseAuth? = null
    var callbackManager : CallbackManager? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.login, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        progressBar3.visibility = View.GONE
        mAuth = FirebaseAuth.getInstance()

        firebaseAuth = FirebaseAuth.getInstance()
        callbackManager = CallbackManager.Factory.create()

        facebook.setOnClickListener {

            login_button.performClick()
            login_button.setReadPermissions("email")
            login_button.fragment = this;
            facebookSignin()
        }

        googlebut.setOnClickListener {
            google_button.performClick()
            signIn()
        }

        loginButton.setOnClickListener() {
            loginUser()
        }

        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("478253187970-mba4a64o9b4ttlkv9j8rqa0m74eoi88e.apps.googleusercontent.com")
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this.requireActivity(), gso)

        forget.setOnClickListener() {
            findNavController().navigate(R.id.action_nav_login_to_nav_forget_password)
        }
    }

    private fun loginUser() {

        val email = email.text.toString().trim()
        val password = password.text.toString().trim()

        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {

            progressBar3.visibility = View.VISIBLE

            mAuth!!.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(requireActivity()) { task ->

                    if (task.isSuccessful) {
                        // Sign in success, update UI with signed-in user's information
                        Log.d("activity", "signInWithEmail:success")
                        updateUI()
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.e("activity", "signInWithEmail:failure", task.exception)
                        Toast.makeText(requireActivity(), "Authentication failed.",
                            Toast.LENGTH_SHORT).show()
                    }
                }
        } else {
            Toast.makeText(activity, "Enter all details", Toast.LENGTH_SHORT).show()
        }
    }

    private fun signIn () {
        val signInIntent: Intent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    private fun facebookSignin(){

        Log.e("Signin", "Method called")

        login_button.registerCallback(callbackManager, object : FacebookCallback<LoginResult?> {

            override fun onSuccess(loginResult: LoginResult?) {
                Log.e("onFailureListener", "No")
                handleFacebookAccessToken(loginResult!!.accessToken)
            }

            override fun onCancel() {
                Log.e("onCancel", "cancel")
            }

            override fun onError(exception: FacebookException) {
                Log.e("onerror", exception.message)
            }
        })
    }

    private fun handleFacebookAccessToken(accessToken : AccessToken?){
        val credentials = FacebookAuthProvider.getCredential(accessToken!!.token)

        Log.e("onFailureListener", accessToken.toString())

        firebaseAuth!!.signInWithCredential(credentials)
            .addOnFailureListener{ e->
                Log.e("onFailureListener", e.message)
                Toast.makeText(activity, e.message, Toast.LENGTH_LONG).show()
            }
            .addOnSuccessListener { result ->
                Log.e("result", result.user?.email)
            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleResult (task)
        }else {
            Toast.makeText(activity, "Problem in execution order :(", Toast.LENGTH_LONG).show()
        }

        callbackManager!!.onActivityResult(requestCode,resultCode,data)
    }

    private fun handleResult (completedTask: Task<GoogleSignInAccount>) {
        try {
            val account: GoogleSignInAccount? = completedTask.getResult(ApiException::class.java)
            if (account != null) {
                updateUI ()
            }
        } catch (e: ApiException) {
            Toast.makeText(activity, e.toString(), Toast.LENGTH_LONG).show()
        }
    }

    private fun updateUI() {
        Toast.makeText(activity, "Logged In Successfully", Toast.LENGTH_LONG).show()
        startActivity(Intent(activity, Drawer::class.java))
        activity?.finish()
    }
}