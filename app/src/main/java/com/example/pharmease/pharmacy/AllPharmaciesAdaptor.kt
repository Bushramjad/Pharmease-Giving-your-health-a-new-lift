package com.example.pharmease.pharmacy

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pharmease.R
import kotlinx.android.synthetic.main.all_pharmacies_item.view.*

class AllPharmaciesAdaptor ( val context: Context, internal var pharmacies: MutableList<AllPharmaciesModel>) : RecyclerView.Adapter<ViewHolder>() {

    // Gets the number of animals in the list
    override fun getItemCount(): Int {
        Log.d("pharmacycount", pharmacies.size.toString())

        return pharmacies.size
    }

    // Inflates the item views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.all_pharmacies_item, parent, false))
    }


    // Binds each animal in the ArrayList to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pharmacy = pharmacies[position]
        holder.name?.text = pharmacy.name
        holder.location?.text = pharmacy.location
        holder.hour?.text = pharmacy.hours

    }
}


class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    // Holds the TextView that will add each animal to
    val name = view.fname
    val location = view.location
    val hour = view.hours

}
