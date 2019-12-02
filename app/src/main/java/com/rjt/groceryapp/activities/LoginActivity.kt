package com.rjt.groceryapp.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import com.rjt.groceryapp.R
import com.rjt.groceryapp.models.Login
import com.rjt.groceryapp.models.User
import kotlinx.android.synthetic.main.login.*
import org.json.JSONObject

class LoginActivity  : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        button_login_submit.setOnClickListener {
//            Toast.makeText()
            getLogin()
        }

    }

    private fun getLogin(){

//        var sharedPreferences : SharedPreferences = getSharedPreferences("Shared", Context.MODE_PRIVATE)

        val url: String = "https://apolis-grocery.herokuapp.com/api/auth/login"
        var requestQueue = Volley.newRequestQueue(this)

        var email = edit_text_login_email.text.toString()
        var password = edit_text_login_password.text.toString()

        var register = User(email, password)

        var gson = Gson()
        var json = gson.toJson(register)

//        GsonBuilder().create()

        var jsonObjRequest = JsonObjectRequest(
            Request.Method.POST, url, JSONObject(json),
            Response.Listener{jsonObject: JSONObject ->
                Toast.makeText(applicationContext, "logined", Toast.LENGTH_LONG).show()

                val validCredential = gson.fromJson(jsonObject.toString(), Login::class.java)

                Toast.makeText(this, """${validCredential.token} ${validCredential.user}""".trimMargin(), Toast.LENGTH_LONG).show()

                var intent = Intent(this, CategoryActivity::class.java)
                startActivity(intent)
            },
            Response.ErrorListener {
                Toast.makeText(applicationContext, "not login", Toast.LENGTH_LONG).show()
            })
        requestQueue.add(jsonObjRequest)
    }

}