package com.example.foodapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class DetailActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);

        TextView textView = (TextView) findViewById(R.id.tv_detail_text);

        if(getIntent() != null && getIntent().getExtras() != null) {
            Bundle extras = getIntent().getExtras();
            String text = extras.getString("Text");
            int position = extras.getInt("Position");

            textView.setText(String.format("%s at position %d", text, position));

            // .. For this purpose set result okay and use position as part of data when Activity finishes
            // Which will in turn invoke the onActivityResult method in the calling activity
            Intent resultIntent = new Intent();
            resultIntent.putExtra("Position", position);
            setResult(RESULT_OK, resultIntent);
        }
    }
}
