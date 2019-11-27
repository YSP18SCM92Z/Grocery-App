package com.rjt.groceryapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.GsonBuilder
import com.rjt.groceryapp.R
import com.rjt.groceryapp.adapters.CategoryAdapter
import com.rjt.groceryapp.models.Category
import com.rjt.groceryapp.models.CategoryList
import kotlinx.android.synthetic.main.activity_category.*
import kotlinx.android.synthetic.main.app_bar.*
import kotlin.collections.ArrayList

class CategoryActivity : AppCompatActivity() {

    var adapter: CategoryAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)

        setUpToolBar()

        init()

        getCategory()
    }

    private fun setUpToolBar() {
        var toolbar = tool_bar

        toolbar.title = "Home"

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            android.R.id.home -> {
                finish()
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
        adapter = CategoryAdapter(this, list)
        recycler_view.adapter = adapter
    }

    private fun getCategory(){
        val url: String = "https://apolis-grocery.herokuapp.com/api/category"
        var requestQueue = Volley.newRequestQueue(this)
        var stringRequest = StringRequest(
            Request.Method.GET, url,
            Response.Listener{ response ->
                // make sure data type is String
                var data = response.toString()

                // using gson to convert between JSON strings and Java objects
                var gson = GsonBuilder().create()

                // gson.fromJson is converting Kotlin Data Class from JSON using GSON
                var categoryList: CategoryList =  gson.fromJson(data, CategoryList::class.java)

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
