package com.sunasterisk.smarthomejava.model;

import com.google.gson.annotations.SerializedName;

public class Led {
    public int id;
    @SerializedName("value")
    public int value;

    public Led(int id, int value) {
        this.id = id;
        this.value = value;
    }

    public Led() {
    }

    @Override
    public String toString() {
        return "Led{" +
                "id=" + id +
                ", value=" + value +
                '}';
    }

    public static String URL = "localhost:3000/getStateBulb?id=1";
}
