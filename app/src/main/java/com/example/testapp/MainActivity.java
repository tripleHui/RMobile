package com.example.testapp;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.SensorManager;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;


import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;


public class MainActivity extends AppCompatActivity {
    public sensor s;
     TextView txtView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        txtView = findViewById(R.id.accelaratorValue);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
       Toolbar toolbar = findViewById(R.id.toolbar);
       setSupportActionBar(toolbar);


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        //


}

    @Override
    protected void onPause() {
        // when the screen is about to turn off

        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private static String host = "10.51.44.72";
   // String wifiip = wifiIpAddress(this);
    //Log.v("SERVER", wifiip);

    private static int port = 8000;
    private Socket socket;
    private Handler handler = new Handler();
    public void run(){

        Thread connectThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    socket = new Socket(host, port);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        connectThread.start();
        try {
            connectThread.join(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

       if (socket == null) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(MainActivity.this, "サーバーに接続できませんでした。", Toast.LENGTH_LONG).show();
                }
            });
            //showMessage("サーバーに接続できませんでした。");
            return;
        }

        try {
            InputStream input = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));

            while(!socket.isClosed()){

                final String message = reader.readLine();
                if(message == null) break;

                //analyzeMessage(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //ソケットクローズ -> スレッド終了

    }
    /*
    public void sendPost() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://10.51.44.72:8080");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                    conn.setRequestProperty("Accept","application/json");
                    conn.setDoOutput(true);
                    conn.setDoInput(true);

                    JSONObject jsonParam = new JSONObject();
                    jsonParam.put("id", 123);


                    Log.i("JSON", jsonParam.toString());
                    DataOutputStream os = new DataOutputStream(conn.getOutputStream());
                    //os.writeBytes(URLEncoder.encode(jsonParam.toString(), "UTF-8"));
                    os.writeBytes(jsonParam.toString());

                    os.flush();
                    os.close();

                    Log.i("STATUS", String.valueOf(conn.getResponseCode()));
                    Log.i("MSG" , conn.getResponseMessage());

                    conn.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();

    }


*/


    public void sendMessage(View view) {


      s = new sensor(this, view, this);

        // txtView_acc.setText(  s.getAccelerationValues().toString());
        //startService(new Intent("SayHello"));

        //    startActivity(intent);
     //  startService(new Intent(this, BackgroundService.class));
      //  startService(new Intent(this, BackgroundDervice2.class));

        //run();

        // sendPost();
    }




}
