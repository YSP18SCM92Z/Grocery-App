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
import com.rjt.groceryapp.helpers.SessionManager
import com.rjt.groceryapp.models.Login
import com.rjt.groceryapp.models.User
import com.rjt.groceryapp.models.UserRecord
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

        text_view_click_1.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        button_guest.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

    }

    private fun getLogin(){

        val url: String = "https://apolis-grocery.herokuapp.com/api/auth/login"
        var requestQueue = Volley.newRequestQueue(this)

        var email = edit_text_login_email.text.toString()
        var password = edit_text_login_password.text.toString()

        var userRecord = UserRecord(email, password)

        var gson = Gson()
        var json = gson.toJson(userRecord)

//        GsonBuilder().create()

        var jsonObjRequest = JsonObjectRequest(
            Request.Method.POST, url, JSONObject(json),
            Response.Listener{jsonObject: JSONObject ->

                Toast.makeText(applicationContext, "logined", Toast.LENGTH_LONG).show()

                val validCredential = gson.fromJson(jsonObject.toString(), Login::class.java)

                SessionManager(this).CreateUser(validCredential.user)

//                Toast.makeText(this, """${validCredential.token} ${validCredential.user}""".trimMargin(), Toast.LENGTH_LONG).show()

                var intent = Intent(this, MainActivity::class.java)
                startActivity(intent)

            },
            Response.ErrorListener {
                Toast.makeText(applicationContext, it.message, Toast.LENGTH_LONG).show()
            })
        requestQueue.add(jsonObjRequest)
    }

}