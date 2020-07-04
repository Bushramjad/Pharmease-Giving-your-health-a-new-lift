package com.example.pharmease.order

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.pharmease.R
import com.example.pharmease.cart.ShoppingCart
import com.example.pharmease.pharmacy.AllPharmaciesAdaptor
import com.example.pharmease.pharmacy.AllPharmaciesModel
import com.example.pharmease.pharmacy.MedicineDataClass
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import kotlinx.android.synthetic.main.all_pharmacies.*
import kotlinx.android.synthetic.main.verify_details.*
import java.io.IOException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*


class VerifyDetailsFragment : Fragment() {

    private val PICK_IMAGE_REQUEST = 71
    private var filePath: Uri? = null
    private var firebaseStore: FirebaseStorage? = null
    private var storageReference: StorageReference? = null
    private lateinit var mDatabaseReference: DatabaseReference
    private var mDatabase: FirebaseDatabase? = null
    val cart = ShoppingCart.getCart()

    var medicines = ArrayList<MedicineDataClass>()

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

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressBar5.visibility = View.GONE


        firebaseStore = FirebaseStorage.getInstance()
        storageReference = FirebaseStorage.getInstance().reference

        mDatabase = FirebaseDatabase.getInstance()
        mDatabaseReference = mDatabase!!.reference

        mDatabaseReference = mDatabase!!.reference.child("pharmacies")


        upload.setOnClickListener {
            launchGallery()
        }

        confirm.setOnClickListener {
            uploadImage()
        }


        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)


        val name = name.text.toString().trim()
        val phone = phone.text.toString().trim()
        val address = address.text.toString().trim()
        val amount : String = ""
        val date : String = current.format(formatter)
        val status : String = "Pending"

        val orderdetails : OrderModel = OrderModel(name,status,date,amount,phone,address)

        Log.e("product", medicines.toString())
        confirm.setOnClickListener() {
            // findNavController().navigate(R.id.action_nav_firstscreen_to_nav_login)
           // Toast.makeText(activity,"Pressed", Toast.LENGTH_SHORT).show()

//            submitOrder(orderdetails)
//            startActivity(Intent(activity, Thankyou::class.java))
        }
    }

    private fun submitOrder(orderdetails : OrderModel) {

        val orderkey: String? = mDatabaseReference.push().key

        for(i in 0 until cart.size) {
            val med = MedicineDataClass()
            val medicinekey : String? = mDatabaseReference.push().key
            med.brand = cart[i].product.brand
            med.name = cart[i].product.name
            med.supplier = cart[i].product.supplier
            med.quantity = cart[i].product.quantity
            med.price = cart[i].product.price
            med.location = cart[i].product.location
            medicines.add(med)
        }

//        mDatabaseReference.child("testcustomer").child("testorder").child(orderkey!!)
//            .child("medicines").child(medicinekey!!).setValue(medicines)

        mDatabaseReference.child("-MApc9XFV3g5SDugXx5a").child("orders")
            .child(orderkey!!).setValue(orderdetails)

        val postListener = object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {

                for (i in dataSnapshot.children) {

                    val pharmacykey = i.key.toString()


                    mDatabaseReference.child(pharmacykey).child("orders")
                        .child(orderkey!!).child("medicines").setValue(medicines)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w(ContentValues.TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }
        mDatabaseReference.addValueEventListener(postListener)



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
//            if (key != null) {
//                it.id = key
//                mDatabaseReference?.child("salads")?.child(key)?.setValue(it)
//
//            }
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
