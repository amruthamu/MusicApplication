package com.example.foodapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "foodStorage";
    private static final String TABLE_FOODS = "foodItems";
    private static final String FOOD_NAME = "foodname";
    private static final String FOOD_DESCRIPT = "description";
    private static final String FOOD_PRICE = "price";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //3rd argument to be passed is CursorFactory instance
    }


    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_FOOD_TABLE = "CREATE TABLE " + TABLE_FOODS + "("
                + FOOD_NAME + " TEXT," + FOOD_DESCRIPT + " TEXT,"
                + FOOD_PRICE + " INTEGER" + ")";
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

    // code to add the new foodstorage
    void addfoodstorage(FoodStorage foodstorage) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FOOD_NAME,foodstorage.getFoodname());
        values.put(FOOD_DESCRIPT, foodstorage.getDescription()); // foodstorage Name
        values.put(FOOD_PRICE, foodstorage.getPrice()); // foodstorage price

        // Inserting Row
        db.insert(TABLE_FOODS, null, values);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
    }


    // code to get all foodstorages in a list view
    public List<FoodStorage> getAllfoodstorages() {
        List<FoodStorage> foodstorageList = new ArrayList<FoodStorage>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_FOODS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                FoodStorage foodstorage = new FoodStorage();
                foodstorage.setFoodname(cursor.getString(0));
                foodstorage.setDescription(cursor.getString(1));
                foodstorage.setPrice(Integer.parseInt(cursor.getString(2)));
                foodstorageList.add(foodstorage);
            } while (cursor.moveToNext());
        }

        // return foodstorage list
        return foodstorageList;
    }

}
