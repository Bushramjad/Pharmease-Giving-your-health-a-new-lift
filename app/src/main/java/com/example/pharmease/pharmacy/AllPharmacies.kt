package com.example.pharmease.pharmacy

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.pharmease.OnItemClickListener
import com.example.pharmease.R
import com.example.pharmease.addOnItemClickListener
import com.example.pharmease.api.EndPoints
//import com.example.pharmease.pharmacy.AllPharmaciesAdaptor
import com.facebook.FacebookSdk.getApplicationContext
import kotlinx.android.synthetic.main.all_pharmacies.*
import org.json.JSONException
import org.json.JSONObject


class AllPharmacies : Fragment() {


    //private var PharmaciesList: ArrayList<AllPharmaciesModel>? = null
    private var PharmaciesList: MutableList<AllPharmaciesModel>? = null


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


        val stringRequest = StringRequest(Request.Method.POST, EndPoints.URL_GET_PHARMACIES,
            Response.Listener<String> { s ->
                try {
                    val obj = JSONObject(s)
                    if (!obj.getBoolean("error")) {
                        val array = obj.getJSONArray("pharmacies")

                        for (i in 0 until array.length()) {
                            val objectPharmacy = array.getJSONObject(i)
                            val pharmacy = AllPharmaciesModel(
                                objectPharmacy.getString("name"),
                                objectPharmacy.getString("address"),
                                objectPharmacy.getString("sector"),
                                objectPharmacy.getString("openinghours")
                            )
                            PharmaciesList?.add(pharmacy)

                            val adapter = AllPharmaciesAdaptor(requireActivity(), PharmaciesList!!)
                            recycler_view.adapter = adapter

                        }
                    } else {
                        Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_LONG).show()
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }, Response.ErrorListener { volleyError -> Toast.makeText(activity, volleyError.message, Toast.LENGTH_LONG).show() })

        val requestQueue = Volley.newRequestQueue(this.requireActivity())
        requestQueue.add<String>(stringRequest)
    }

}
