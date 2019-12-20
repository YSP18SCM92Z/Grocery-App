package com.rjt.groceryapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.rjt.groceryapp.R
import com.rjt.groceryapp.app.Config.Companion.IMAGE_URL
import com.rjt.groceryapp.database.DBHelper
import com.rjt.groceryapp.helpers.SessionManager
import com.rjt.groceryapp.models.Cart
import com.rjt.groceryapp.models.Product
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_cart.*
import kotlinx.android.synthetic.main.activity_product_detail.*
import kotlinx.android.synthetic.main.app_bar.*
import kotlinx.android.synthetic.main.row_cart_adapter.*

class ProductDetailActivity : AppCompatActivity() {

    private lateinit var dbHelper: DBHelper

//    private lateinit var product: Product

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)

        var product = intent.getSerializableExtra(Product.PRODUCT) as Product

        //set up tool bar for each product
        setUpToolBar(product)

        init(product)


    }

    private fun setUpToolBar(product: Product) {
        var toolbar = tool_bar

        toolbar.title = product.productName

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    //back button on the tool bar
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
            R.id.item_cart -> {
                startActivity(Intent(this, CartActivity::class.java))
                return true
            }
            R.id.item_sign_out -> {
                startActivity(Intent(this, LoginActivity::class.java))
                SessionManager(this).logout()
//                Toast.makeText(this, SessionManager.IS_LOGIN, Toast.LENGTH_LONG).show()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }



    private fun init(product : Product) {

        text_view_product_detail_name.text = product.productName
        text_view_product_detail_description.text = product.description
        text_view_product_detail_price.text = "$" + product.price.toString()

        Picasso.with(this).
            load(IMAGE_URL + product.image).
            error(R.drawable.no_image).
            into(image_view_product_detail)

        button_add_to_cart.setOnClickListener {

            dbHelper = DBHelper(this)


            product.qty = 1 + dbHelper.getProductFromCart(product)

            dbHelper.addToCart(product)

            Toast.makeText(this, product.productName + " added", Toast.LENGTH_LONG).show()

            var intent = Intent(this, CartActivity::class.java)

//            intent.putExtra("QTY", product.qty)

            startActivity(intent)
        }
    }

}
