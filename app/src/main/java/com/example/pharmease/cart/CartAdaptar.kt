package com.example.pharmease.cart

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pharmease.R
import kotlinx.android.synthetic.main.cart_item.view.*

class CartAdaptar (var context: Context, var cartItems: ArrayList<String>) : RecyclerView.Adapter<CartAdaptar.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(
                context
            ).inflate(R.layout.cart_item, parent, false)
        )
    }

    override fun getItemCount(): Int = cartItems.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name?.text = cartItems[position]
        holder.company?.text = "Phillips Pharmaceuticals"
        holder.quantity?.text = "5"
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name = view.product_name
        val company = view.product_price
        val quantity = view.product_quantity


    }

}