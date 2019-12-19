package com.rjt.groceryapp.ImageSliderFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.rjt.groceryapp.R
import kotlinx.android.synthetic.main.fragment_image.view.*

class SecondImage : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_image, container, false)

        view.image_view_picture.setImageResource(R.drawable.happy_thanksgiving)

        return view
    }
}