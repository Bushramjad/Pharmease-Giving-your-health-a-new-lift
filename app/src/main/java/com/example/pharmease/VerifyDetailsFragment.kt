package com.example.pharmease

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import bolts.Task
import com.example.pharmease.order.OrderModel
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import kotlinx.android.synthetic.main.activity_splash_screen.*
import kotlinx.android.synthetic.main.verify_details.*
import java.io.IOException
import java.util.*


class VerifyDetailsFragment : Fragment() {

    private val PICK_IMAGE_REQUEST = 71
    private var filePath: Uri? = null
    private var firebaseStore: FirebaseStorage? = null
    private var storageReference: StorageReference? = null
    private var mDatabaseReference: DatabaseReference? = null
    private var mDatabase: FirebaseDatabase? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.verify_details, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressBar5.visibility = View.GONE


        firebaseStore = FirebaseStorage.getInstance()
        storageReference = FirebaseStorage.getInstance().reference

        mDatabase = FirebaseDatabase.getInstance()
        mDatabaseReference = mDatabase!!.reference

        val name = name.text.toString().trim()
        val phone = phone.text.toString().trim()
        val address = address.text.toString().trim()


        upload.setOnClickListener {
            launchGallery()
        }

        confirm.setOnClickListener {
            uploadImage()
        }

//        confirm.setOnClickListener() {
//            // findNavController().navigate(R.id.action_nav_firstscreen_to_nav_login)
//           // Toast.makeText(activity,"Pressed", Toast.LENGTH_SHORT).show()
//            startActivity(Intent(activity, Thankyou::class.java))
//        }

    }

    fun loadDatabase() {
        val orderhistory: List<OrderModel> = mutableListOf(
            OrderModel("Bushra", "pending","12/3/2020","PKR 300"),
            OrderModel("Kainat", "pending","12/3/2020","PKR 300"),
            OrderModel("Bushra", "pending","12/3/2020","PKR 300"),
            OrderModel("Kainat", "pending","12/3/2020","PKR 300")
        )
        orderhistory.forEach {
            val key = mDatabaseReference?.child("orders")?.push()?.key
            if (key != null) {
                it.id = key
                mDatabaseReference?.child("salads")?.child(key)?.setValue(it)

            }
        }
    }

    private fun launchGallery() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST)

    }

    private fun uploadImage(){
        if(filePath != null){

            progressBar5.visibility = View.VISIBLE

            val ref = storageReference?.child("uploads/" + UUID.randomUUID().toString())
            val uploadTask = ref?.putFile(filePath!!)

            if(filePath != null){
                val ref = storageReference?.child("uploads/" + UUID.randomUUID().toString())

                ref?.putFile(filePath!!)?.addOnSuccessListener(OnSuccessListener<UploadTask.TaskSnapshot> {
                    loadDatabase()
                    Toast.makeText(this.requireActivity(), "Image Uploaded", Toast.LENGTH_SHORT).show()
                    progressBar5.visibility = View.GONE

                })?.addOnFailureListener(OnFailureListener { e ->
                    Toast.makeText(this.requireActivity(), "Image Uploading Failed " + e.message, Toast.LENGTH_SHORT).show()
                    progressBar5.visibility = View.GONE

                })
            }else{
                Toast.makeText(this.requireActivity(), "Please Select an Image", Toast.LENGTH_SHORT).show()
                progressBar5.visibility = View.GONE

            }
        }else{
            Toast.makeText(this.requireActivity(), "Please Upload an Image", Toast.LENGTH_SHORT).show()
            progressBar5.visibility = View.GONE

        }
    }

    private fun addUploadRecordToDb(uri: String){
        val db = FirebaseFirestore.getInstance()

        val data = HashMap<String, Any>()
        data["imageUrl"] = uri

        db.collection("posts")
            .add(data)
            .addOnSuccessListener { documentReference ->
                Toast.makeText(this.requireActivity(), "Saved to DB", Toast.LENGTH_LONG).show()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this.requireActivity(), "Error saving to DB", Toast.LENGTH_LONG).show()
            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            if(data == null || data.data == null){
                return
            }

            filePath = data.data
            try {
                val bitmap = MediaStore.Images.Media.getBitmap(activity?.contentResolver, filePath)
                imageView.setImageBitmap(bitmap)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        //super.onPrepareOptionsMenu(menu)
        menu.clear();
    }
}
