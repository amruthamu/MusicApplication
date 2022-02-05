package com.example.foodapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class MusicItemsDatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "ItemStorage";
    private static final String TABLE_MUSICS = "MusicItems";
    private static final String MUSIC_NAME = "ItemName";
    private static final String MUSIC_DESCRIPT = "description";
    private static final String MUSIC_URL="url";
    private static final String MUSIC_PRICE = "price";


    public MusicItemsDatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //3rd argument to be passed is CursorFactory instance
    }


    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_MUSIC_TABLE = "CREATE TABLE " + TABLE_MUSICS + "("
                + MUSIC_NAME + " TEXT," + MUSIC_DESCRIPT + " TEXT,"
                +MUSIC_URL+"TEXT," +MUSIC_PRICE + " INTEGER" + ")";
        db.execSQL(CREATE_MUSIC_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MUSICS);

        // Create tables again
        onCreate(db);
    }

    void deleteAllEnties(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_MUSICS, null, null);
        db.close();
    }

    // code to add the new foodstorage
    void addfoodstorage(FItem foodstorage) {
        SQLiteDatabase db = this.getWritableDatabase();
//content valuesclass that matches a value to a String key. It contains multiple overloaded put methods that enforce type safety
        ContentValues values = new ContentValues();
        values.put(MUSIC_NAME,foodstorage.getName());
        values.put(MUSIC_DESCRIPT, foodstorage.getDesc());
        values.put(MUSIC_URL,foodstorage.getUrl());
        values.put(MUSIC_PRICE, foodstorage.getPrice().toString());


        // Inserting Row
        db.insert(TABLE_MUSICS, null, values);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
    }


    // code to get all foodstorages in a list view
    public List<FItem> getAllfoodstorages() {
        List<FItem> foodstorageList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_MUSICS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        //We can retrieve anything from database using an object of the Cursor class. We will call a method of this class called rawQuery and it will return a resultset with the cursor pointing to the table.
        if (cursor.moveToFirst()) {
            do {
                FItem foodstorage = new FItem();
                foodstorage.setName(cursor.getString(0));
                foodstorage.setDesc(cursor.getString(1));
                foodstorage.setUrl(cursor.getString(2));
                foodstorage.setPrice(cursor.getInt(3));
                foodstorageList.add(foodstorage);
            } while (cursor.moveToNext());
        }

        // return foodstorage list
        return foodstorageList;
    }

}
