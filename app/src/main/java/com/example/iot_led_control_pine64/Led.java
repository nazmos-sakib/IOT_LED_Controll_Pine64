package com.example.iot_led_control_pine64;

import android.graphics.Color;

import com.example.iot_led_control_pine64.databinding.ActivityMainBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class Led {

    public static JSONObject checkLedStatus(String ledStatusUrl) throws MalformedURLException, JSONException, ExecutionException, InterruptedException, DisconnectedException {
        String ledStatus = CommunicateWIthBl602.getLedStatus( new URL(ledStatusUrl) );
        if (ledStatus!=null){
             return  new JSONObject(ledStatus);
        } else throw new DisconnectedException("Disconnected");
    }

    public static void setLedAccordingToStatus(JSONObject jsonObject, ActivityMainBinding binding) throws JSONException {

        binding.swRed.setChecked(
                jsonObject.getInt("led_red")==0);

        binding.swGreen.setChecked(
                jsonObject.getInt("led_green")==0);

        binding.swBlue.setChecked(
                jsonObject.getInt("led_blue")==0);

        binding.imgLed.setColorFilter(Color.argb(
                255,
                jsonObject.getInt("led_red")==0?255:0,
                jsonObject.getInt("led_green")==0?255:0,
                jsonObject.getInt("led_blue")==0?255:0
        ));

    }


}
