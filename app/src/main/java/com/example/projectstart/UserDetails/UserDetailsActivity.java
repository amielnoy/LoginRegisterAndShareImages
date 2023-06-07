package com.example.projectstart.UserDetails;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.example.projectstart.LoginActivity;
import com.example.projectstart.R;
import com.example.projectstart.RegisterUserActivity;

import java.util.ArrayList;

import Data.DataBaseInstance;
import Data.DatabaseHelper;
import Model.User;

public class UserDetailsActivity extends AppCompatActivity {

    Button button;
    TextView UserDetails;
    DatabaseHelper db;
    ListView listviewUsers;
    ArrayList<User> arrayOfUsers;
    Intent activityIntentLogin, activityIntentRegisterUser,activityIntentUserDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);
        //to be deleted
        //editTextCnfPassword = findViewById(R.id.editTextPhone);
        //button=findViewById(R.id.my_button);
       // UserDetails=findViewById(R.id.UserDetails_title);
        listviewUsers=findViewById(R.id.ListViewUsers);
         DatabaseHelper db =new DatabaseHelper(this);
        arrayOfUsers=db.getAllUsers();
        UserDetailsAdapter ad=new UserDetailsAdapter(this,R.layout.activity_user_details,arrayOfUsers);
        listviewUsers.setAdapter(ad);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(LoginActivity.getCurrentLoggedInMaster())
            getMenuInflater().inflate(R.menu.menu_take_a_pic_admin,menu);
        else
            getMenuInflater().inflate(R.menu.menu_for_regular_user,menu);

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
                Toast.makeText(this, "Entering User Details...", Toast.LENGTH_SHORT).show();
                activityIntentUserDetails = new Intent(this, UserDetailsActivity.class);
                startActivity(activityIntentUserDetails);
                return true;

            case R.id.menuitem_exit:
                Toast.makeText(this, "Exiting...", Toast.LENGTH_SHORT).show();
                this.finishAffinity();
                return true;

        }

        return super.onOptionsItemSelected(item);
    }

}
