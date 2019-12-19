package com.rjt.groceryapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.GsonBuilder
import com.rjt.groceryapp.R
import com.rjt.groceryapp.adapters.FragmentAdapter
import com.rjt.groceryapp.app.Endpoints
import com.rjt.groceryapp.helpers.SessionManager
import com.rjt.groceryapp.models.SubCategory
import com.rjt.groceryapp.models.SubCategoryList
import kotlinx.android.synthetic.main.activity_product_list.*
import kotlinx.android.synthetic.main.app_bar.*

class SubCategoryActivity : AppCompatActivity() {

//    var prefixURL : String = "https://apolis-grocery.herokuapp.com/api/subcategory/"

    var fragmentAdapter: FragmentAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_list)

        var catID = intent.getIntExtra("ID", 0)
        var catName = intent.getStringExtra("NAME")

        setUpToolBar(catName)


//        var url = prefixURL + catID.toString()
        var url = Endpoints.getSubCategoryByCatId(catID)


        Toast.makeText(this, url, Toast.LENGTH_LONG).show()

        getSubCategory(url)
    }

    private fun setUpToolBar(catName: String?) {
        var toolbar = tool_bar

        toolbar.title = catName

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


    private fun getSubCategory(url: String): ArrayList<SubCategory>{
        fragmentAdapter = FragmentAdapter(supportFragmentManager)

        var list: ArrayList<SubCategory> = ArrayList<SubCategory>()
//        list.add(SubCategory("Sub 1"))
//        list.add(SubCategory("Sub 2"))
//        list.add(SubCategory("Sub 3"))
//        list.add(SubCategory("Sub 4"))
//        return list

        var requestQueue = Volley.newRequestQueue(this)
        var stringRequest = StringRequest(
            Request.Method.GET, url,
            Response.Listener { response ->
                var data = response.toString()
                var gson = GsonBuilder().create()
                var subCategoryList: SubCategoryList =  gson.fromJson(data, SubCategoryList::class.java)

                list = subCategoryList.data

                for(subCategory in list){
                    fragmentAdapter?.addFragment(subCategory)
                }
                view_pager.adapter = fragmentAdapter
                tab_layout.setupWithViewPager(view_pager)

            },
            Response.ErrorListener {
                Log.e("yifan", it.message)
            })
        requestQueue.add(stringRequest)
        return list
    }
}
