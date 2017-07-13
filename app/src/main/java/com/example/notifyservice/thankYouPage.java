package com.example.notifyservice;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class thankYouPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thank_you_page);


    }

    public void homePage(View view) {

        Intent intent = new Intent(thankYouPage.this, MainActivity.class);

        startActivity(intent);

        overridePendingTransition(R.anim.enter, R.anim.exit);
    }

}
