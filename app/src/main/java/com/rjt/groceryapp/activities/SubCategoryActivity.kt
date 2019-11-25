package com.rjt.groceryapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.GsonBuilder
import com.rjt.groceryapp.R
import com.rjt.groceryapp.adapters.FragmentAdapter
import com.rjt.groceryapp.models.SubCategory
import com.rjt.groceryapp.models.SubCategoryList
import kotlinx.android.synthetic.main.activity_product_list.*
import java.net.URL

class SubCategoryActivity : AppCompatActivity() {

    var prefixURL : String = "https://apolis-grocery.herokuapp.com/api/subcategory/name/"

    var fragmentAdapter: FragmentAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_list)

        var categoryName = intent.getStringExtra("NAME")

        var url = prefixURL + categoryName

//        Toast.makeText(this, url, Toast.LENGTH_LONG).show()

        init(url)

        getSubCategory(url)
    }

    private fun init(url : String) {

        var list = ArrayList<SubCategory>()

        fragmentAdapter = FragmentAdapter(supportFragmentManager)
//        for(subCategory in getSubCategory(url)){
//            fragmentAdapter?.addFragment(subCategory)
//        }
        view_pager.adapter = fragmentAdapter
        tab_layout.setupWithViewPager(view_pager)


    }


    private fun getSubCategory(url: String): ArrayList<SubCategory>{
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

//                fragmentAdapter?.setData(subCategory.subName)
//                list.add(subCategory)

            },
            Response.ErrorListener {
                Log.e("yifan", it.message)
            })
        requestQueue.add(stringRequest)
        return list
    }
}
