package com.example.foodapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static androidx.core.app.ActivityCompat.startActivityForResult;

public class MusicItemsRecyclerAdapter extends RecyclerView.Adapter<MusicItemsRecyclerAdapter.ViewHolder> {

    private List<FItem> musicstorage;
    private LayoutInflater mInflater;
    int total_price=0;
    int total_items=0;
    Context context;
    Activity activity;
    private MusicItems mCallback;
    private CallbackInterface mCallbacks;
    final int PICK_IMAGE_MULTIPLE = 1;
    public interface CallbackInterface{

        /**
         * Callback invoked when clicked
         * @param position - the position
         * @param text - the text to pass back
         */
        void onHandleSelection(int position, String text);
    }

    MusicItemsRecyclerAdapter(Context context, List<FItem> musicStorage, MusicItems listener, int sum) {
        this.mInflater = LayoutInflater.from(context);
        this.musicstorage = musicStorage;
        this.context=context;
        this.mCallback = listener;
       this.total_price=sum;
        this.total_items=musicStorage.size();
        try{
            mCallbacks = (CallbackInterface) context;
        }catch(ClassCastException ex){
            //.. should log the error or throw and exception
            Log.e("MyAdapter","Must implement the CallbackInterface in the Activity", ex);
        }

    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.activity=activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.food_items_list, parent, false);
        return new ViewHolder(view);


    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final FItem musicStorage = musicstorage.get(position);
       holder.item_name_tv.setText(musicStorage.getName());
        holder.item_description.setText(musicStorage.getDesc());
        holder.price_text.setText(String.valueOf(musicStorage.getPrice()));
        Glide.with(context).load(musicStorage.getUrl()).into(holder.item_image_iv);
        Log.d("My_Log----------->","musicstorage.size():"+musicstorage.size()+"position:"+position);

        holder.increment_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int currentNos = Integer.parseInt(holder.item_count.getText().toString()) ;
                    holder.item_count.setText(String.valueOf(++currentNos));
                    total_items++;
                mCallback.itemclick(String.valueOf(total_items));
                total_price = total_price + musicStorage.getPrice();
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
                total_price = total_price - musicStorage.getPrice();
                mCallback.sum(String.valueOf(total_price));
                }


        });

        holder.uploadbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             //   getAllShownImagesPath(activity);
//                int newPosition = holder.getAdapterPosition();
//                total_price = total_price - (musicStorage.getPrice() * Integer.parseInt(holder.item_count.getText().toString()));
//                mCallback.sum(String.valueOf(total_price));
//                musicstorage.remove(newPosition);
//                total_items = total_items -  Integer.parseInt(holder.item_count.getText().toString());
//                mCallback.itemclick(String.valueOf(total_items));
//                notifyItemRemoved(newPosition);
//                notifyItemRangeChanged(newPosition, musicstorage.size());



            }
        });


    }

    @Override
    public int getItemCount() {
        return musicstorage.size();
    }
    private ArrayList<String> getAllShownImagesPath(Activity activity) {
        Uri uri;
        Cursor cursor;
        int column_index_data, column_index_folder_name;
        ArrayList<String> listOfAllImages = new ArrayList<String>();
        String absolutePathOfImage = null;
        uri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        String[] projection = { MediaStore.MediaColumns.DATA,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME };

        cursor = activity.getContentResolver().query(uri, projection, null,
                null, null);

        column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        column_index_folder_name = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
        while (cursor.moveToNext()) {
            absolutePathOfImage = cursor.getString(column_index_data);

            listOfAllImages.add(absolutePathOfImage);
        }
        return listOfAllImages;
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView item_name_tv, item_description, price_text,item_count,increment_btn,decrement_btn,uploadbtn;
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
            uploadbtn=itemView.findViewById(R.id.deletebtn);
        }

    }
}
