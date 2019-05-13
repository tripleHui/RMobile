package com.example.testapp;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class BackgroundService extends Service implements SensorEventListener {

    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private float mAccel; // acceleration apart from gravity
    private float mAccelCurrent; // current acceleration including gravity
    private float mAccelLast; // last acceleration including gravity
    private Client client;
    private DatabaseReference mDatabase;


    @Override
    public void onCreate() {
        super.onCreate();
        // register receiver that handles screen on and screen off logic
        IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        BroadcastReceiver mReceiver = new ScreenReceiver();
        registerReceiver(mReceiver, filter);
    }

    @Override
    public void onStart(Intent intent, int startId) {


    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        System.out.println("SCREEN detect!!!!!!!!!!!!");
        boolean screenOn = intent.getBooleanExtra("screen_state", false);
        if (!screenOn) {
            System.out.println("SCREEN OFF");
        } else {
            System.out.println("SCREEN ON");
        }

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager
                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener(this, mAccelerometer,
                SensorManager.SENSOR_DELAY_UI, new Handler());
        return START_STICKY;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    public void onSensorChanged(SensorEvent event) {


        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];
        mAccelLast = mAccelCurrent;
        mAccelCurrent = (float) Math.sqrt((double) (x * x + y * y + z * z));
        float delta = mAccelCurrent - mAccelLast;
        mAccel = mAccel * 0.9f + delta; // perform low-cut filter
      //  Log.d("mySensor", "Acceleration is " + mAccel + "m/s^2");

        if (mAccel > 11) {
           // mLastShakeTime = curTime;
            Toast.makeText(getApplicationContext(), "FALL DETECTED",
                    Toast.LENGTH_LONG).show();
            writeStatus("notdefined", "iphone", 0);
         //   client = new Client(this);
           // client.connectWithAsyncTask();

        }
       // else{  System.out.print("no");}
    }

    /**
     * show notification when Accel is more then the given int.
     */
    private void writeStatus(String deviceID, String name, int status) {


        mDatabase = FirebaseDatabase.getInstance().getReference();
       // Device device = new Device(name, status);
     //   mDatabase.child("devices").child(deviceID).setValue(device);
    }
}
