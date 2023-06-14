package com.example.projectstart.TakeAPictureAndShowOnList;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.projectstart.LoginActivity.LoginActivity;
import com.example.projectstart.MainActivity.MainActivity;
import com.example.projectstart.R;
import com.example.projectstart.RegisterUserActivity.RegisterUserActivity;
import com.example.projectstart.UserImagesListAndGridViews.ShowUserImagesAndUserNameActivity;

import java.util.ArrayList;

import Data.DatabaseHelper;
import Model.UserImage;

public class ShowUserImageActivity extends AppCompatActivity {
    ImageView myUserImage;
    int currUserImageIndex;
    Intent activityIntentLogin;
    Intent activityIntentRegisterUser;
    Intent activityIntentTakeAPicture;
    Intent activityIntentShowAPicture;
    Intent activityIntentMain;
    Intent activityIntentShowUserImages;
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
            Toast.makeText(this, "You've got to Last Image", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(LoginActivity.getCurrentLoggedInMaster())
            getMenuInflater().inflate(R.menu.menu_take_a_pic_admin,menu);
        else
            getMenuInflater().inflate(R.menu.menu_take_a_pic,menu);

        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.menuitem_main_screen:
                Toast.makeText(this, "Play Music...", Toast.LENGTH_SHORT).show();
                activityIntentMain = new Intent(this, MainActivity.class);
                startActivity(activityIntentMain);
                return true;
            case R.id.menuitem_login:
                Toast.makeText(this, "Login user...", Toast.LENGTH_SHORT).show();
                activityIntentLogin = new Intent(this, LoginActivity.class);
                startActivity(activityIntentLogin);
                return true;
            case R.id.menuitem_register:
                Toast.makeText(this, "Register user...", Toast.LENGTH_SHORT).show();
                activityIntentRegisterUser = new Intent(this, RegisterUserActivity.class);
                startActivity(activityIntentRegisterUser);
                return true;
            case R.id.menuitem_take_picture:
                Toast.makeText(this, "Taking a picture...", Toast.LENGTH_SHORT).show();
                activityIntentTakeAPicture = new Intent(this, TakePictureActivity.class);
                startActivity(activityIntentTakeAPicture);
                return true;
            case R.id.menuitem_show_picture:
                Toast.makeText(this, "Displaying user picture...", Toast.LENGTH_SHORT).show();
                activityIntentShowAPicture = new Intent(this, ShowUserImageActivity.class);
                startActivity(activityIntentShowAPicture);
                return true;
            case R.id.menuitem_show_user_images:
                Toast.makeText(this, "Displaying user Images in Gridview...", Toast.LENGTH_SHORT).show();
                activityIntentShowUserImages = new Intent(this, ShowUserImagesAndUserNameActivity.class);
                startActivity(activityIntentShowUserImages);
                return true;
            case R.id.menuitem_exit:
                Toast.makeText(this, "Exiting...", Toast.LENGTH_SHORT).show();
                this.finishAffinity();
                return true;


        }

        return super.onOptionsItemSelected(item);
    }
}