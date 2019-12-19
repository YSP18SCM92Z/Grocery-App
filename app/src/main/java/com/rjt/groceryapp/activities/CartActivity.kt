package com.rjt.groceryapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Adapter
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.rjt.groceryapp.R
import com.rjt.groceryapp.adapters.CartAdapter
import com.rjt.groceryapp.database.DBHelper
import com.rjt.groceryapp.helpers.SessionManager
import com.rjt.groceryapp.interfaces.ClickListener
import com.rjt.groceryapp.models.Cart
import kotlinx.android.synthetic.main.activity_cart.*
import kotlinx.android.synthetic.main.app_bar.*
import kotlinx.android.synthetic.main.fragment_product_big_layout.*

class CartActivity : AppCompatActivity(), ClickListener {

    private lateinit var dbHelper: DBHelper

    private lateinit var cartAdapter: CartAdapter

    private var mList: ArrayList<Cart> = ArrayList()

    private var totalPrice: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        onQuantityChange()

        Toast.makeText(this, ""+ mList.size, Toast.LENGTH_LONG).show()

        init()

        setUpToolBar()

    }

    private fun setUpToolBar() {
        var toolbar = tool_bar

        toolbar.title = "Cart"

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

    private fun init() {
        recycler_view_cart.layoutManager = LinearLayoutManager(this)
        cartAdapter = CartAdapter(this, mList, this)
        recycler_view_cart.adapter = cartAdapter

        button_place_order.setOnClickListener {
            if (SessionManager(this).isLogin()) {
                startActivity(Intent(this, CheckoutActivity::class.java))
            } else {
                var intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
        }


    }

    override fun onQuantityChange() {
        dbHelper = DBHelper(this)
        mList = dbHelper.getAllCartProduct()
        totalPrice = dbHelper.getTotalPrice()

        text_view_number_of_price.text = "$ %.2f".format(totalPrice)
    }


}
