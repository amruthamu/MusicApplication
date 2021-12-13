package com.example.foodapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements FoodItems {
    ImageView food_app_bar_back_iv;
    TextView food_app_item_count,price_value_tv;
    //private FoodItems mCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        food_app_bar_back_iv=findViewById(R.id.food_app_bar_back_iv);
        food_app_item_count=findViewById(R.id.food_app_item_count);
        price_value_tv=findViewById(R.id.price_value_tv);
        DatabaseHandler db = new DatabaseHandler(this);
        if(db.getAllfoodstorages().size() !=0){
            // we have ro clear the database
            db.deleteALlEnties();
        }


        db.addfoodstorage(new FoodStorage("Dosa","regular breakfast",45));
        db.addfoodstorage(new FoodStorage("Idly","regular breakfast",30));
        db.addfoodstorage(new FoodStorage("Vada","regular breakfast",10));
        db.addfoodstorage(new FoodStorage("Pizza","regular breakfast",45));
        db.addfoodstorage(new FoodStorage("Panipoori","regular snack",40));
        db.addfoodstorage(new FoodStorage("Halwa","regular snack",40));
        db.addfoodstorage(new FoodStorage("Biriyani","regular snack",100));
        db.addfoodstorage(new FoodStorage("Rice","regular snack",80));
        db.addfoodstorage(new FoodStorage("Chana","regular snack",60));
        db.addfoodstorage(new FoodStorage("Bhelpuri","regular snack",35));
        db.addfoodstorage(new FoodStorage("Masalapuri","regular snack",70));
        db.addfoodstorage(new FoodStorage("Mixture","regular snack",30));
        db.addfoodstorage(new FoodStorage("Chips","regular snack",40));
        db.addfoodstorage(new FoodStorage("Burger","regular snack",100));
        db.addfoodstorage(new FoodStorage("Sandwitch","regular snack",80));

       List<FoodStorage> foodStorage = db.getAllfoodstorages();

        food_app_bar_back_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_new);
        MyRecyclerViewAdapter adapter = new MyRecyclerViewAdapter(this,foodStorage,this);
        Log.d("My_Log----------->",String.valueOf(foodStorage.size()));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void itemclick(String items) {
    food_app_item_count.setText(items);


    }

    @Override
    public void sum(String sum) {
        price_value_tv.setText(sum);

    }
}