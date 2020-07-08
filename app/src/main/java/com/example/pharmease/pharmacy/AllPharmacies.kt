package com.example.pharmease.pharmacy


import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pharmease.OnItemClickListener
import com.example.pharmease.R
import com.example.pharmease.addOnItemClickListener
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import kotlinx.android.synthetic.main.all_pharmacies.*


class AllPharmacies() : Fragment() {


    var PharmaciesList = ArrayList<AllPharmaciesModel>()
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

        mDatabase = FirebaseDatabase.getInstance()
        mDatabaseReference = mDatabase!!.reference.child("pharmacies")

        recycler_view.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)

        loadPharmacies()

        recycler_view.addOnItemClickListener(object: OnItemClickListener {
            override fun onItemClicked(position: Int, view: View) {


                val pos : AllPharmaciesModel = PharmaciesList[position]
                val key = pos.key

//                Log.e("key", key)

                val bundle = bundleOf("key" to key)

                requireActivity().findNavController(R.id.nav_host_fragment).navigate(R.id.action_nav_pharmacies_to_nav_pharmacy_profile, bundle)
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
//                    val post = i.getValue<AllPharmaciesModel>()
                    val post = i.getValue<AllPharmaciesModel>()
//                    Log.e("list", post.toString())

                    post?.let {
                        val pharm = AllPharmaciesModel(i.key.toString(),post.hours, post.address, post.name)

                        PharmaciesList.add(pharm)

                    }
                }
                val adapter = AllPharmaciesAdaptor(requireActivity(), PharmaciesList)
                recycler_view.adapter = adapter
                progressBar7.visibility = View.GONE

            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }
        mDatabaseReference?.addValueEventListener(postListener)
    }

}