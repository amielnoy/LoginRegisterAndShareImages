package com.example.projectstart.TakeAPictureAndShowOnList;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectstart.LoginActivity;
import com.example.projectstart.R;

import java.io.ByteArrayOutputStream;

import Data.DatabaseHelper;

public class TakePictureActivity extends AppCompatActivity {
    public static int REQUESTR_IMAGE=100;
    TextView userNameTextView;
    ImageView takenImageView;

    byte [] imageArray;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_picture);
        userNameTextView=findViewById(R.id.textViewUseName);
        takenImageView=findViewById(R.id.take_picture_View);

    }

    byte[] contactArray;
    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data)
    {
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode==REQUESTR_IMAGE && resultCode== Activity.RESULT_OK)
        {
            Bitmap userBitmap=(Bitmap) data.getExtras().get("data");
            takenImageView.setImageBitmap(userBitmap);
            ByteArrayOutputStream baos=new ByteArrayOutputStream();
            userBitmap.compress(Bitmap.CompressFormat.JPEG,100,baos);
            imageArray=baos.toByteArray();
        }
    }

    public void imgClickHandler(View view)
    {
        Intent intentShowListOfPictures=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intentShowListOfPictures,REQUESTR_IMAGE);
    }

    public void buttonSaveWithImageListener(View view)
    {
        DatabaseHelper dbHelper=new DatabaseHelper(this);
        dbHelper.add_user_details_with_img(LoginActivity.getCurrentLoggedInUserName(),imagetArray);
    }
}