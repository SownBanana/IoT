package com.sunasterisk.smarthomejava;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.io.UnsupportedEncodingException;

public class MqttConnectBroadcast extends BroadcastReceiver {
    static public  String BROAD_CAST_NAME ="MqttConnectBroadcast";
    static public  String NOTIFI_ID ="BROAD_CARD_NAME";

    public String TAG = "IoT8";
    MqttAndroidClient client;
    String topic = "IoT8";
    String topic2 = "IoT82";
    Context mContext;
    int idNotify =3;
    byte[] payload = "Xin chào nhé".getBytes();
    void initMqtt() {
        Log.d(TAG, "Init ");
        String clientId = MqttClient.generateClientId();
        client =
                new MqttAndroidClient(mContext.getApplicationContext(), "tcp://broker.hivemq.com:1883",
                        clientId);

        try {
            MqttConnectOptions options = new MqttConnectOptions();
            options.setMqttVersion(MqttConnectOptions.MQTT_VERSION_3_1);

            options.setWill(topic, payload ,1,false);
            IMqttToken token = client.connect(options);
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    // We are connected
                    Log.d(TAG, "onSuccess");
                    sub(topic);
//                    pubblicToppic(topic,"");
//                    sub(topic2);
//                    pubblicToppic(topic2,"Bật tắt đèn");
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    // Something went wrong e.g. connection timeout or firewall problems
                    Log.d(TAG, "onFailure");

                }
            });
            client.setCallback(new MqttCallback() {
                @Override
                public void connectionLost(Throwable cause) {

                }

                @Override
                public void messageArrived(String topic, MqttMessage message) throws Exception {
//khi có dữ liệu đổ về thì hàm này chạy
                    Log.d(topic, topic.toString() +message.toString());
                    NotificationSmartHouse.callNotify(mContext,idNotify++,topic,"có dữ liệu",message.toString());
                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {

                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }

    }

     static public void start(Context context){
        Intent intent = new Intent();
        intent.setAction(BROAD_CAST_NAME);
        context.sendBroadcast(intent);
    }

    public void pubblicToppic(String topic, String content){
        String payload = content;
        byte[] encodedPayload = new byte[0];
        try {
            encodedPayload = payload.getBytes("UTF-8");
            MqttMessage message = new MqttMessage(encodedPayload);
            client.publish(topic, message);
        } catch (UnsupportedEncodingException | MqttException e) {
            e.printStackTrace();
        }
    }
    public  void sub(String topic){
        int qos = 1;
        try {
            IMqttToken subToken = client.subscribe(topic, qos);
            subToken.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    Log.d(TAG,"Sub thành công" );
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken,
                                      Throwable exception) {
                    Log.d(TAG,"Sub Thất bại");
                    // The subscription could not be performed, maybe the user was not
                    // authorized to subscribe on the specified topic e.g. using wildcards

                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        mContext =context;
        NotificationSmartHouse.createChannel(context,NOTIFI_ID,NOTIFI_ID, NotificationManager.IMPORTANCE_HIGH);
        initMqtt();
    }
}
