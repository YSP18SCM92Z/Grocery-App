package com.rjt.groceryapp.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.GsonBuilder
import com.rjt.groceryapp.R
import com.rjt.groceryapp.adapters.CategoryAdapter
import com.rjt.groceryapp.adapters.ImageSliderAdapter
import com.rjt.groceryapp.app.Endpoints
import com.rjt.groceryapp.helpers.SessionManager
import com.rjt.groceryapp.models.Category
import com.rjt.groceryapp.models.CategoryList
import kotlinx.android.synthetic.main.activity_category.*
import kotlinx.android.synthetic.main.app_bar.*

class CategoryActivity : AppCompatActivity() {

    var adapter: CategoryAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)

        setUpToolBar()

        imageSlider()

        init()

        getCategory()

        if (SessionManager(this).getFirstName() == null) {
            Toast.makeText(this, "Welcome guest", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this, "Welcome ${SessionManager(this).getFirstName()}", Toast.LENGTH_LONG).show()
        }

    }

    private fun imageSlider() {
        var imageSliderAdapter: ImageSliderAdapter = ImageSliderAdapter(supportFragmentManager)
        view_pager_category.adapter = imageSliderAdapter
    }

    private fun setUpToolBar() {
        var toolbar = tool_bar

        if (SessionManager(this).getFirstName() == null) {
            toolbar.title = "Welcome my guest"
        } else {
            toolbar.title = "Welcome ${SessionManager(this).getFirstName()}"
        }


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
        var list = ArrayList<Category>()

        recycler_view.layoutManager = GridLayoutManager(this, 2)
//        recycler_view.layoutManager = LinearLayoutManager(this)
        adapter = CategoryAdapter(this, list)
        recycler_view.adapter = adapter
    }

    private fun getCategory() {
        val url: String = Endpoints.getCategory()
        var requestQueue = Volley.newRequestQueue(this)
        var stringRequest = StringRequest(
            Request.Method.GET, url,
            Response.Listener { response ->
                // make sure data type is String
                var data = response.toString()

                // using gson to convert between JSON strings and Java objects
                var gson = GsonBuilder().create()

                // gson.fromJson is converting Kotlin Data Class from JSON using GSON
                var categoryList: CategoryList = gson.fromJson(data, CategoryList::class.java)

                adapter?.setData(categoryList.data)

                progress_bar.visibility = View.GONE

                Log.d("yifan", data)
            },
            Response.ErrorListener {
                Log.e("yifan", it.message)
            })
        requestQueue.add(stringRequest)
    }
}
