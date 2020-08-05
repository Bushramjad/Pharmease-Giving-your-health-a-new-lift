package com.example.pharmease.signup


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.pharmease.Drawer
import com.example.pharmease.R
import com.facebook.CallbackManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.signup_1.*
import java.util.regex.Matcher
import java.util.regex.Pattern


class SignupFirst : Fragment() {

    lateinit var gso: GoogleSignInOptions
    lateinit var mGoogleSignInClient: GoogleSignInClient
    val RC_SIGN_IN: Int = 1
    var firebaseAuth : FirebaseAuth? = null
    var callbackManager : CallbackManager? = null
    private lateinit var auth: FirebaseAuth


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()

        val root = inflater.inflate(R.layout.signup_1, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        firebaseAuth = FirebaseAuth.getInstance()
        callbackManager = CallbackManager.Factory.create()


        var count = 0
        next.setOnClickListener() {

            val email = email.text.toString().trim()
            val password = password.text.toString().trim()
            val cpassword = cpassword.text.toString().trim()


            if(!isEmailValid(email)){
                showSnackBar("Invalid Email address")
                count++
            }
            else if(password != cpassword){
                showSnackBar("Please enter same password in both fields")
                count++
            }
            else{

                if (it.findNavController().currentDestination?.id == R.id.nav_signup1) {
                    val bundle = bundleOf("email" to email, "password" to password)
                    it.findNavController().navigate(R.id.action_nav_signup1_to_nav_signup2, bundle)
                }
            }

        }

        google.setOnClickListener {
            sign_in_button.performClick()
            signIn()
        }

        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("478253187970-mba4a64o9b4ttlkv9j8rqa0m74eoi88e.apps.googleusercontent.com")
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this.requireActivity(), gso)


    }

    private fun signIn () {
        val signInIntent: Intent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        firebaseAuth?.signInWithCredential(credential)
            ?.addOnCompleteListener(this.requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    // ...
                    Snackbar.make(requireView(), "Authentication Failed.", Snackbar.LENGTH_SHORT).show()
                    updateUI(null)
                }

                // ...
            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)

            val account = task.getResult(ApiException::class.java)!!
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
//                updateUI (account)

                firebaseAuthWithGoogle(account.idToken!!)

            }
        } catch (e: ApiException) {
            Toast.makeText(activity, e.toString(), Toast.LENGTH_LONG).show()
        }
    }

    private fun updateUI (account: FirebaseUser?) {
//        Log.e("success", account.displayName)
        Toast.makeText(activity, "Logged In Successfully", Toast.LENGTH_LONG).show()

        startActivity(Intent(activity, Drawer::class.java))

    }

    private fun showSnackBar(msg : String) {
        val snackBar = Snackbar.make(requireActivity().findViewById(android.R.id.content),
            msg, Snackbar.LENGTH_LONG
        )
        snackBar.show()
    }


//    fun isEmailValid(email: String): Boolean {
//        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
//    }

    fun isEmailValid(email: String): Boolean {
        return Pattern.compile(
            "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]|[\\w-]{2,}))@"
                    + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                    + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                    + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                    + "[0-9]{1,2}|25[0-5]|2[0-4][0-9]))|"
                    + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$"
        ).matcher(email).matches()
    }


}
