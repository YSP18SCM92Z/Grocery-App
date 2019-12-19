package com.rjt.groceryapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.rjt.groceryapp.R
import com.rjt.groceryapp.helpers.SessionManager

class SplashActivity : AppCompatActivity() {

    lateinit var myHandler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        myHandler = Handler()

        myHandler.postDelayed({
            goToMainActivity()
        }, 3000)
    }

    private fun goToMainActivity() {
        if (SessionManager(this).isLogin()) {
            startActivity(Intent(this, MainActivity::class.java))
        } else {
            var intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        //after main activity gets started, we can close this activity
        finish()
    }
}
