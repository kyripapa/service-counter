package com.example.notifyservice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class QpageTwo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qpage_two);
    }

    public void pageThree(View view)
    {
        Intent intent = new Intent(QpageTwo.this, QpageThree.class);
        startActivity(intent);
        overridePendingTransition(R.anim.enter, R.anim.exit);

    }
}
