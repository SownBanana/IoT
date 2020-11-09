package com.sunasterisk.remotesmarthouse.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class APIResponse {
    lateinit var mRetrofit: Retrofit
    constructor(){
        mRetrofit=buildRetrofit()
    }
    private fun buildRetrofit() : Retrofit {
        val url ="https://jsonplaceholder.typicode.com/"
        return  Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(url)
            .build()
    }
    fun getRetrofit() =mRetrofit
    companion object {
        @Volatile
        private var INSTANCE: APIResponse? = null

        fun getInstance() =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: APIResponse().also { INSTANCE = it }
            }
    }
}
