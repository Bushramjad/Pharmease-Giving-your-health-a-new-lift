package com.example.pharmease.cart

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.pharmease.Drawer
import com.example.pharmease.R
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import kotlinx.android.synthetic.main.cart_item.view.*
import kotlinx.android.synthetic.main.cart_item.view.price
import kotlinx.android.synthetic.main.medicine_list_item.view.*
import kotlinx.android.synthetic.main.pharmacy_profile.*

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

        @SuppressLint("CheckResult")
        fun bindItem(cartItem: Cartmodel) {

            itemView.pname.text = cartItem.medicines.pharmacy
            itemView.mname.text = cartItem.medicines.name
            itemView.price.text = "PKR ${cartItem.medicines.price}"
            itemView.quantity.text = cartItem.quantity.toString()


            Observable.create(ObservableOnSubscribe<MutableList<Cartmodel>> {

                itemView.removeItem.setOnClickListener { view ->

                    ShoppingCart.removeItem(cartItem, itemView.context)
                    it.onNext(ShoppingCart.getCart())

                    Navigation.findNavController(view).navigate(R.id.action_nav_cart_to_nav_cart)

                }
            }).subscribe { cart ->

                var quantity = 0

                cart.forEach { cartItem ->
                    quantity += cartItem.quantity
                }

//                (itemView.context as Drawer).cart_size.text = quantity.toString()
//                Toast.makeText(itemView.context, "Cart size $quantity", Toast.LENGTH_SHORT).show()


            }
        }
    }
}