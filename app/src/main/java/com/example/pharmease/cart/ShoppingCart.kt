package com.example.pharmease.cart

import android.content.Context
import io.paperdb.Paper

class ShoppingCart {

    companion object {

        fun addItem(cartItem: Cartmodel) {
            val cart = ShoppingCart.getCart()

            val targetItem = cart.singleOrNull { it.medicines.name == cartItem.medicines.name }

            if (targetItem == null) {
                cartItem.quantity++
                cart.add(cartItem)
            } else {

                targetItem.quantity++
            }
            ShoppingCart.saveCart(cart)

        }

        fun removeItem(cartItem: Cartmodel, context: Context) {

            val cart = ShoppingCart.getCart()

            val targetItem = cart.singleOrNull { it.medicines.name == cartItem.medicines.name }

            if (targetItem != null) {

                if (targetItem.quantity > 0) {

//                    Toast.makeText(context, "great quantity", Toast.LENGTH_SHORT).show()
                    targetItem.quantity--
                } else {
                    cart.remove(targetItem)
                }

            }

            ShoppingCart.saveCart(cart)

        }

        fun saveCart(cart: MutableList<Cartmodel>) {
            Paper.book().write("tcart", cart)
        }

        fun getCart(): MutableList<Cartmodel> {
            return Paper.book().read("tcart", mutableListOf())
        }

        fun getShoppingCartSize(): Int {

            var cartSize = 0
            ShoppingCart.getCart().forEach {
                cartSize += it.quantity;
            }

            return cartSize
        }
    }

}