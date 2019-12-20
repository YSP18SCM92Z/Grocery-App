package com.rjt.groceryapp.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.rjt.groceryapp.fragments.AddressFragment
import com.rjt.groceryapp.fragments.PaymentFragment
import com.rjt.groceryapp.fragments.ShippingFragment

class CheckoutFragmentAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return when(position) {
            0 -> AddressFragment()
            1 -> ShippingFragment()
            else -> PaymentFragment()
        }
    }

    override fun getCount(): Int {
        return 3
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position) {
            0 -> "Address"
            1 -> "Shipping"
            else -> "Payment"
        }
    }
}