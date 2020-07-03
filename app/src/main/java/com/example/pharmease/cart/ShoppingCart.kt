package com.example.pharmease.cart

import android.content.Context
import android.widget.Toast
import io.paperdb.Paper

class ShoppingCart {

    companion object {

        fun addItem(cartItem: Cartmodel) {
            val cart = ShoppingCart.getCart()

            val targetItem = cart.singleOrNull { it.product == cartItem.product }

            if (targetItem == null) {
                cartItem.quantity++
                cart.add(cartItem)
            } else {

                targetItem.quantity++
            }
            saveCart(cart)
        }

        fun removeItem(cartItem: Cartmodel, context: Context) {

            val cart = getCart()
            val targetItem = cart.singleOrNull { it.product.name == cartItem.product.name }
            if (targetItem != null) {

                if (targetItem.quantity > 0) {
//                    Toast.makeText(context, "great quantity", Toast.LENGTH_SHORT).show()
                    targetItem.quantity--
                } else {
                    cart.remove(targetItem)
                }
            }

            saveCart(cart)

        }

        fun saveCart(cart: MutableList<Cartmodel>) {
            Paper.book().write("medcartn", cart)
        }

        fun getCart(): MutableList<Cartmodel> {
            return Paper.book().read("medcartn", mutableListOf())
        }

        fun getShoppingCartSize(): Int {

            var cartSize = 0
            getCart().forEach {
                cartSize += it.quantity;
            }

            return cartSize
        }
    }

}