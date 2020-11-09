package com.sunasterisk.remotesmarthouse.model

data class Sensors(
    var LEDList : List<LED>,
    var airList : List<Air>,
    var RFIDList : List<RFID>
)
