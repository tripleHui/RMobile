package com.example.testapp;

public class Device {

    public String deviceName;
  //  public int deviceStatus;

   // private float[] magneticValues;
    private String accelerationValues;
    //private float[] orientationValues;
    // private float[] rotationMatrix;
    private float pressureValue;
    private int proxmityValue;


    public Device(String name, float[] accelerationValues, float pressureValue, int proxmityValue) {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)

        this.deviceName = deviceName;
        //  this.deviceStatus = deviceStatus;
        this.accelerationValues = accelerationValues.toString();
      //  this.pressureValue = pressureValue;
        //this.proxmityValue = proxmityValue;
    }



}


