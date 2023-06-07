package com.example.projectstart.TakeAPictureAndShowOnList;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projectstart.R;

import java.util.ArrayList;

import Data.DatabaseHelper;
import Model.UserImage;

public class DisplayAllUserImagesActivity extends AppCompatActivity {
    ListView listViewsUserImages;
    ArrayList<UserImage>userImagesArray;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_display_all_user_images);
            listViewsUserImages=findViewById(R.id.Listviewuserimages);
            DatabaseHelper db= new DatabaseHelper(this);
            userImagesArray=db.getAllContacts();
            UserImagesAdapter ad=new UserImagesAdapter(this,R.layout.activity_user_image_layout,userImagesArray);
            listViewsUserImages.setAdapter(ad);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}