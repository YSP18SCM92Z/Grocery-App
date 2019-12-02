package com.rjt.groceryapp.fragments


import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.GsonBuilder

import com.rjt.groceryapp.R
import com.rjt.groceryapp.adapters.ProductAdapter
import com.rjt.groceryapp.models.Product
import com.rjt.groceryapp.models.ProductList
import kotlinx.android.synthetic.main.fragment_product_big_layout.*
import kotlinx.android.synthetic.main.fragment_product_big_layout.view.*

private const val ARG_PARAM1 = "param1"

/**
 * A simple [Fragment] subclass.
 */
class ProductFragment : Fragment() {

    // TODO: Rename and change types of parameters
    private var subCategoryId: String? = null

    private var listener: OnFragmentInteractionListener? = null

    private lateinit var adapter: ProductAdapter

    private var list: ArrayList<Product> = ArrayList<Product>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            subCategoryId = it.getString(ARG_PARAM1)

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_product_big_layout, container, false)
//        view.text_view.text = subCategoryName

        view.recycler_view.layoutManager = LinearLayoutManager(activity)

        adapter = ProductAdapter(activity!!, list)

        view.recycler_view.adapter = adapter

        getData()

        return view
    }

    private fun getData() {

        var list: ArrayList<Product> = ArrayList<Product>()

//        var filtedList: ArrayList<Product> = ArrayList<Product>()

        var prefix: String = "https://apolis-grocery.herokuapp.com/api/products/"
        val url = prefix + subCategoryId

//        val url: String = "http://rjtmobile.com/grocery/products.json"

        var requestQueue = Volley.newRequestQueue(activity)
        var stringRequest = StringRequest(
            Request.Method.GET, url,
            Response.Listener{ response ->
                // make sure data type is String
                var data = response.toString()

                // using gson to convert between JSON strings and Java objects
                var gson = GsonBuilder().create()

                // gson.fromJson is converting Kotlin Data Class from JSON using GSON
                var productList: ProductList =  gson.fromJson(data, ProductList::class.java)

                list = productList.data

//                for (product in list) {
//                    if (product.subId.toString() == subCategoryId) {
//                        filtedList.add(product)
//                    }
//                }


                adapter.setData(list)

//                adapter.setData(filtedList)

                progress_bar.visibility = View.GONE

            },
            Response.ErrorListener {
                Toast.makeText(activity, it.message, Toast.LENGTH_LONG).show()
            })
        requestQueue.add(stringRequest)

    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

//    override fun onAttach(context: Context) {
//        super.onAttach(context)
//        if (context is OnFragmentInteractionListener) {
//            listener = context
//        } else {
//            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
//        }
//    }
//
//    override fun onDetach() {
//        super.onDetach()
//        listener = null
//    }


    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String) =
            ProductFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)

                }
            }
    }


}