package com.example.pharmease.cart

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pharmease.R
import com.example.pharmease.pharmacy.MedicineDataClass
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

        adapter = CartAdaptar(requireActivity(), ShoppingCart.getCart())
        adapter.notifyDataSetChanged()

        shopping_cart_recyclerView.adapter = adapter

        shopping_cart_recyclerView.layoutManager = LinearLayoutManager(requireActivity())


        val totalPrice = ShoppingCart.getCart()
            .fold(0.toDouble()) { acc, cartItem -> acc + cartItem.quantity.times(cartItem.medicines.price!!.toDouble()) }


        tprice.text = "PKR ${totalPrice}"
        Log.e("send", totalPrice.toString())


        val pharmacy : Unit =  ShoppingCart.getCart().forEach { cartItem ->

            pharmacyArray.add(cartItem.medicines.pharmacy)

        }

//        Log.e("send", pharmacyArray.toString())

        val bundle = bundleOf("amount" to totalPrice.toString(), "pharmacy" to pharmacyArray)

        checkout.setOnClickListener() {
            findNavController().navigate(R.id.action_nav_cart_to_nav_verify_details, bundle)
        }

    }

    companion object{
        val pharmacyArray : ArrayList<String> = ArrayList()
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        //super.onPrepareOptionsMenu(menu)
        menu.clear();
    }

}
