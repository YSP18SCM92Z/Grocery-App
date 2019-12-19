package com.rjt.groceryapp.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.rjt.groceryapp.R
import com.rjt.groceryapp.helpers.SessionManager
import com.rjt.groceryapp.models.Register
import kotlinx.android.synthetic.main.register.*
import org.json.JSONObject

class RegisterActivity  : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register)

        button_submit.setOnClickListener {
            getRegister()

            var intent = Intent(this, LoginActivity::class.java)

            startActivity(intent)
        }

        text_view_click_2.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

    }

    private fun getRegister(){
        val url: String = "https://apolis-grocery.herokuapp.com/api/auth/register"
        var requestQueue = Volley.newRequestQueue(this)

        var email = edit_text_email.text.toString()
        var first = edit_text_firstname.text.toString()
        var last = edit_text_lastname.text.toString()
        var mobile = edit_text_mobile.text.toString()
        var password = edit_text_password.text.toString()

        var register = Register(email, first, last, mobile, password)

        var gson = Gson()
        var json = gson.toJson(register)

            GsonBuilder().create()

        var jsonObjRequest = JsonObjectRequest(
            Request.Method.POST, url, JSONObject(json),
            Response.Listener{ response ->

                Toast.makeText(applicationContext, "registered", Toast.LENGTH_LONG).show()
                SessionManager(this).CreateUser(register)

                var intent = Intent(this, MainActivity::class.java)
                startActivity(intent)

            },
            Response.ErrorListener {
                Toast.makeText(applicationContext, "not created", Toast.LENGTH_LONG).show()
            })
        requestQueue.add(jsonObjRequest)
    }


}