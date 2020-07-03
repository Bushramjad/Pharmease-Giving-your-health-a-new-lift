package com.example.pharmease.cart

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.pharmease.R
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import kotlinx.android.synthetic.main.cart_item.view.*
import kotlinx.android.synthetic.main.cart_item.view.price
import kotlinx.android.synthetic.main.medicine_list_item.view.*
import java.util.*

class CartAdaptar (var context: Context, var cartItems: List<Cartmodel> ) : RecyclerView.Adapter<CartAdaptar.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(
                context
            ).inflate(R.layout.cart_item, parent, false)
        )
    }

    override fun getItemCount(): Int = cartItems.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bindItem(cartItems[position])

    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bindItem(cartItem: Cartmodel) {

            itemView.pname.text = "Pharmacy name"
            itemView.mname.text = cartItem.product.name
            itemView.price.text = "$${cartItem.product.price}"
            itemView.quantity.text = cartItem.quantity.toString()

        }
    }
}