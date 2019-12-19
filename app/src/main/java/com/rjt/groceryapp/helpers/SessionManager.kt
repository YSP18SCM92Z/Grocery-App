package com.rjt.groceryapp.helpers

import android.content.Context
import android.content.SharedPreferences
import com.rjt.groceryapp.models.Register
import com.rjt.groceryapp.models.User

class SessionManager (var context: Context){

    lateinit var sharedPreferences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor

    init{
        sharedPreferences = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()
    }

    companion object{
        val FILE_NAME: String = "myPref"
        val FIRST_NAME : String = "first_Name"
        val LAST_NAME: String = "last_Name"
        val MOBILE: String = "mobile"
        val EMAIL: String = "email"
        val PASSWORD: String = "password"
        val IS_LOGIN: String = "is_login"
    }


    fun CreateUser(register: Register){
        editor.putString(FIRST_NAME, register.firstName)
        editor.putString(LAST_NAME, register.lastName)
        editor.putString(MOBILE, register.mobile)
        editor.putString(EMAIL, register.email)
        editor.putString(PASSWORD, register.password)
        editor.putBoolean(IS_LOGIN, true)
        editor.commit()
    }

    // Overload with User
    fun CreateUser(user: User) {
        editor.putString(FIRST_NAME, user.firstName)
        editor.putString(LAST_NAME, user.lastName)
        editor.putString(MOBILE, user.mobile)
        editor.putString(EMAIL, user.email)
        editor.putString(PASSWORD, user.password)
        editor.putBoolean(IS_LOGIN, true)
        editor.commit()
    }

    fun isLogin(): Boolean {
        return sharedPreferences.getBoolean(IS_LOGIN, false)
    }

    fun logout() {
        sharedPreferences.edit().clear().commit()
    }

    fun getFirstName() : String? {
        return sharedPreferences.getString(FIRST_NAME, null)
    }

}