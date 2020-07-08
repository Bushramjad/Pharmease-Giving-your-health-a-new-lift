package com.example.pharmease.order

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pharmease.R
import kotlinx.android.synthetic.main.order_history_item.view.*

class OrderAdaptar (val orders : ArrayList<OrderModel>, val context: Context) : RecyclerView.Adapter<ViewHolder>() {

    // Gets the number of animals in the list
    override fun getItemCount(): Int {
        return orders.size
    }

    // Inflates the item views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.order_history_item, parent, false))
    }

    // Binds each animal in the ArrayList to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bindProduct(orders[position])
    }
}

class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {

    fun bindProduct(product: OrderModel) {
        itemView.name.text = product.address
        itemView.price.text = "PKR ${product.amount}"
        itemView.date.text = product.date
        itemView.status.text = product.status
    }

}
