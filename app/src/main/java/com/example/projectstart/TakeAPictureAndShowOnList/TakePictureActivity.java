package com.example.projectstart.TakeAPictureAndShowOnList;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectstart.LoginActivity;
import com.example.projectstart.R;
import com.example.projectstart.RegisterUserActivity;
import com.example.projectstart.UserDetails.UserDetailsActivity;

import java.io.ByteArrayOutputStream;

import Data.DatabaseHelper;

public class TakePictureActivity extends AppCompatActivity {
    public static int REQUESTR_IMAGE=100;
    TextView userNameTextView;
    ImageView takenImageView;
    Intent activityIntentLogin;
    Intent activityIntentRegisterUser;
    Intent activityIntentUserDetails;
    Intent activityIntentTakeAPicture;

    Button gotoDisplayAllUserImagesButton;
    byte [] imageByteArray;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_picture);
        userNameTextView=findViewById(R.id.textViewUseName);
        takenImageView=findViewById(R.id.take_picture_View);
        gotoDisplayAllUserImagesButton=findViewById(R.id.save_taken_image_button);
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
            imageByteArray =baos.toByteArray();
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
        dbHelper.add_user_details_with_img(LoginActivity.getCurrentLoggedInUserName(),this.imageByteArray);
    }

    public void DisplayAllUserImagesButtonListener(View view) {
        Intent intent=new Intent(this, DisplayAllUserImagesActivity.class);
        startActivity(intent);
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
            case R.id.menuitem_login:
                Toast.makeText(this, "login user...", Toast.LENGTH_SHORT).show();
                activityIntentLogin = new Intent(this, LoginActivity.class);
                startActivity(activityIntentLogin);
                return true;
            case R.id.menuitem_register:
                Toast.makeText(this, "Register user...", Toast.LENGTH_SHORT).show();
                activityIntentRegisterUser = new Intent(this, RegisterUserActivity.class);
                startActivity(activityIntentRegisterUser);
                return true;
            case R.id.menuitem_userdetails:
                Toast.makeText(this, "Show all Users Details...", Toast.LENGTH_SHORT).show();
                activityIntentUserDetails = new Intent(this, UserDetailsActivity.class);
                startActivity(activityIntentUserDetails);
                return true;
            case R.id.menuitem_take_picture:
                Toast.makeText(this, "Taking a picture...", Toast.LENGTH_SHORT).show();
                activityIntentTakeAPicture = new Intent(this, TakePictureActivity.class);
                startActivity(activityIntentTakeAPicture);
                return true;
            case R.id.menuitem_exit:
                Toast.makeText(this, "Exiting...", Toast.LENGTH_SHORT).show();
                this.finishAffinity();
                return true;


        }

        return super.onOptionsItemSelected(item);
    }
}