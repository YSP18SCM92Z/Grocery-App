package com.rjt.groceryapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.rjt.groceryapp.R
import kotlinx.android.synthetic.main.fragment_address.view.*

class AddressFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_address, container, false)

//        view.text_view_title.text = "From first fragement"
//        view.image_view_picture.setImageResource(R.drawable.ic_launcher_background)
        view.button_confirm_address.setOnClickListener {
            fragmentManager?.beginTransaction()?.replace(R.id.fragment_container_checkout, ShippingFragment())?.commit()
        }

        return view
    }
}