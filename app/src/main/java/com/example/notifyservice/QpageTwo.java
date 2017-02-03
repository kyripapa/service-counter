package com.example.notifyservice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;

public class QpageTwo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qpage_two);

    }

    public void pageThree(View view)
    {
        RadioButton wasImportant = (RadioButton) findViewById(R.id.wasImportant);
        RadioButton wasAnnoying = (RadioButton) findViewById(R.id.wasAnnoying);
        String importantNot;
        String annoyingNot;

        if(wasImportant.isChecked() == true) {
             importantNot = "It was important";
            Log.d("Notification: ", "Important");
        }
        else {
             importantNot = "It was not important";
        }
        if(wasAnnoying.isChecked() == true) {
             annoyingNot = "I was annoying";
        }
        else {
            annoyingNot = "It was not annoying";
        }

        Intent intent = new Intent(QpageTwo.this, QpageThree.class);
        //Get the bundle
        Bundle bundle = getIntent().getExtras();

        //Extract the data…
        String firstAnswer = bundle.getString("First answer");
        String secondAnswer = bundle.getString("Second answer");
        Log.d("First: ", firstAnswer);



        //Add your data to bundle
        bundle.putString("First answer", firstAnswer);
        bundle.putString("Second answer", secondAnswer);
        bundle.putString("Third answer", importantNot);
        bundle.putString("Fourth answer", annoyingNot);

        //Add the bundle to the intent
        intent.putExtras(bundle);
        startActivity(intent);


        overridePendingTransition(R.anim.enter, R.anim.exit);

    }
}
