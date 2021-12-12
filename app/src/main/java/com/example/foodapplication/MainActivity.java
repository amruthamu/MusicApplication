package com.example.foodapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DatabaseHandler db = new DatabaseHandler(this);


        db.addfoodstorage(new FoodStorage("Dosa","regular breakfast",45));
        db.addfoodstorage(new FoodStorage("Idly","regular breakfast",30));
        db.addfoodstorage(new FoodStorage("vada","regular breakfast",10));
        db.addfoodstorage(new FoodStorage("pizza","regular breakfast",45));
        db.addfoodstorage(new FoodStorage("panipoori","regular snack",40));
        db.addfoodstorage(new FoodStorage("halwa","regular snack",40));
        db.addfoodstorage(new FoodStorage("biriyani","regular snack",100));
        db.addfoodstorage(new FoodStorage("rice","regular snack",80));
        db.addfoodstorage(new FoodStorage("chana","regular snack",60));
        db.addfoodstorage(new FoodStorage("bhelpuri","regular snack",35));
        db.addfoodstorage(new FoodStorage("masalapuri","regular snack",70));
        db.addfoodstorage(new FoodStorage("mixture","regular snack",30));
        db.addfoodstorage(new FoodStorage("chips","regular snack",40));
        db.addfoodstorage(new FoodStorage("burger","regular snack",100));
        db.addfoodstorage(new FoodStorage("sandwitch","regular snack",80));

       List<FoodStorage> foodStorage = db.getAllfoodstorages();


        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_new);
        MyRecyclerViewAdapter adapter = new MyRecyclerViewAdapter(this,foodStorage);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

}