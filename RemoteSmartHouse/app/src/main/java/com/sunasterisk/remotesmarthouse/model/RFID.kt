package com.sunasterisk.remotesmarthouse.model

import com.google.gson.annotations.Expose

data class RFID(
    val id : Int,
    @Expose(deserialize = true)
    var name: String="RFID ${id}",
    val state: Boolean = true,
    val check: Boolean = true)
