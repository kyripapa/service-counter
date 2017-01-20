package com.example.notifyservice;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.notifyservice.R;

public class ResultActivity extends Activity {
    String title;
    String text;
    TextView txttitle;
    TextView txttext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notificationview);

        // Create Notification Manager
        NotificationManager notificationmanager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        // Dismiss Notification
        notificationmanager.cancel(0);

        /*// Retrive the data from MainActivity.java
        Intent i = getIntent();

        title = i.getStringExtra("title");
        text = i.getStringExtra("text");

        // Locate the TextView
        txttitle = (TextView) findViewById(R.id.title);
        txttext = (TextView) findViewById(R.id.text);

        // Set the data into TextView
        txttitle.setText(title);
        txttext.setText(text);*/
    }
    public void pageTwo(View view)
    {
        Intent intent = new Intent(ResultActivity.this, QpageTwo.class);

        startActivity(intent);
        overridePendingTransition(R.anim.enter, R.anim.exit);
    }
}