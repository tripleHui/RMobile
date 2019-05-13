package com.example.testapp;


import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
        import android.hardware.SensorEventListener;
        import android.hardware.SensorManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class sensor {


    private float[] magneticValues;
    private float[] accelerationValues;
    private float[] orientationValues;
    private float[] rotationMatrix;
    private float pressureValue;
    private int proxmityValue;
    public TextView txtView_acc;
    public TextView txtView_mag;
    public TextView textView_pre;
    public TextView textView_pro;
    private DatabaseReference mDatabase;


    public sensor(Context context, View view, Activity act) {

        initializeSensor(context, view);
        txtView_acc = act.findViewById(R.id.accelaratorValue);
        txtView_mag = act.findViewById(R.id.magneticValue);
        textView_pre = act.findViewById(R.id.pressureValue);
        textView_pro = act.findViewById(R.id.proximityValue);

    }
    private List<Float> convertList(float[] a){

        List<Float> list = new ArrayList<>(a.length);

        for(Float valor : a) {
            list.add(valor);
        }
        return list;
    }

    public boolean onTouchEvent(MotionEvent event) {
        int x = (int)event.getX();
        int y = (int)event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_UP:
        }
        return false;
    }

    public void initializeSensor(Context context, View view){
/////////////////////////////////////////////////////////
        SensorManager sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);

        mDatabase = FirebaseDatabase.getInstance().getReference("not_defined");
        magneticValues = new float[3];
        accelerationValues = new float[3];
        orientationValues = new float[3];
        rotationMatrix = new float[9];
        //writeStatus("not_defined", "phone", accelerationValues, pressureValue, proxmityValue);
        final SensorEventListener mEventListener = new SensorEventListener() {
            public void onAccuracyChanged(android.hardware.Sensor sensor, int accuracy) {}
            public void onSensorChanged(SensorEvent event) {

                switch (event.sensor.getType()) {
                    case android.hardware.Sensor.TYPE_ACCELEROMETER:

                        System.arraycopy(event.values, 0, accelerationValues, 0, 3);
                        mDatabase.child("accelerationValues").setValue( convertList(accelerationValues));
                        txtView_acc.setText(   "value" +  accelerationValues[0] + " " + accelerationValues[1] + " " + accelerationValues[2]);
                        break;
                    case android.hardware.Sensor.TYPE_MAGNETIC_FIELD:
                        mDatabase.child("magneticValues").setValue(convertList(magneticValues));
                        System.arraycopy(event.values, 0, magneticValues, 0, 3);
                        txtView_mag.setText("value" +  magneticValues[0] + " " + magneticValues[1] + " " + magneticValues[2]);
                        break;
                    case android.hardware.Sensor.TYPE_PRESSURE:
                        mDatabase.child("pressure").setValue(convertList(accelerationValues));
                        pressureValue = event.values[0];
                       // Log.d("sensor", "value" +pressureValue );

                        textView_pre.setText("value" +  pressureValue);
                        break;
                    case Sensor.TYPE_PROXIMITY:

                        proxmityValue = (int) event.values[0];
                        textView_pro.setText("value" + proxmityValue);
                        mDatabase.child("proximity").setValue(proxmityValue);

                        if (event.values[0] == 0) {

                            Log.d("sensor", "near!!!!!" );
                        } else {
                            Log.d("sensor", "far!!!!!" );

                        }
                        break;
                }


            }
        };
        setListeners(sensorManager, mEventListener);
    }

    public void setListeners(SensorManager sensorManager, SensorEventListener mEventListener)
    {
        sensorManager.registerListener(mEventListener,
                sensorManager.getDefaultSensor(android.hardware.Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(mEventListener,
                sensorManager.getDefaultSensor(android.hardware.Sensor.TYPE_MAGNETIC_FIELD),
                SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(mEventListener,
                sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE),
                SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(mEventListener,
                sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY),
                SensorManager.SENSOR_DELAY_NORMAL);

    }
    private void writeStatus(String deviceID, String name, float[] accelerationValues, float pressureValue, int proxmityValue) {


        mDatabase = FirebaseDatabase.getInstance().getReference();
        String userId = mDatabase.push().getKey();

// creating user object
// pushing user to 'users' node using the userId

        Device device = new Device(name, accelerationValues, pressureValue, proxmityValue);

        mDatabase.child(userId).setValue(device);
    }

    public float[] getMagneticValues() {
        return magneticValues;
    }

    public float[] getAccelerationValues() {
        return accelerationValues;
    }

    public float[] getOrientationValues() {
        return orientationValues;
    }

    public float[] getRotationMatrix() {
        return rotationMatrix;
    }
}