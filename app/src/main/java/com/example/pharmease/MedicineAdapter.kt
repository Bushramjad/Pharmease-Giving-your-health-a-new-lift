package com.example.pharmease

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.pharmease.cart.Cartmodel
import com.example.pharmease.cart.ShoppingCart
import com.example.pharmease.pharmacy.MedicineDataClass
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import kotlinx.android.synthetic.main.medicine_list_item.view.*
import kotlinx.android.synthetic.main.pharmacy_profile.*


class MedicineAdapter (var products: List<MedicineDataClass>, val context: Context) : RecyclerView.Adapter<ViewHolder>() {

    // Gets the number of animals in the list
    override fun getItemCount(): Int {
        return products.size
    }

    // Inflates the item views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.medicine_list_item, parent, false))
    }


    // Binds each animal in the ArrayList to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bindProduct(products[position])

    }
}

class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    // Holds the TextView that will add each animal to
//    val medicineType = view.date


    @SuppressLint("CheckResult")
    fun bindProduct(product: MedicineDataClass) {

        //itemView.name.text = product.toString()
        itemView.date.text = product.name
        itemView.name.text = product.brand
        itemView.price.text = "$${product.price.toString()}"

        Observable.create(ObservableOnSubscribe<MutableList<Cartmodel>> {

            itemView.addtocart.setOnClickListener { view ->

                val item = Cartmodel(product)
                ShoppingCart.addItem(item)
                //notify users
//                Snackbar.make(
//                    (itemView.context as MainActivity).coordinator,
//                    "${product.toString()} added to your cart",
//                    Snackbar.LENGTH_LONG
//                ).show()

                it.onNext(ShoppingCart.getCart())
            }

            itemView.removeitem.setOnClickListener { view ->

                val item = Cartmodel(product)
                ShoppingCart.removeItem(item, itemView.context)

//                Snackbar.make(
//                    (itemView.context as MainActivity).coordinator,
//                    "${product.toString()} removed from your cart",
//                    Snackbar.LENGTH_LONG
//                ).show()
                it.onNext(ShoppingCart.getCart())

            }
        }).subscribe { cart ->

            var quantity = 0

            cart.forEach { cartItem ->
                quantity += cartItem.quantity
            }

            (itemView.context as Drawer).cart_size.text = quantity.toString()
            Toast.makeText(itemView.context, "Cart size $quantity", Toast.LENGTH_SHORT).show()

        }
    }
}
