package com.sunasterisk.smarthomejava.model;

import com.google.gson.annotations.SerializedName;

public class Door {
    @SerializedName("time_stamp")
    public String timeStamp;
    @SerializedName("permission")
    public String permission;
    public static String URL = "localhost:3000/getStateBulb?id=1";

    public Door(String timeStamp, String permission) {
        this.timeStamp = timeStamp;
        this.permission = permission;
    }
}
