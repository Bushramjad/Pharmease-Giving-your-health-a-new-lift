package com.example.pharmease.signup

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.example.pharmease.R
import com.example.pharmease.api.EndPoints
import com.example.pharmease.api.VolleySingleton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.signup_2.*
import org.json.JSONException
import org.json.JSONObject


private val TAG = "CreateAccountActivity"
class SignupSecond : Fragment() {

    private val SPLASH_TIME_OUT:Long = 1000 // 1 sec

    private var mDatabaseReference: DatabaseReference? = null
    private var mDatabase: FirebaseDatabase? = null
    private var mAuth: FirebaseAuth? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
        val root = inflater.inflate(R.layout.signup_2, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mDatabase = FirebaseDatabase.getInstance()
        mDatabaseReference = mDatabase!!.reference.child("Users")
        mAuth = FirebaseAuth.getInstance()

        progressBar4.visibility = View.GONE


        signup2.setOnClickListener() {

//            progressBar4.visibility = View.VISIBLE
            createNewAccount()

        }
    }


    private fun createNewAccount() {
        val email = arguments?.getString("email")
        val password = arguments?.getString("password")
        val fname = fname.text.toString().trim()
        val lname = lname.text.toString().trim()
        val phone = phone.text.toString().trim()

        progressBar4.visibility = View.VISIBLE

        mAuth!!
            .createUserWithEmailAndPassword(email!!, password!!)
            .addOnCompleteListener(requireActivity()) { task ->
                //mProgressBar!!.hide()

                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")

                    val userId = mAuth!!.currentUser!!.uid

                    //Verify Email
                    verifyEmail();

                    //update user profile information
                    val currentUserDb = mDatabaseReference!!.child(userId)
                    currentUserDb.child("fname").setValue(fname)
                    currentUserDb.child("lname").setValue(lname)
                    currentUserDb.child("phone").setValue(phone)

                    updateUserInfoAndUI()

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(activity, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }


    private fun updateUserInfoAndUI() {
        //start next activity
        findNavController().navigate(R.id.action_nav_signup2_to_nav_enable_location_notification)
    }

    private fun verifyEmail() {
        val mUser = mAuth!!.currentUser;
        mUser!!.sendEmailVerification()
            .addOnCompleteListener(requireActivity()) { task ->

                if (task.isSuccessful) {
                    Toast.makeText(requireActivity(), "Verification email sent to " + mUser.email, Toast.LENGTH_SHORT).show()
                } else {
                    Log.e(TAG, "sendEmailVerification", task.exception)
                    Toast.makeText(requireActivity(), "Failed to send verification email.", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun addUser() {

        val fname = fname.text.toString().trim()
        val lname = lname.text.toString().trim()
        val phone = phone.text.toString().trim()

        //creating volley string request
        val stringRequest = object : StringRequest(Request.Method.POST, EndPoints.CREATE_USER,
            Response.Listener<String> { response ->
                try {
                    val obj = JSONObject(response)
                    Toast.makeText(activity, obj.getString("message"), Toast.LENGTH_LONG).show()
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            object : Response.ErrorListener {
                override fun onErrorResponse(volleyError: VolleyError) {
                    Toast.makeText(activity, volleyError.message, Toast.LENGTH_LONG).show()
                }
            })
        {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params.put("email", fname)
                params.put("password", lname)
                params.put("name", phone)
                params.put("school", "0004")

                return params
            }
        }
        //adding request to queue
        VolleySingleton.instance?.addToRequestQueue(stringRequest)
    }
}