package com.example.pharmease.cart

import android.graphics.PorterDuff
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pharmease.OnItemClickListener
import com.example.pharmease.R
import com.example.pharmease.addOnItemClickListener
import kotlinx.android.synthetic.main.all_pharmacies.*
import kotlinx.android.synthetic.main.cart.*


class CartFragment : Fragment() {

    private val medicines: ArrayList<String> = ArrayList()
    lateinit var adapter: CartAdaptar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.cart, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkout.setOnClickListener() {
            findNavController().navigate(R.id.action_nav_cart_to_nav_verify_details)
        }

//        shopping_cart_recyclerView.layoutManager =
//            LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
//        shopping_cart_recyclerView.adapter = CartAdaptar(requireActivity(), ShoppingCart.getCart())
//
//
//        this.shopping_cart_recyclerView.adapter?.notifyDataSetChanged()


        adapter = CartAdaptar(requireActivity(), ShoppingCart.getCart())
        adapter.notifyDataSetChanged()

        shopping_cart_recyclerView.adapter = adapter

        shopping_cart_recyclerView.layoutManager = LinearLayoutManager(requireActivity())


        val totalPrice = ShoppingCart.getCart()
            .fold(0.toDouble()) { acc, cartItem -> acc + cartItem.quantity.times(cartItem.product.price!!.toDouble()) }


        tprice.text = "$${totalPrice}"


//        var totalPrice = ShoppingCart.getCart()
//            .fold(0.toDouble()) { acc, cartItem -> acc + cartItem.quantity.times(cartItem.product.price!!.toDouble()) }

    }


    override fun onPrepareOptionsMenu(menu: Menu) {
        //super.onPrepareOptionsMenu(menu)
        menu.clear();
    }

}
