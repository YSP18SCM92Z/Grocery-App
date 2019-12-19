package com.rjt.groceryapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.rjt.groceryapp.R
import com.rjt.groceryapp.helpers.SessionManager
import kotlinx.android.synthetic.main.activity_checkout.*
import kotlinx.android.synthetic.main.app_bar.*

class CheckoutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)

        setUpToolBar()

        init()

    }

    private fun init() {
        button_confirm_address.setOnClickListener {
            startActivity(Intent(this, ConfirmationActivity::class.java))
        }
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
}
