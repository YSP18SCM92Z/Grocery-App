package com.rjt.groceryapp.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.rjt.groceryapp.R
import com.rjt.groceryapp.activities.ConfirmationActivity
import kotlinx.android.synthetic.main.fragment_payment.*
import kotlinx.android.synthetic.main.fragment_payment.view.*

class PaymentFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_payment, container, false)

        view.button_confirm_payment.setOnClickListener {
            startActivity(Intent(view.context, ConfirmationActivity::class.java))
        }

        return view
    }
}