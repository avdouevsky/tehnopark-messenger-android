package com.mshvdvskgmail.technoparkmessenger.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.mshvdvskgmail.technoparkmessenger.Fragments;
import com.mshvdvskgmail.technoparkmessenger.R;
import com.mshvdvskgmail.technoparkmessenger.adapters.ChatListAdapter;
import com.squareup.picasso.Picasso;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

/**
 * Created by Admin on 11.05.2017.
 */

public class ViewerActivity extends AppCompatActivity {
    private final static String TAG = ViewerActivity.class.toString();


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewer);
        setListeners();
        setContent();
    }

    private void setListeners(){
        FrameLayout flBackButton = (FrameLayout) findViewById(R.id.activity_viewer_fl_back);
        flBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void setContent(){
        ImageView imagePicture = (ImageView) findViewById(R.id.activity_viewer_image_picture);
        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        if (url!=null){
            Log.d(TAG, "wow url =  " + url);
            Picasso.with(this)
                    .load(url)
                    .fit()
                    .centerCrop()
                    .into(imagePicture);
        }
    }

}
