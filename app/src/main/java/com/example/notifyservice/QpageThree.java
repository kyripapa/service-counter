package com.example.notifyservice;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class QpageThree extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qpage_three);
        final DatabaseHandler db = new DatabaseHandler(this);
        final Button button = (Button) findViewById(R.id.save);


        Intent i = new Intent("com.example.notify.NOTIFICATION_LISTENER_EXAMPLE");
        final String packageName = i.getStringExtra("packageName");


        final String currentDate = getDateTime();




        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

               //Get answer no5
                RadioButton time = (RadioButton) findViewById(R.id.time);
                RadioButton manyNotif = (RadioButton) findViewById(R.id.manyNotif);
                RadioButton distract = (RadioButton) findViewById(R.id.distract);
                RadioButton other = (RadioButton) findViewById(R.id.other);
                String fifthAnswer;

                if(time.isChecked() == true) {
                    fifthAnswer = "The time was not right";
                }
                else if(manyNotif.isChecked() == true) {
                    fifthAnswer = "Too many notifications";
                }
                else if(distract.isChecked() == true) {
                    fifthAnswer = "It distracted me";
                }
                else if(distract.isChecked() == true) {
                    fifthAnswer = "All together";
                }
                else {
                    fifthAnswer = "";
                }


//        Get answer no6
                RadioButton visual = (RadioButton) findViewById(R.id.visual);
                RadioButton vibrate = (RadioButton) findViewById(R.id.vibrate);
                RadioButton sound = (RadioButton) findViewById(R.id.sound);
                RadioButton all = (RadioButton) findViewById(R.id.all);
                final String sixthAnswer;
                if(visual.isChecked() == true) {
                    sixthAnswer = "Visual";
                }
                else if(vibrate.isChecked() == true) {
                    sixthAnswer = "Vibrate";
                }
                else if(sound.isChecked() == true) {
                    sixthAnswer = "Sound";
                }
                else if(all.isChecked() == true) {
                    sixthAnswer = "All";
                }
                else {
                    sixthAnswer = "";
                }

                Bundle bundle = getIntent().getExtras();

                //Extract the data…
                String firstAnswer = bundle.getString("First answer");
                String secondAnswer = bundle.getString("Second answer");
                String thirdAnswer = bundle.getString("Third answer");
                String fourthAnswer = bundle.getString("Fourth answer");
                Log.d("Insert: ", firstAnswer + "," + sixthAnswer);
                Log.d("Insert: ", secondAnswer + "," + thirdAnswer);

                // Perform action on click
                Log.d("Insert: ", "Inserting ..");
                db.addContact(new Notification(currentDate, packageName, firstAnswer, secondAnswer, thirdAnswer, fourthAnswer, fifthAnswer, sixthAnswer));
/*
                db.addContact(new Notification(currentDate, packageName, "not", secondAnswer, thirdAnswer, fourthAnswer, fifthAnswer, sixthAnswer));
*/

// Reading all contacts
                Log.d("Reading: ", "Reading all contacts..");
                List<Notification> contacts = db.getAllContacts();
                for (Notification cn : contacts) {
                    String log = "Id: "+cn.getId()+" ,Time: " + cn.getTime()+ " ,Package: " + cn.getPackageName()+ "First " + cn.getFirstAnswer()+ "Second " + cn.getSecondAnswer()+ "Third " + cn.getThirdAnswer() + "Fourth " + cn.getFourthAnswer() + "Fifth " + cn.getFifthAnswer()+ "Sixth " + cn.getSixthAnswer();
                    // Writing Contacts to log
                    Log.d("Addition: ", log);
                }



                Toast.makeText(getApplicationContext(), "Thank you!!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(QpageThree.this, thankYouPage.class);
                startActivity(intent);
                overridePendingTransition(R.anim.enter, R.anim.exit);

            }
        });
    }

    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

}