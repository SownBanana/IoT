package com.sunasterisk.smarthomejava

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.sunasterisk.smarthomejava.config.Config.*
import kotlinx.android.synthetic.main.activity_login.*


class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        btnLogin.setOnClickListener(){
            if (inputUsername.text.toString().trim().length>0 && inputPassword.text.toString().trim().length>0){
                
            }
        }
    }

    private fun loadUser() {
        val sharedPreferences = getSharedPreferences(FILE_USER, Context.MODE_PRIVATE)
        if (sharedPreferences != null) {

        } else {

        }
    }

    private fun doSave(username: String, pass : String) {
        val sharedPreferences = getSharedPreferences(FILE_USER, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(FILE_USER_NAME, username)
        editor.putString(FILE_USER_PASS, pass)
        // Save.
        editor.apply()
    }
}
