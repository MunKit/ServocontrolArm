package com.example.munkit.servocontrolarm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.DisconnectedBufferOptions;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import static android.R.color.holo_green_light;
import static android.R.color.holo_orange_light;
import static android.R.color.holo_red_light;

public class MainActivity extends AppCompatActivity {

    private MqttAndroidClient mqttAndroidClient;
    private final String serverUri = "tcp://iot.eclipse.org:1883";
    private final String clientId = "myAndClient";
    private static final String TAG = "Mymessage";
    private final String pubchannel = "RoboticArm/message";

    //true mean release
    private boolean clampstat = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mqttAndroidClient = new MqttAndroidClient(getApplicationContext(), serverUri, clientId);
        mqttAndroidClient.setCallback(new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean reconnect, String serverURI) {

                TextView respondtext = (TextView) findViewById(R.id.respondtext);
                if (reconnect) {
                    respondtext.setText("Reconnected to : " + serverURI);
                    respondtext.setTextColor(getResources().getColor(holo_orange_light));
                    //Log.i(TAG,"Reconnected to : " + serverURI);
                    // Because Clean Session is true, we need to re-subscribe
                    //subscribeToTopic(subchannel);
                } else {
                    respondtext.setText("Connected to: " + serverURI);
                    respondtext.setTextColor(getResources().getColor(holo_green_light));
                    //Log.i(TAG,"Connected to: " + serverURI);
                }
            }

            @Override
            public void connectionLost(Throwable cause) {
                TextView respondtext = (TextView) findViewById(R.id.respondtext);
                respondtext.setText("The Connection was lost.");
                respondtext.setTextColor(getResources().getColor(holo_red_light));
                //Log.i(TAG,"The Connection was lost.");
            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                //Log.i(TAG,"Incoming message: " + new String(message.getPayload()));
                //set transition
                //transition = true;
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {

            }
        });

        MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
        mqttConnectOptions.setAutomaticReconnect(true);
        mqttConnectOptions.setCleanSession(false);

        try {
            mqttAndroidClient.connect(mqttConnectOptions, null, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    DisconnectedBufferOptions disconnectedBufferOptions = new DisconnectedBufferOptions();
                    disconnectedBufferOptions.setBufferEnabled(true);
                    disconnectedBufferOptions.setBufferSize(100);
                    disconnectedBufferOptions.setPersistBuffer(false);
                    disconnectedBufferOptions.setDeleteOldestMessages(false);
                    mqttAndroidClient.setBufferOpts(disconnectedBufferOptions);
                    //subscribeToTopic(subchannel);
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    //Log.i(TAG,"Failed to connect to: " + serverUri);
                    //respondtext.setText("Failed to connect to: " + serverUri);
                    //respondtext.setTextColor(getResources().getColor(holo_red_light));
                }
            });


        } catch (MqttException ex){
            ex.printStackTrace();
        }
        final Button clampButton = (Button)findViewById(R.id.Clampbutton);
        clampButton.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){
                        //click to stop publish message
                        clampstat = !clampstat;

                        if (clampstat) {
                            clampButton.setText("Hold");
                            publishMessage(pubchannel, "release");
                        } else {
                            clampButton.setText("release");
                            publishMessage(pubchannel, "Hold");
                        }

                    }
                });

        Button Servo1cwButton = (Button)findViewById(R.id.Servo1cw);
        Servo1cwButton.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){
                            publishMessage(pubchannel, "Servo1cw");
                    }
                });
        Button Servo2cwButton = (Button)findViewById(R.id.Servo2cw);
        Servo2cwButton.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){
                        publishMessage(pubchannel, "Servo2cw");
                    }
                });
        Button Servo3cwButton = (Button)findViewById(R.id.Servo3cw);
        Servo3cwButton.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){
                        publishMessage(pubchannel, "Servo3cw");
                    }
                });
        Button Servo4cwButton = (Button)findViewById(R.id.Servo4cw);
        Servo4cwButton.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){
                        publishMessage(pubchannel, "Servo4cw");
                    }
                });
        Button Servo1ccwButton = (Button)findViewById(R.id.Servo1ccw);
        Servo1ccwButton.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){
                        publishMessage(pubchannel, "Servo1ccw");
                    }
                });
        Button Servo2ccwButton = (Button)findViewById(R.id.Servo2ccw);
        Servo2ccwButton.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){
                        publishMessage(pubchannel, "Servo2ccw");
                    }
                });
        Button Servo3ccwButton = (Button)findViewById(R.id.Servo3ccw);
        Servo3ccwButton.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){
                        publishMessage(pubchannel, "Servo3ccw");
                    }
                });
        Button Servo4ccwButton = (Button)findViewById(R.id.Servo4ccw);
        Servo4ccwButton.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){
                        publishMessage(pubchannel, "Servo4ccw");
                    }
                });

    }

    public void publishMessage(String Channel, String pubmessage){

        try {
            MqttMessage message = new MqttMessage();
            message.setPayload(pubmessage.getBytes());
            mqttAndroidClient.publish(Channel, message);
            if(!mqttAndroidClient.isConnected()){
                Log.i(TAG,mqttAndroidClient.getBufferedMessageCount() + " messages in buffer.");
            }
        } catch (MqttException e) {
            Log.i(TAG,"Error Publishing: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
