package com.example.notifyservice;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;



public class DatabaseHandler extends SQLiteOpenHelper {
    private static final String TAG = DatabaseHandler.class.toString();

    static String id;
    // All Static variables

    // Database Version
    private static final int DATABASE_VERSION = 2;

    // Database Name
    public static final String DATABASE_NAME = "managerN";

    // Contacts table name
    private static final String TABLE_CONTACTS = "notificationsM";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "time";
    private static final String KEY_PH_NO = "packageName";
    private static final String KEY_1_Q = "firstAnswer";
    private static final String KEY_2_Q = "secondAnswer";
    private static final String KEY_3_Q = "thirdAnswer";
    private static final String KEY_4_Q = "fourthAnswer";
    private static final String KEY_5_Q = "fifthAnswer";
    private static final String KEY_6_Q = "sixthAnswer";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        context.getApplicationContext();
        id = Settings.Secure.getString(context.getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);

    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_PH_NO + " TEXT," + KEY_1_Q + " TEXT," + KEY_2_Q + " TEXT," + KEY_3_Q + " TEXT," + KEY_4_Q + " TEXT," + KEY_5_Q + " TEXT," + KEY_6_Q + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);

        // Create tables again
        onCreate(db);
    }

    // Adding new contact
    void addContact(Notification contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, contact.getId()); // Contact Name
        values.put(KEY_PH_NO, contact.getTime()); // Contact Phone
        values.put(KEY_PH_NO, contact.getPackageName()); // Package
        values.put(KEY_1_Q, contact.getFirstAnswer()); // 1st answer
        values.put(KEY_2_Q, contact.getSecondAnswer()); // 1st answer
        values.put(KEY_3_Q, contact.getThirdAnswer()); // 1st answer
        values.put(KEY_4_Q, contact.getFourthAnswer()); // 1st answer
        values.put(KEY_5_Q, contact.getFifthAnswer()); // 1st answer
        values.put(KEY_6_Q, contact.getSixthAnswer()); // 1st answer

        // Inserting Row
        db.insert(TABLE_CONTACTS, null, values);
        db.close(); // Closing database connection
    }

    // Getting All Contacts
    public List<Notification> getAllContacts() {
        List<Notification> contactList = new ArrayList<Notification>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Notification contact = new Notification();
                contact.setId(Integer.parseInt(cursor.getString(0)));
                contact.setTime(cursor.getString(1));
                contact.setPackageName(cursor.getString(2));
                contact.setFirstAnswer(cursor.getString(3));
                contact.setSecondAnswer(cursor.getString(4));
                contact.setThirdAnswer(cursor.getString(5));
                contact.setFourthAnswer(cursor.getString(6));
                contact.setFifthAnswer(cursor.getString(7));
                contact.setSixthAnswer(cursor.getString(8));
                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactList;
    }


    /**
     * Save database to a file
     * @param c the application context
     * @return the path to which the database file was saved
     */
    public static String exportDB(Context c)
    {

        System.out.println("Device: " + id);
        try
        {
            // the date formatter - change the date format used in filenames here.
            SimpleDateFormat formatter = new SimpleDateFormat("dd_MM_yyyy_HH_mm_ss");


            // get current db path ...
            File currentDBPath = c.getDatabasePath(DatabaseHandler.DATABASE_NAME);
            if(!currentDBPath.exists()) {
                Log.e(TAG, "DATABASE WAS NEVER CREATED - EXPORT SHALL FAIL!");
            }
            Log.i(TAG, "Database Path: "+currentDBPath.toString());
            // get context's cache path
            File cachePath = c.getCacheDir();
            Log.i(TAG, "Cache Path: "+cachePath.toString());
            // setup a temporary file to copy db (safer than sending directly the db file - as db might be modified concurently)
            File tmpDBPath = File.createTempFile(id, "."+formatter.format(new Date()), cachePath);
            System.out.println("Path: " + tmpDBPath);
            Log.i(TAG, "Temporary DB File Path: "+tmpDBPath.toString());

            // copy current database file to the temporary database file
            Log.i(TAG, "Backing up database '"+DATABASE_NAME+"'...");
            FileChannel src = new FileInputStream(currentDBPath).getChannel();
            FileChannel dst = new FileOutputStream(tmpDBPath).getChannel();
            final long bytesTransferred = dst.transferFrom(src, 0, src.size());
            Log.i(TAG, "Transferred "+bytesTransferred+"bytes!");
            src.close();
            dst.close();
            return tmpDBPath.toString();
        } catch (Exception e)
        {
            Log.i(TAG, "Failed to export db");
            e.printStackTrace();
        }
        return null;
    }

}

