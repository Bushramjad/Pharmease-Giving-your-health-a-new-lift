package com.example.pharmease.pharmacy

//import com.example.pharmease.pharmacy.AllPharmaciesAdaptor

import android.content.ContentValues.TAG
import android.os.Bundle
import android.os.Message
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pharmease.OnItemClickListener
import com.example.pharmease.R
import com.example.pharmease.addOnItemClickListener
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import kotlinx.android.synthetic.main.all_pharmacies.*
import org.w3c.dom.Comment


class AllPharmacies : Fragment() {


    //private var PharmaciesList: ArrayList<AllPharmaciesModel>? = null
    private var PharmaciesList: MutableList<AllPharmaciesModel>? = null

    private var mDatabaseReference: DatabaseReference? = null
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
        val root = inflater.inflate(R.layout.all_pharmacies, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //recycler_view.adapter = AllPharmaciesAdaptor(medicines, requireActivity())
        recycler_view.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)

        PharmaciesList = mutableListOf<AllPharmaciesModel>()

        mDatabase = FirebaseDatabase.getInstance()
        mDatabaseReference = mDatabase!!.reference!!.child("pharma")

        loadPharmacies()

        recycler_view.addOnItemClickListener(object: OnItemClickListener {
            override fun onItemClicked(position: Int, view: View) {
                // Toast.makeText(activity, "List view in progress!", Toast.LENGTH_SHORT).show()
                //startActivity(Intent(activity, CartActivity::class.java))
                requireActivity().findNavController(R.id.nav_host_fragment).navigate(R.id.action_nav_pharmacies_to_nav_pharmacy_profile)

            }
        })
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        //super.onPrepareOptionsMenu(menu)
        menu.clear();
    }



    private fun loadPharmacies() {

        val postListener = object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {

                for(i in dataSnapshot.children)
                {
                    val post = i.getValue<AllPharmaciesModel>()
                    post?.let {
                        val pharmacy = AllPharmaciesModel(post.name, post.location, post.hours)
                        PharmaciesList?.add(pharmacy)
                    }
                }

                val adapter = AllPharmaciesAdaptor(requireActivity(), PharmaciesList!!)
                recycler_view.adapter = adapter
            }


            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
                // ...
            }
        }
        mDatabaseReference?.addValueEventListener(postListener)

    }
}

