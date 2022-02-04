package com.example.foodapplication;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;

import java.util.List;

public class FoodItemsRecyclerAdapter extends RecyclerView.Adapter<FoodItemsRecyclerAdapter.ViewHolder> {

    private List<FItem> foodstorage;
    private LayoutInflater mInflater;
    int total_price=0;
    int total_items=0;
    Context context;
    private FoodItems mCallback;


    FoodItemsRecyclerAdapter(Context context, List<FItem> foodStorage, FoodItems listener, int sum) {
        this.mInflater = LayoutInflater.from(context);
        this.foodstorage = foodStorage;
        this.context=context;
        this.mCallback = listener;
       this.total_price=sum;
        this.total_items=foodStorage.size();

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.food_items_list, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final FItem foodStorage = foodstorage.get(position);
       holder.item_name_tv.setText(foodStorage.getName());
        holder.item_description.setText(foodStorage.getDesc());
        holder.price_text.setText(String.valueOf(foodStorage.getPrice()));
        Glide.with(context).load(foodStorage.getUrl()).into(holder.item_image_iv);
        Log.d("My_Log----------->","foodstorage.size():"+foodstorage.size()+"position:"+position);
        holder.increment_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int currentNos = Integer.parseInt(holder.item_count.getText().toString()) ;
                    holder.item_count.setText(String.valueOf(++currentNos));
                    total_items++;
                mCallback.itemclick(String.valueOf(total_items));
                total_price = total_price + foodStorage.getPrice();
                mCallback.sum(String.valueOf(total_price));
                }


        });
        holder.decrement_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int currentNos = Integer.parseInt(holder.item_count.getText().toString()) ;
                    holder.item_count.setText(String.valueOf(--currentNos));
                    total_items--;
                mCallback.itemclick(String.valueOf(total_items));
                total_price = total_price - foodStorage.getPrice();
                mCallback.sum(String.valueOf(total_price));
                }


        });
        holder.deletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int newPosition = holder.getAdapterPosition();
                total_price = total_price - (foodStorage.getPrice() * Integer.parseInt(holder.item_count.getText().toString()));
                mCallback.sum(String.valueOf(total_price));
                foodstorage.remove(newPosition);
                total_items = total_items -  Integer.parseInt(holder.item_count.getText().toString());
                mCallback.itemclick(String.valueOf(total_items));
                notifyItemRemoved(newPosition);
                notifyItemRangeChanged(newPosition, foodstorage.size());

            }
        });


    }

    @Override
    public int getItemCount() {
        return foodstorage.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView item_name_tv, item_description, price_text,item_count,increment_btn,decrement_btn,deletebtn;
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
            deletebtn=itemView.findViewById(R.id.deletebtn);
        }

    }
}
