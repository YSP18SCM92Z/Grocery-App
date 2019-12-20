package com.rjt.groceryapp.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.rjt.groceryapp.R
import com.rjt.groceryapp.activities.ConfirmationActivity
import kotlinx.android.synthetic.main.fragment_shipping.*
import kotlinx.android.synthetic.main.fragment_shipping.view.*

class ShippingFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_shipping, container, false)

//        onRadioButtonClickedPayment(view)

//        button_confirm_shipping(view)
//        onRadioButtonClickedShipping(view)

        view.button_confirm_shipping.setOnClickListener {
            //            fragmentManager?.beginTransaction()?.replace(R.id.fragment_container_checkout, PaymentFragment())?.commit()

            if (button_card.isChecked) {
                fragmentManager?.beginTransaction()?.replace(R.id.fragment_container_checkout, PaymentFragment())?.commit()
            } else {
                startActivity(Intent(view.context, ConfirmationActivity::class.java))
            }
        }
        return view
    }

//    private fun onRadioButtonClickedPayment(view: View?) {
//        if (view is RadioButton) {
//            // Is the button now checked?
//            val checked = view.isChecked
//
//            // Check which radio button was clicked
//            when (view.getId()) {
//                R.id.button_card ->
//                    if (checked) {
//                        // Pirates are the best
//                    }
//                R.id.button_cash ->
//                    if (checked) {
//                        // Ninjas rule
//                    }
//            }
//        }
//    }

    private fun onRadioButtonClicked(view: View) {
        if (view is RadioButton) {
            // Is the button now checked?
            val checked = view.isChecked

            // Check which radio button was clicked
            when (view.getId()) {
                R.id.button_standard ->
                    if (checked) {
                        Toast.makeText(view.context, "standard", Toast.LENGTH_SHORT).show()// Pirates are the best
                    }
                R.id.button_express ->
                    if (checked) {
                        Toast.makeText(view.context, "express", Toast.LENGTH_SHORT).show()
                    }
            }
        }
    }
}