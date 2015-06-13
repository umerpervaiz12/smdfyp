package com.example.umer.server; /**
 * Created by Umer on 6/6/2015.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.inputmethodservice.Keyboard;
import android.provider.SyncStateContract;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Ammar on 6/4/2015.
 */
public class DatabaseOperations extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "Emergency";

    // Contacts table name
    private static final String TABLE_NAME = "Emergencyinfo";

    private static final String KEY_PH_NO = "ph_no";
    private static final String KEY_DETAILS = "details";
    private static final String KEY_LNG = "lng";
    private static final String KEY_LAT = "lat";

    public String CREATE_QUERY="CREATE TABLE "+ TABLE_NAME +" ( "+
            KEY_PH_NO+" TEXT, "+ KEY_DETAILS+" TEXT, "+ KEY_LNG+" TEXT, "+ KEY_LAT+" TEXT );";

    public DatabaseOperations(Context context) {
        super(context, DATABASE_NAME,null,DATABASE_VERSION);
        Log.d("Database operations", "Database created");
    }
    @Override
    public void onCreate(SQLiteDatabase sdb) {
        sdb.execSQL(CREATE_QUERY);
        Log.d("Database operations", "Table created");
    }
    @Override
    public void onUpgrade(SQLiteDatabase sdb,int oldVersion,int newVersion) {
        sdb.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        // Create tables again
        onCreate(sdb);
    }

    public void addTableData(TableData tabledata) {
        SQLiteDatabase sdb = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_PH_NO,tabledata.getphonenumber()); // Contact Name
        values.put(KEY_DETAILS, tabledata.getdetails()); // Contact Phone Number
        values.put(KEY_LNG,tabledata.getlongitude()); // Contact Name
        values.put(KEY_LAT, tabledata.getlatitude()); // Contact Phone Number
        // Inserting Row
        sdb.insert(TABLE_NAME, null, values);
        sdb.close(); // Closing database connection
    }


    // Getting All Contacts
    public ArrayList<TableData> getAllContacts() {
        ArrayList<TableData> TableList = new ArrayList<TableData>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                TableData tabledata = new TableData();
                tabledata.setphonenumber(cursor.getString(0));
                tabledata.setdetails(cursor.getString(1));
                tabledata.setlongitude(cursor.getString(2));
                tabledata.setlatitude(cursor.getString(3));
                // Adding contact to list
                TableList.add(tabledata);
            } while (cursor.moveToNext());
        }

        // return contact list
        return TableList;
    }

}
