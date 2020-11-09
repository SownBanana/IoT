package com.sunasterisk.remotesmarthouse.network

import com.sunasterisk.remotesmarthouse.model.Sensors
import retrofit2.Call
import retrofit2.http.GET

interface IDataNetwork {
    @get:GET()
    val sensors: Call<Sensors>
}
