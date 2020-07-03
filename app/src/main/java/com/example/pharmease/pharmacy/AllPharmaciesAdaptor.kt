package com.example.pharmease.pharmacy

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pharmease.R
import kotlinx.android.synthetic.main.all_pharmacies_item.view.*

class AllPharmaciesAdaptor ( val context: Context, var pharmacies: ArrayList<AllPharmaciesModel>) : RecyclerView.Adapter<ViewHolder>() {



    override fun getItemCount(): Int {
        return pharmacies.size
    }

    // Inflates the item views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.all_pharmacies_item, parent, false))
    }


    // Binds each animal in the ArrayList to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bindProduct(pharmacies[position])

    }
}

class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {

    fun bindProduct(product: AllPharmaciesModel) {

        itemView.fname.text = product.name
        itemView.location.text = product.address
        itemView.hours.text = product.hours
    }

}
