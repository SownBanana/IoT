package com.sunasterisk.smarthomejava

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.sunasterisk.smarthomejava.config.Config
import com.sunasterisk.smarthomejava.config.Config.*
import com.sunasterisk.smarthomejava.retrofit.INetwork
import com.sunasterisk.smarthomejava.retrofit.RetrofitRespon
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit


class Login : AppCompatActivity() {
    var retrofit: Retrofit? = null
    var iNetwork: INetwork? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        //        innit retrofit
        MainActivity.retrofit = RetrofitRespon.getInstance().retrofit
        iNetwork = MainActivity.retrofit.create(INetwork::class.java)
        btnLogin.setOnClickListener(){
            doSaveShared(FILE_USER_TOKEN_SESSION,"11");

            startActivity(Intent(this@Login,MainActivity::class.java))

//            if (inputUsername.text.toString().trim().length>0 && inputPassword.text.toString().trim().length>0){
//                 iNetwork?.login(inputUsername.text.toString().trim(), inputPassword.text.toString().trim())?.enqueue(object : Callback<String> {
//                     override fun onResponse(call: Call<String>, response: Response<String>) {
//                         doSave(inputUsername.text.toString().trim(), inputPassword.text.toString().trim(), response.body().toString());
//                         startActivity(Intent(this@Login,MainActivity::class.java))
//                     }
//
//                     override fun onFailure(call: Call<String>, t: Throwable) {
//                         TODO("Not yet implemented")
//                     }
//
//                 });
//            }
        }
    }
    private fun loadToken() {
        val sharedPreferences = getSharedPreferences(Config.FILE_USER, Context.MODE_PRIVATE)
        if (sharedPreferences != null) {
            val token = sharedPreferences.getString(Config.FILE_USER_TOKEN_SESSION, "")
        }
    }
    private fun loadUser() {
        val sharedPreferences = getSharedPreferences(FILE_USER, Context.MODE_PRIVATE)
        if (sharedPreferences != null) {

        } else {

        }
    }
    private fun doSaveShared(key: String, value: String) {
        val sharedPreferences = getSharedPreferences(FILE_USER, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(key, value)
        // Save.
        editor.apply()
    }
    private fun doSave(username: String, pass: String, token: String) {
        val sharedPreferences = getSharedPreferences(FILE_USER, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(FILE_USER_NAME, username)
        editor.putString(FILE_USER_PASS, pass)
        editor.putString(FILE_USER_TOKEN_SESSION, token)
        // Save.
        editor.apply()
    }
}
