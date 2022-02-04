package com.example.foodapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;


import java.util.List;

public class MainActivity extends AppCompatActivity implements FoodItems {
    ImageView food_app_bar_back_iv;
    TextView food_app_item_count,price_value_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        food_app_bar_back_iv=findViewById(R.id.food_app_bar_back_iv);
        food_app_item_count=findViewById(R.id.food_app_item_count);
        price_value_tv=findViewById(R.id.price_value_tv);
        FoodItemsDatabaseHandler db = new FoodItemsDatabaseHandler(this);
        if(db.getAllfoodstorages().size() !=0){
            // we have to clear the database
            db.deleteAllEnties();
        }

        Gson gson = new Gson();
        FoodItemsNew foodItemsNew=gson.fromJson(Utilts.getJsonFromAssets(this,"response.json"),FoodItemsNew.class);
        Log.d("FoodStorage", "onCreate: "+foodItemsNew);
        List<FItem> fItem = foodItemsNew.getfItems();





        for(int i=0;i<fItem.size();i++){
            db.addfoodstorage(fItem.get(i));
        }

       List<FItem> foodStorage = db.getAllfoodstorages();

        food_app_bar_back_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_new);
        FoodItemsRecyclerAdapter adapter = new FoodItemsRecyclerAdapter(this,fItem,this,getTotalPrice(fItem));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        food_app_item_count.setText(String.valueOf(fItem.size()));
        price_value_tv.setText(String.valueOf(getTotalPrice(fItem)));
    }
    private int getTotalPrice(List<FItem> fItems){
        int sum=0;
        for(int i=0;i<fItems.size();i++){
            sum=sum+fItems.get(i).getPrice();
        }
        return sum;
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