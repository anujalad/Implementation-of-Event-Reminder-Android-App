package com.example.anuja.androidproject.androidproject.DatabaseAccess;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.anuja.androidproject.androidproject.Others.Event;

import java.util.ArrayList;

/**
 * Created by anuja on 4/20/2016.
 */
public class DBConnection extends SQLiteOpenHelper {

    private static final int DB_VERSION=1;
    private static final String TABLE_NAME ="events";
    private static final String TABLE_NAME1 ="users";

    private static final String DATABASE_NAME="events.db";
    private static final String COL1="event_id";
    private static final String COL2="date";
    private static final String COL3="time";
    private static final String COL4="description";
    private static final String COL5="image";
    private static final String COL6="user";

   private static final String COL7="username";
   private static final String COL8="password";

   // private static final String COL6="user";

//    private static final String COL7="user_name";
//    private static final String COL8="password";

    public DBConnection(Context context) {
        super(context, DATABASE_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createQuery="CREATE TABLE "+ TABLE_NAME +" ("+COL1 +" INTEGER PRIMARY KEY AUTOINCREMENT," +COL2 + " TEXT ,"
                +COL3+" TEXT ," +COL4 +" TEXT, "+ COL5+" BLOB, "+COL6+" TEXT );";
        db.execSQL(createQuery);

        String query="CREATE TABLE " + TABLE_NAME1 +" ("+COL7 + " TEXT,"+COL8 +" TEXT );";
        db.execSQL(query);

        insertUsers("anuja", "anuja", db);
        insertUsers("harika", "harika", db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean insertEvent(String date, String time,String description, byte[] image,String user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL2, date);
        cv.put(COL3, time);
        cv.put(COL4, description);
        cv.put(COL5, image);
        cv.put(COL6, user);
        long res = db.insert(TABLE_NAME, null, cv);
        return res > 0;
    }

    public boolean insertUsers(String userName, String password, SQLiteDatabase db)
    {
//        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL7,userName);
        cv.put(COL8,password);
        long res=db.insert(TABLE_NAME1,null,cv);
        return res>0;
    }

    public ArrayList<Event> GetAllEvents(String user) {
        ArrayList<Event> events = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME + " where " + COL6+"='"+user+"'", null);

        while(res.moveToNext()) {
            Event currEvent = new Event();
            currEvent.setDate(res.getString(1));
            currEvent.setTime(res.getString(2));
            currEvent.setDescription(res.getString(3));
            currEvent.setImage(res.getBlob(4));
            events.add(currEvent);
        }
        res.close();
        return events;
    }

    public String GetId(String desc) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME + " where " + COL4 + " = '" + desc + "'", null);

        if(res.moveToNext()) {
            return res.getString(0);
        }
        res.close();
        return "";
    }

    public void DeleteEvent(String desc) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COL4 + " = ?", new String[]{desc});
    }

    public void UpdateEvent(String id, String date, String time,String description, byte[] image) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL2, date);
        cv.put(COL3, time);
        cv.put(COL4, description);
        cv.put(COL5, image);
        db.update(TABLE_NAME, cv, COL1 + " = ?", new String[]{id});
    }

    public boolean isUSerExists(String username,String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from users where " + COL7 + " = '" + username + "' and "+COL8+" = '"+password+"'", null);

        if(res.moveToNext()) {
            return true;
        }
        res.close();
        return false;
    }
}
