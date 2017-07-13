package com.example.notifyservice;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.provider.MediaStore;
import android.service.notification.StatusBarNotification;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.notifyservice.R;

public class ResultActivity extends Activity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notificationview);
        String packageName = getIntent().getStringExtra("packname");
        System.out.println("Package" + packageName);
        // Create Notification Manager
        NotificationManager notificationmanager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        // Dismiss Notification
        notificationmanager.cancel(0);

    }



    public void pageTwo(View view)
    {
        RadioButton rb;
        RadioButton stayedNot;
        RadioButton wasBusy;
        RadioButton wasNotBusy;
        rb = (RadioButton) findViewById(R.id.deletedNot);
        wasBusy = (RadioButton)findViewById(R.id.wasBusy);
        String notificationStayed;
        String busyOrNot;
        String packageName = getIntent().getStringExtra("packname");
        System.out.println("Package" + packageName);
        if(rb.isChecked() == true) {
            notificationStayed = "Deleted";
            Log.d("Notification: ", "deleted");
        }
       else {
            notificationStayed = "Notification opened";
        }
        if(wasBusy.isChecked() == true) {
             busyOrNot = "I was busy";
        }
        else {
            busyOrNot = "I was not busy";
        }


        Intent intent = new Intent(ResultActivity.this, QpageTwo.class);

        //Create the bundle
        Bundle bundle = new Bundle();

        //Add your data to bundle
        bundle.putString("First answer", notificationStayed);
        bundle.putString("Second answer", busyOrNot);
        bundle.putString("Package" , packageName);

        //Add the bundle to the intent
        intent.putExtras(bundle);

        startActivity(intent);
        overridePendingTransition(R.anim.enter, R.anim.exit);
    }
}