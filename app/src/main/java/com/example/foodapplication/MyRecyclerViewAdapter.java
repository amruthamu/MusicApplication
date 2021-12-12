package com.example.foodapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> implements FoodItems {

    private List<FoodStorage> foodstorage;
    private LayoutInflater mInflater;
    int minteger = 0;
    int sum=0;
    Context context;
    int[] myImageList;


    MyRecyclerViewAdapter(Context context, List<FoodStorage> foodStorage) {
        this.mInflater = LayoutInflater.from(context);
        this.foodstorage = foodStorage;
        this.context=context;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.food_items_list, parent, false);
        myImageList = new int[]{R.drawable.dosapicture, R.drawable.idlypicture,R.drawable.vadapicture,R.drawable.pizzapicture,R.drawable.panipuripicture,R.drawable.halwapicture,R.drawable.biriyanipicture,R.drawable.ricepicture,R.drawable.chanapicture,R.drawable.bhelpuripicture,R.drawable.masalapuripicture,R.drawable.mixtureimage,R.drawable.chipspicture,R.drawable.burgerpicture,R.drawable.sandwitch};

        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final FoodStorage foodStorage = foodstorage.get(position);
       holder.item_name_tv.setText(foodStorage.getFoodname());
        holder.item_description.setText(foodStorage.getDescription());
        holder.price_text.setText(String.valueOf(foodStorage.getPrice()));
        holder.item_image_iv.setImageResource(myImageList[position]);
       // sum += holder.price_text.setText(String.valueOf(foodStorage.getPrice()));
        holder.increment_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                minteger = minteger + 1;
                holder.item_count.setText(String.valueOf(minteger));

            }
        });
        holder.decrement_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                minteger = minteger - 1;
                holder.item_count.setText(String.valueOf(minteger));

            }
        });


    }

    @Override
    public int getItemCount() {
        return foodstorage.size();
    }

    @Override
    public void itemclick(int position,int items, int total) {

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView item_name_tv, item_description, price_text,item_count,increment_btn,decrement_btn;
        ImageView item_image_iv;

        ViewHolder(View itemView) {
            super(itemView);
            item_name_tv = itemView.findViewById(R.id.item_name_tv);
            item_description = itemView.findViewById(R.id.item_description);
            price_text = itemView.findViewById(R.id.price_text);
            item_count=itemView.findViewById(R.id.item_count);
            increment_btn=itemView.findViewById(R.id.increment_btn);
            decrement_btn=itemView.findViewById(R.id.decrement_btn);
            item_image_iv=itemView.findViewById(R.id.item_image_iv);
        }

    }
}
