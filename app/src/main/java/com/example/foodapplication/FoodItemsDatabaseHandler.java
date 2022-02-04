package com.example.foodapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class FoodItemsDatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "foodStorage";
    private static final String TABLE_FOODS = "foodItems";
    private static final String FOOD_NAME = "foodName";
    private static final String FOOD_DESCRIPT = "description";
    private static final String FOOD_URL="url";
    private static final String FOOD_PRICE = "price";


    public FoodItemsDatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //3rd argument to be passed is CursorFactory instance
    }


    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_FOOD_TABLE = "CREATE TABLE " + TABLE_FOODS + "("
                + FOOD_NAME + " TEXT," + FOOD_DESCRIPT + " TEXT,"
                +FOOD_URL+"TEXT," +FOOD_PRICE + " INTEGER" + ")";
        db.execSQL(CREATE_FOOD_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FOODS);

        // Create tables again
        onCreate(db);
    }

    void deleteAllEnties(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_FOODS, null, null);
        db.close();
    }

    // code to add the new foodstorage
    void addfoodstorage(FItem foodstorage) {
        SQLiteDatabase db = this.getWritableDatabase();
//content valuesclass that matches a value to a String key. It contains multiple overloaded put methods that enforce type safety
        ContentValues values = new ContentValues();
        values.put(FOOD_NAME,foodstorage.getName());
        values.put(FOOD_DESCRIPT, foodstorage.getDesc());
        values.put(FOOD_URL,foodstorage.getUrl());
        values.put(FOOD_PRICE, foodstorage.getPrice().toString());


        // Inserting Row
        db.insert(TABLE_FOODS, null, values);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
    }


    // code to get all foodstorages in a list view
    public List<FItem> getAllfoodstorages() {
        List<FItem> foodstorageList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_FOODS;

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
