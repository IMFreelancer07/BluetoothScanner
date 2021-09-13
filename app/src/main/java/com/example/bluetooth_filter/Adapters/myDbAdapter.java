package com.example.bluetooth_filter.Adapters;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public  class myDbAdapter extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "mydb.db";    // Database Name
    private static final String TABLE_NAME = "mytbl";   // Table Name
    private static final int DATABASE_Version = 1;    // Database Version
    public static final String UID="_id";     // Column I (Primary Key)
    public static final String TP = "tp";    //Column II
    public static final String RFID= "rfid";    // Column III
    public static final String TIME = "time"; // Column IV
    public static final String COLUMN_STATUS = "status"; //Column V


    public myDbAdapter (Context context)
    {
        super(context, DATABASE_NAME,null,DATABASE_Version);
    }



    @Override
    public void onCreate(SQLiteDatabase DB)
    {
        String createtbl = "create Table "+TABLE_NAME+"("+UID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+TP+" VARCHAR(255) ,"+ RFID+" VARCHAR(225) , "+TIME+" VARCHAR(255),"+COLUMN_STATUS+" TINYINT, UNIQUE ("+TP+","+RFID+"))";
        DB.execSQL(createtbl);
    }

    @Override
    public void onUpgrade (SQLiteDatabase DB, int oldverion, int newverion)
    {
        DB.execSQL("drop table if exists "+TABLE_NAME);
    }


    public long insertData(String tp, String rfid, String time, int status)
    {
        SQLiteDatabase dbb = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TP, tp);
        contentValues.put(RFID, rfid);
        contentValues.put(TIME, time);
        contentValues.put(COLUMN_STATUS, status);
        long id = dbb.insert(TABLE_NAME, null , contentValues);
        if (id<=0)
        {

//            Toast.makeText(,"Insertion Unsuccessful", Toast.LENGTH_LONG).show();
            Log.i("INSERTION", "FAILED");
            return id;

        } else
        {
//            Toast.makeText(context , "Insertion Successful", Toast.LENGTH_LONG).show();
            Log.i("INSERTION", "Successful");
            return id;
        }

    }


    public boolean updateDataStatus(int id, int status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_STATUS, status);
        db.update(TABLE_NAME, contentValues, UID + "=" + id, null);
        db.close();
        return true;
    }

    public Cursor getData ()
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from "+TABLE_NAME+"", null);
        return cursor;

    }

    public Cursor getUnsyncedData() {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_STATUS + " = 0;";
        Cursor c = db.rawQuery(sql, null);
        return c;
    }

}