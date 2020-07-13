package com.example.pharmease.order

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pharmease.R
import com.example.pharmease.order.OrderFragment
import com.example.pharmease.pharmacy.AllPharmaciesModel
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import kotlinx.android.synthetic.main.order_history.*


class OrderFragment : Fragment() {

    private val medicines: ArrayList<String> = ArrayList()

    var OrdersList = ArrayList<OrderModel>()

    private var mDatabaseReference: DatabaseReference? = null
    private var pharmacyReference: DatabaseReference? = null
    private var mDatabase: FirebaseDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.order_history, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mDatabase = FirebaseDatabase.getInstance()
        mDatabaseReference = mDatabase!!.reference.child("orders")

        loadOrders()

        orderHistory.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)

    }



    private fun loadOrders() {

        mDatabaseReference?.addValueEventListener(object : ValueEventListener {



            override fun onDataChange(dataSnapshot: DataSnapshot) {

                for (i in dataSnapshot.children) {

                    pharmacyReference =
                        mDatabase!!.reference.child("pharmacies").child(i.key.toString())
                            .child("orders")
                    pharmacyReference?.addValueEventListener(object : ValueEventListener {

                        override fun onDataChange(dataSnapshot: DataSnapshot) {

                            for (j in dataSnapshot.children) {

                                val post = j.getValue<OrderModel>()

                                post?.let {
                                    val order = OrderModel(
                                        post.name,
                                        post.status,
                                        post.date,
                                        post.amount,
                                        post.phone,
                                        post.address
                                    )
                                    OrdersList.add(order)
                                }
                            }

                            val adapter = OrderAdaptar(OrdersList, requireActivity())
                            orderHistory.adapter = adapter
                            progressBar8.visibility = View.GONE
                        }

                        override fun onCancelled(databaseError: DatabaseError) {
                            Log.w(
                                ContentValues.TAG,
                                "loadPost:onCancelled",
                                databaseError.toException()
                            )
                        }
                    })
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        })
    }


    override fun onPrepareOptionsMenu(menu: Menu) {
        //super.onPrepareOptionsMenu(menu)
        menu.clear();
    }

}
