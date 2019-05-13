package com.example.testapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class ScreenReceiver extends BroadcastReceiver {
    private boolean screenOff;

    @Override
    public void onReceive(Context context, Intent intent) {
        System.out.println("######################");
        Log.d("screen ", "on/off" + intent.getAction());
        if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
            screenOff = true;
        } else if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
            screenOff = false;
        }
        Intent i = new Intent(context, BackgroundDervice2.class);
        i.putExtra("screen_state", screenOff);
        //context.startService(i);
    }
    }

