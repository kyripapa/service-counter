package com.example.notifyservice;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;



public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 2;

    // Database Name
    private static final String DATABASE_NAME = "managerN";

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

}

