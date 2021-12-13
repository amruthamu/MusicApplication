package com.example.foodapplication;

import android.graphics.drawable.Drawable;

import java.util.ArrayList;

public class FoodStorage {
    String foodname;
    String description;
    int price,item;
    public FoodStorage(){   }
    public FoodStorage(String foodname, String description, int price){
        this.foodname = foodname;
        this.description = description;
        this.price = price;
    }
    public  String getFoodname(){
        return this.foodname;
    }

    public void setFoodname(String foodname){
        this.foodname = foodname;
    }

    public String getDescription(){
        return this.description;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public int getPrice(){
        return this.price;
    }

    public void setPrice(int price){
        this.price = price;
    }
    public int getItem(){
        return this.item;
    }

    public void setItem(int item){
        this.item = item;
    }


}
