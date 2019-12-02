package com.rjt.groceryapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rjt.groceryapp.R
import com.rjt.groceryapp.models.Product
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_product_detail.*

class ProductDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)

        val product = intent.getSerializableExtra(Product.PRODUCT) as Product

        //set up tool bar for each product
//        setUpToolBar(product)

        text_view_product_detail_name.text = product.productName
        text_view_product_detail_description.text = product.description
        text_view_product_detail_price.text = "$" + product.price.toString()

        Picasso.with(this).
            load("http://rjtmobile.com/grocery/images/" + product.image).
            error(R.drawable.no_image).
            into(image_view_product_detail)
    }
}
