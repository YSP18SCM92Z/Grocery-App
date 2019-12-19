package com.rjt.groceryapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rjt.groceryapp.R
import com.rjt.groceryapp.helpers.SessionManager
import kotlinx.android.synthetic.main.activity_confirmation.*

class ConfirmationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirmation)

        text_view_information.text = "Thank you for shopping! ${SessionManager(this).getFirstName()}"

    }
}
