package com.example.pharmease.pharmacy

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pharmease.MedicineAdapter
import com.example.pharmease.OnItemClickListener
import com.example.pharmease.R
import com.example.pharmease.addOnItemClickListener
import com.example.pharmease.cart.ShoppingCart
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import kotlinx.android.synthetic.main.pharmacy_profile.*


/**
 * A simple [Fragment] subclass.
 */
class pharmacy_profile : Fragment() {


       // private val medicines: ArrayList<String> = ArrayList()
       private var medicines = ArrayList<MedicineDataClass>()

    private var mDatabaseReference: DatabaseReference? = null
    private var mDatabase: FirebaseDatabase? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
        ): View? {

            val root = inflater.inflate(R.layout.pharmacy_profile, container, false)
            return root
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
            //addMedicine()


          //  val tv = view.findViewById<TextView>(R.id.textViewAmount)

            val pharmacy = arguments?.getString("pharmacy")

            Log.i("test", pharmacy)


            mDatabase = FirebaseDatabase.getInstance()
            mDatabaseReference = mDatabase!!.reference.child("pharma")



                if (pharmacy != null) {
                getMedicines(pharmacy)
            }




        }


    fun getMedicines( pharmacy_name  : String)
    {

            val postListener = object : ValueEventListener {

                override fun onDataChange(dataSnapshot: DataSnapshot) {

                    for (i in dataSnapshot.children) {

                        val post = i.getValue<AllPharmaciesModel>()
                        post?.let {


                            Log.i("data", dataSnapshot.value.toString())


                            if (post.name == pharmacy_name) {

//                                val pharmacy = MedicineDataClass(i.name, i.price)
//                                medicines.add(pharmacy)

                            }
                        }
                    }


                    cart_size.text = ShoppingCart.getShoppingCartSize().toString()

                    medicine_list.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                    medicine_list.adapter = MedicineAdapter(medicines, requireActivity())
                    medicine_list.addOnItemClickListener(object: OnItemClickListener {
                        override fun onItemClicked(position: Int, view: View) {
                            Toast.makeText(activity, "Good to go!", Toast.LENGTH_SHORT).show()


                            //startActivity(Intent(activity, CartActivity::class.java))

                        }
                    })

                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Getting Post failed, log a message
                    Log.w(ContentValues.TAG, "loadPost:onCancelled", databaseError.toException())
                    // ...
                }
            }
            mDatabaseReference?.addValueEventListener(postListener)


    }

        fun addMedicine() {
//            medicines.add(0, MedicineDataClass("A","120"))
//            medicines.add(1, MedicineDataClass("B","125"))
//            medicines.add(2, MedicineDataClass("C","126"))
//            medicines.add(3, MedicineDataClass("D","127"))

        }
    }

