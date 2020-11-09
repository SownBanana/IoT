package com.sunasterisk.remotesmarthouse

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.gson.Gson
import com.sunasterisk.remotesmarthouse.model.Air
import com.sunasterisk.remotesmarthouse.model.LED
import com.sunasterisk.remotesmarthouse.model.RFID
import com.sunasterisk.remotesmarthouse.model.Sensors
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var airs = listOf<Air>(Air(1,value = 20))
        var leds = listOf<LED>(LED(1,value = 20))
        var RFIDs = listOf<RFID>(RFID(1))
        var s = Sensors(leds,airs,RFIDs)
        var gson = Gson()
        text.text =    gson.toJson(s)
    }
}
