package com.example.pharmease

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_feedback.*


class FeedbackFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_feedback, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        progressBar9.visibility = View.GONE

        sendEmailBtn.setOnClickListener {

            progressBar9.visibility = View.VISIBLE

            val recipient = "bushra.bsse3041@iiu.edu.pk"
            val message = messageEt.text.toString().trim()

            sendEmail(recipient, message)
        }

    }

    private fun sendEmail(recipient: String,message: String) {

        val mIntent = Intent(Intent.ACTION_SEND)

        mIntent.data = Uri.parse("mailto:")
        mIntent.type = "text/plain"

        mIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(recipient))
        mIntent.putExtra(Intent.EXTRA_SUBJECT, "Feedback from Pharmease Mobile App")
        mIntent.putExtra(Intent.EXTRA_TEXT, message)


        try {
            //start email intent
            startActivity(Intent.createChooser(mIntent, "Choose Email Client..."))
//            progressBar9.visibility = View.GONE
        }
        catch (e: Exception){
            Toast.makeText(activity, e.message, Toast.LENGTH_LONG).show()
        }

    }
}