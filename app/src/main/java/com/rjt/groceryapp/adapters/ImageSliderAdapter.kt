package com.rjt.groceryapp.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.rjt.groceryapp.ImageSliderFragments.FirstImage
import com.rjt.groceryapp.ImageSliderFragments.SecondImage
import com.rjt.groceryapp.ImageSliderFragments.ThirdImage

class ImageSliderAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm){
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                FirstImage()
            }
            1 -> {
                SecondImage()
            }
            else -> {
                ThirdImage()
            }
        }
    }

    override fun getCount(): Int {
        return 3
    }

}