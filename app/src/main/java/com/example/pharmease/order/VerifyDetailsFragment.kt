package com.example.pharmease.order

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.example.pharmease.Drawer
import com.example.pharmease.R
import com.example.pharmease.cart.CartFragment
import com.example.pharmease.cart.ShoppingCart
import com.example.pharmease.pharmacy.AllPharmaciesModel
import com.example.pharmease.pharmacy.MedicineDataClass
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.database.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import kotlinx.android.synthetic.main.invoice.view.*
import kotlinx.android.synthetic.main.verify_details.*
import java.io.IOException
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*
import kotlin.collections.ArrayList


class VerifyDetailsFragment : Fragment()  {


    private val PICK_IMAGE_REQUEST = 71
    private var filePath: Uri? = null
    private var firebaseStore: FirebaseStorage? = null
    private var storageReference: StorageReference? = null
    private lateinit var mDatabaseReference: DatabaseReference
    private var mDatabase: FirebaseDatabase? = null
    val cart = ShoppingCart.getCart()

    var pharmacy = CartFragment.pharmacyArray

    var medicines = ArrayList<MedicineDataClass>()

    private var lat : Double? = null
    private var lng : Double? = null


    private lateinit var fusedLocationClient: FusedLocationProviderClient


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
//        mDatabaseReference = mDatabase!!.reference.child("orders")

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this.requireActivity())
        if (ActivityCompat.checkSelfPermission(
                this.requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this.requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            return
        }
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location : Location? ->
                // Got last known location. In some rare situations this can be null.
                lat = location?.latitude
                lng = location?.longitude

            }

        val tname: TextInputLayout = tilname
        val tphone: TextInputLayout = tilphone
        val taddress: TextInputLayout = tilAddress

        val tn :TextInputEditText = name
        val tp :TextInputEditText = phone
        val ta :TextInputEditText = address

        upload.setOnClickListener {
            launchGallery()
        }

        val watch1: TextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable) {
                    tname.error = null
            }
        }
        tn.addTextChangedListener(watch1)

        val watch2: TextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable) {
                tphone.error = null
            }
        }
        tp.addTextChangedListener(watch2)

        val watch3: TextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable) {
                taddress.error = null
            }
        }
        ta.addTextChangedListener(watch3)


        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)

        val amount = arguments?.getString("amount")
//        pharmacy  = arguments?.getStringArray("pharmacy")

        Log.e("pharmacyverify" , pharmacy[0].toString())

        textView26.text = amount

        confirm.setOnClickListener() {

            val name = tn.text.toString().trim()
            val phone = tp.text.toString().trim()
            val address = ta.text.toString().trim()
            val amount : String = amount!!
            val date : String = current.format(formatter)
            val status : String = "Pending"


            var count = 0

                if(tn.text!!.isEmpty()){
                    tname.error = "You need to enter your Name"
                    count++
                }
                if(tp.text!!.isEmpty()){
                    tphone.error = "You need to enter your Phone Number"
                    count++
                }
                if(ta.text!!.isEmpty()) {
                    taddress.error = "You need to enter your Address"
                    count++
                }

                if(count == 0){

                    val orderdetails: OrderModel = OrderModel(name, status, date, amount, phone, address, this.lat!!, this.lng!!)

                    submitOrder(orderdetails)
//                    uploadOrderDetails(orderdetails)
                }
            }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun submitOrder(orderdetails : OrderModel)  {

        var orderkey: String? = mDatabaseReference.push().key
        uploadImage(orderkey!!, orderdetails)

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun uploadOrderDetails(orderkey : String, orderdetails : OrderModel) {

        for (i in 0 until cart.size)
        {
            val med = MedicineDataClass()
            med.brand = cart[i].medicines.brand
            med.name = cart[i].medicines.name
            med.quantity = cart[i].medicines.quantity
            med.price = cart[i].medicines.price
            medicines.add(med)
        }


        val PharmacyListener = object :ValueEventListener
        {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                for (j in dataSnapshot.children) {

                    val pharmacykey = j.key.toString()

                    val post: AllPharmaciesModel? = j.getValue(AllPharmaciesModel::class.java)

                    for(k in pharmacy) {

                        if (post?.name.equals(k)) {


                            mDatabaseReference.child(pharmacykey).child("orders").child(orderkey).setValue(orderdetails)
                            mDatabaseReference.child(pharmacykey).child("orders").child(orderkey).child("medicines").setValue(medicines)
//                            orderkey = mDatabaseReference.push().key.toString()

//                            val postListener = object : ValueEventListener {
//
//                                override fun onDataChange(Snapshot: DataSnapshot) {
//
//                                    for (i in Snapshot.children) {
//
////                                        val pharmacykey1 = i.key.toString()
//
//                                        mDatabaseReference.child(pharmacykey).child("orders").child(orderkey).child("medicines").setValue(medicines)
//                                    }
//                                }
//
//                                override fun onCancelled(databaseError: DatabaseError) {
//                                    Log.w(
//                                        ContentValues.TAG,
//                                        "loadPost:onCancelled",
//                                        databaseError.toException()
//                                    )
//                                }
//                            }
//                            mDatabaseReference.addValueEventListener(postListener)
                        }
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w(ContentValues.TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }
        mDatabaseReference.addValueEventListener(PharmacyListener)

    }

    private fun launchGallery() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST)

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun uploadImage(orderkey: String, orderdetails : OrderModel){
        if(filePath != null){

            progressBar5.visibility = View.VISIBLE

            val ref = storageReference?.child("orders/"+orderkey)

            if(filePath != null){

                ref?.putFile(filePath!!)?.addOnSuccessListener(OnSuccessListener<UploadTask.TaskSnapshot> {

                    uploadOrderDetails(orderkey, orderdetails)

                    Toast.makeText(this.requireActivity(), "Order submitted successfully", Toast.LENGTH_SHORT).show()
                    progressBar5.visibility = View.GONE
                    orderConfirmation(orderdetails)


                })?.addOnFailureListener(OnFailureListener { e ->
                    Toast.makeText(this.requireActivity(), "Order submission Failed " + e.message, Toast.LENGTH_SHORT).show()
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
                upload.visibility = View.GONE
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

    @RequiresApi(Build.VERSION_CODES.O)
    private fun orderConfirmation(orderdetails : OrderModel)
    {
        val now = LocalTime.now()

        val messageBoxView = LayoutInflater.from(activity).inflate(R.layout.invoice, null)
        messageBoxView.date.text = orderdetails.date
        messageBoxView.address.text = orderdetails.address
        messageBoxView.amount.text = now.toString()
        messageBoxView.name.text = orderdetails.name
        messageBoxView.amount.text = orderdetails.amount

        val messageBoxBuilder = AlertDialog.Builder(activity).setView(messageBoxView)
        val  messageBoxInstance = messageBoxBuilder.show()


        messageBoxView.ok.setOnClickListener {
           Log.e("test", "test")
//            messageBoxInstance.dismiss()
            startActivity(Intent(activity, Drawer::class.java))
        }

    }


}
