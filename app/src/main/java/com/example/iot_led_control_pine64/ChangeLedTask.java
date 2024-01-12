package com.example.iot_led_control_pine64;

import android.os.AsyncTask;

public class ChangeLedTask extends AsyncTask<Callback, Void, Void> {
    String result;
    @Override
    protected Void doInBackground(Callback... callbacks) {
        callbacks[0].onCallback();
        return null;
    }
}
