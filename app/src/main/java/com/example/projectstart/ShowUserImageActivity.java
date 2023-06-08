package com.example.projectstart;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

import Data.DatabaseHelper;
import Model.UserImage;

public class ShowUserImageActivity extends AppCompatActivity {
    ImageView myUserImage;
    int currUserImageIndex;

    Button buttonForward,buttonBackwards;
    ArrayList<UserImage> userImagesListOfPhotos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_user_image);
        myUserImage=findViewById(R.id.show_image);
        buttonForward=findViewById(R.id.button_foward);
        buttonBackwards=findViewById(R.id.button_back);

        DatabaseHelper db=new DatabaseHelper(ShowUserImageActivity.this);
        userImagesListOfPhotos=db.getAllUserImages();
        currUserImageIndex=userImagesListOfPhotos.size()-1;
        UserImage lastUserImage=userImagesListOfPhotos.get(userImagesListOfPhotos.size()-1);
        byte [] imageBytesArray=lastUserImage.getLastTakenUserImage();

        if(imageBytesArray!=null)
        {
            Bitmap bitmap= BitmapFactory.decodeByteArray(imageBytesArray,0,imageBytesArray.length);
            myUserImage.setImageBitmap(bitmap);
        }
    }

    public void goToOneImageBeforeListener(View view){
        if(currUserImageIndex>0)
          currUserImageIndex-=1;
        else
           Toast.makeText(this, "You've got to First Image", Toast.LENGTH_SHORT).show();

        if(currUserImageIndex>=0) {
            UserImage CurrUserImage = userImagesListOfPhotos.get(currUserImageIndex);
            byte[] imageBytesArray = CurrUserImage.getLastTakenUserImage();

            if (imageBytesArray != null) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytesArray, 0, imageBytesArray.length);
                myUserImage.setImageBitmap(bitmap);
            }
        }else{
            Toast.makeText(this, "You've got to First Image", Toast.LENGTH_SHORT).show();
        }
    }

    public void goToNextImageListener(View view){
        if(currUserImageIndex<userImagesListOfPhotos.size()-1)
          currUserImageIndex+=1;
        if(currUserImageIndex<userImagesListOfPhotos.size()) {
            UserImage CurrUserImage = userImagesListOfPhotos.get(currUserImageIndex-1);
            byte[] imageBytesArray = CurrUserImage.getLastTakenUserImage();

            if (imageBytesArray != null) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytesArray, 0, imageBytesArray.length);
                myUserImage.setImageBitmap(bitmap);
            }
        }else{
            currUserImageIndex-=1;
            Toast.makeText(this, "You've got to last Image", Toast.LENGTH_SHORT).show();
        }
    }
}