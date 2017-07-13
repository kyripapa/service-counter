package com.example.notifyservice;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity {

/*
    protected final Timer myTimer = new Timer("MainActivityTimer", false);
*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // FIRST OF ALL
        // START SERVICE AS STICKY - BY EXPLICIT INTENT
        // to prevent being started by the system (without the sticky flag)
        Intent intent = new Intent(getApplicationContext(), NLService.class);

        // TODO: to be removed
        intent.putExtra("id", 101);
        intent.putExtra("msg", "hi");

        //starting service



        startService(intent);


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent i = new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS");

        startActivity(i);
        //setting button click
        findViewById(R.id.btn_start_service).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Ask service (async) for its status (manually after the main activity was paused or closed and missed some NLService's broadcasts)
                // the background service is sticky and the counters(added/removed) are not reset.

                //Creating an intent for sending to service
                //Intent intent = new Intent(getApplicationContext(), MyService.class);
                Intent intent = new Intent(getApplicationContext(), NLService.class);
                intent.putExtra("command", "get_status");
                //starting service
                startService(intent);

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        // register broadcast receiver for the intent MyTaskStatus
        LocalBroadcastManager.getInstance(this).registerReceiver(MyReceiver, new IntentFilter(NLService.ACTION_STATUS_BROADCAST));

    }


    //Defining broadcast receiver
    private BroadcastReceiver MyReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i("MainActivity", "Broadcast Recieved: "+intent.getStringExtra("serviceMessage"));

            String message = intent.getStringExtra("serviceMessage");


            Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
        }
    };




    @Override
    protected void onStop() {
        super.onStop();

        LocalBroadcastManager.getInstance(this).unregisterReceiver(MyReceiver);
    }
    public void sendDB(View view){

        getApplicationContext().sendBroadcast(
                new Intent(getApplicationContext(), UploadDataReceiver.class));
    }

}