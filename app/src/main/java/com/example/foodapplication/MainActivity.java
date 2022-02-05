package com.example.foodapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Intent;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;


import java.util.List;

public class MainActivity extends AppCompatActivity implements MusicItems {
    ImageView food_app_bar_back_iv,IVPreviewImage;
    TextView food_app_item_count,price_value_tv,BSelectImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        food_app_bar_back_iv=findViewById(R.id.food_app_bar_back_iv);
        food_app_item_count=findViewById(R.id.food_app_item_count);
        price_value_tv=findViewById(R.id.price_value_tv);
        IVPreviewImage=findViewById(R.id.item_image_iv);
        BSelectImage=findViewById(R.id.uploadbtn);

        MusicItemsDatabaseHandler db = new MusicItemsDatabaseHandler(this);
        if(db.getAllfoodstorages().size() !=0){
            // we have to clear the database
            db.deleteAllEnties();
        }

        Gson gson = new Gson();
        MusicItemsNew foodItemsNew=gson.fromJson(Utilts.getJsonFromAssets(this,"response.json"), MusicItemsNew.class);
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
        MusicItemsRecyclerAdapter adapter = new MusicItemsRecyclerAdapter(this,fItem,this,getTotalPrice(fItem));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(adapter);
        BSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageChooser();
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                FItem deletedCourse = fItem.get(viewHolder.getAdapterPosition());
                int position = viewHolder.getAdapterPosition();
                fItem.remove(viewHolder.getAdapterPosition());

                adapter.notifyItemRemoved(viewHolder.getAdapterPosition());

                Snackbar.make(recyclerView, deletedCourse.getName(), Snackbar.LENGTH_LONG).setAction("Undo", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        fItem.add(position, deletedCourse);
                        adapter.notifyItemInserted(position);
                    }
                }).show();
            }
        }).attachToRecyclerView(recyclerView);


        food_app_item_count.setText(String.valueOf(fItem.size()));
        price_value_tv.setText(String.valueOf(getTotalPrice(fItem)));
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            // compare the resultCode with the
            // SELECT_PICTURE constant
            if (requestCode == 200) {
                // Get the url of the image from data
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    // update the preview image in the layout
                    IVPreviewImage.setImageURI(selectedImageUri);
                }
            }
        }
    }
    void imageChooser() {

        // create an instance of the
        // intent of the type image
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);

        // pass the constant to compare it
        // with the returned requestCode
        startActivityForResult(Intent.createChooser(i, "Select Picture"), 200);
    }
    @Override
    public void onHandleSelection(int position, String text) {

        Toast.makeText(this, "Selected item in list "+ position + " with text "+ text, Toast.LENGTH_SHORT).show();

        // ... Start a new Activity here and pass the values
        Intent secondActivity = new Intent(MainActivity.this, DetailActivity.class);
        secondActivity.putExtra("Text",text);
        secondActivity.putExtra("Position", position);
        startActivityForResult(secondActivity, 200);
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