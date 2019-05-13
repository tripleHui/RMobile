package com.example.testapp;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class mobileTracking extends AppCompatActivity implements SensorEventListener {

    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private float mAccel; // acceleration apart from gravity
    private float mAccelCurrent; // current acceleration including gravity
    private float mAccelLast; // last acceleration including gravity
    private Client client;
    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_tracking);
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
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
    private void writeStatus(String deviceID, String name, int status) {


        mDatabase = FirebaseDatabase.getInstance().getReference();
       // Device device = new Device(name, status);
        //mDatabase.child("devices").child(deviceID).setValue(device);
    }
}
