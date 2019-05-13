package com.example.testapp;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Sensortracking extends Activity implements SensorEventListener {

    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private float mAccel; // acceleration apart from gravity
    private float mAccelCurrent; // current acceleration including gravity
    private float mAccelLast; // last acceleration including gravity
    private Client client;
    private DatabaseReference mDatabase;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("Compass MainActivity", "Registerered for ORIENTATION Sensor");
        super.onCreate(savedInstanceState);


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

        Log.d("mySensor", "Acceleration is " + mAccel + "m/s^2");

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
        //Device device = new Device(name, status);
     //   mDatabase.child("devices").child(deviceID).setValue(device);
    }


}
