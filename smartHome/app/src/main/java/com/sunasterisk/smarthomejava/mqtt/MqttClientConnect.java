package com.sunasterisk.smarthomejava.mqtt;

import android.content.Context;

import org.eclipse.paho.android.service.MqttAndroidClient;

public class MqttClientConnect {
    static public  String BROAD_CAST_NAME ="MqttConnectBroadcast";
    static public  String NOTIFI_ID ="BROAD_CARD_NAME";

    public String TAG = "IoT8";
    MqttAndroidClient client;
    String topic = "IoT8";
    String topic2 = "IoT82";
    Context mContext;
    int idNotify =3;
    byte[] payload = "Xin chào nhé".getBytes();
}
