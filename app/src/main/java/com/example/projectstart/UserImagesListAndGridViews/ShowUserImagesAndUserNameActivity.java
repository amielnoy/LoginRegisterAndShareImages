package com.example.projectstart.UserImagesListAndGridViews;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.projectstart.LoginActivity.LoginActivity;
import com.example.projectstart.MainActivity.MainActivity;
import com.example.projectstart.R;
import com.example.projectstart.RegisterUserActivity.RegisterUserActivity;
import com.example.projectstart.TakeAPictureAndShowOnList.ShowUserImageActivity;
import com.example.projectstart.TakeAPictureAndShowOnList.TakePictureActivity;

import java.util.ArrayList;

import Data.DatabaseHelper;
import Model.User;
import Model.UserImage;

public class ShowUserImagesAndUserNameActivity extends AppCompatActivity {
    Intent activityIntentLogin;
    Intent activityIntentRegisterUser;
    Intent activityIntentTakeAPicture;
    Intent activityIntentShowAPicture;

    Intent activityIntentShowUserImages;
    Intent activityIntentMain;
    ArrayList<User> arrayOfUsers;
    ArrayList<UserImage> arrayOfUserImages;
    ListView usersListView;

    GridViewAdapter gridUserImagesViewAdapter;

    GridView userImagesGridView;
    // Declare db here to make it accessible in the OnItemClickListener
    DatabaseHelper db;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_images_list_and_grid_views);
        usersListView=findViewById(R.id.users_list_view);
        userImagesGridView=findViewById(R.id.user_images_grid_view);

        try {
            // Initialize the DatabaseHelper
            db = new DatabaseHelper(this);

            // Fetch initial data from the database
            arrayOfUsers = db.getAllUsers();
            arrayOfUserImages = db.getAllUserImages();

            // Set up the Users ListView
            UsersAdapter usersAdapter = new UsersAdapter(this, arrayOfUsers);
            usersListView.setAdapter(usersAdapter);

            // Set up the User Images GridView
            gridUserImagesViewAdapter = new GridViewAdapter(this, arrayOfUserImages);
            userImagesGridView.setAdapter(gridUserImagesViewAdapter);

        } catch (SQLiteException e) {
            // Log the error for debugging purposes
            Log.e("DatabaseError", "Error accessing database: " + e.getMessage());
            // Inform the user that something went wrong
            Toast.makeText(this, "Database error. Could not load data.", Toast.LENGTH_LONG).show();
            // Optionally, you could finish the activity if it cannot function without the database
            // finish();
        }


        usersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    String selectedUserName =((User)parent.getItemAtPosition(position)).getUserName();
                    Toast.makeText(getApplicationContext(), "Selected item: " + selectedUserName, Toast.LENGTH_SHORT).show();

                    // Check if db was initialized before using it
                    if (db != null) {
                        arrayOfUserImages = db.getAllSpecificUserImages(selectedUserName);
                        gridUserImagesViewAdapter.clear();
                        gridUserImagesViewAdapter.addAll(arrayOfUserImages);
                        // No need to set the adapter again, just notify it that the data has changed
                        gridUserImagesViewAdapter.notifyDataSetChanged();
                    }
                } catch (SQLiteException e) {
                    Log.e("DatabaseError", "Error filtering user images: " + e.getMessage());
                    Toast.makeText(getApplicationContext(), "Error loading images for this user.", Toast.LENGTH_SHORT).show();
                }
            }
        });
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
                Toast.makeText(this, "login user...", Toast.LENGTH_SHORT).show();
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