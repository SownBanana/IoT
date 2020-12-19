package com.sunasterisk.smarthomejava.model;

import com.google.gson.annotations.SerializedName;

public class Air {
    @SerializedName("time_stamp")
    public long timeStamp;

    @SerializedName("value")
    public double value;
    
    public static String URL = "localhost:3000/getStateBulb?id=1";
}
