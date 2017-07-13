package com.example.notifyservice;

/******************************************************************************

 * Contributors:
 * Andreas Komninos, University of Strathclyde - code implementation
 * http://www.komninos.info
 * http://mobiquitous.cis.strath.ac.uk
 *****************************************************************************/

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

/**
 * sets up a process for automatically uploading the data to a web server. It's called by an alarm that is scheduled in the SoftKeyboardService class
 * @author ako2
 *
 */
public class UploadDataReceiver extends BroadcastReceiver {
    public static final String TAG = UploadDataReceiver.class.toString();
    Context c;

    /**
     * Checks if the user has enabled the automatic upload mode and we are connected, if so on both accounts, upload the data
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
        boolean insert = intent.getBooleanExtra("insert", false);

        c=context;
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(c);

		/*
		 * if the received intent is from the alarm and manual mode is on, do nothing
		 */



        //Log.i("Alarm fired", "time: "+System.currentTimeMillis());
        if(isNetworkAvailable(context))
        {
            // MAKE SURE DB IS CREATED PRIO TO EXPORTING AND SENDING IT
            DatabaseHandler dbm = new DatabaseHandler(context);
            {
                final SQLiteDatabase dbi = dbm.getWritableDatabase();
                dbi.close();
            }

            //save the file
            String fileToUpload=exportDB(context);
            //send the file to the server - depending on the results, the asynctask will truncate db on success.
            HttpPostAsyncTask task = new HttpPostAsyncTask(dbm, insert, c);
            task.execute(fileToUpload);

        }
        else
        {
            //Log.i("Alarm event", "Network Not Available");
            //Toast.makeText(c, "Can't upload: No Network. Try saving locally!", Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * Checks if the device is connected to the Internet via Wi-Fi
     * @param c the application context
     * @return true if connected via Wi-Fi, else returns false
     */
    private boolean isNetworkAvailable(Context c) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();

    }

    /**
     * Save database to a file
     * @param c the application context
     * @return the path to which the database file was saved
     */
    private String exportDB(Context c)
    {
        return DatabaseHandler.exportDB(c);
    }


}

