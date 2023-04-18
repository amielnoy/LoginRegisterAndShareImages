package com.example.projectstart;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import Data.DataBaseInstance;
import Data.DatabaseHelper;
import Model.User;
import Utill.Constants;

public class UserDetailsActivity extends AppCompatActivity {

    Button buttonLogin;
    TextView UserDetails;
    DatabaseHelper db;
    ListView listview;
    Intent activityIntentLogin, activityIntentRegisterUser,activityIntentUserDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);
        //to be deleted
        //editTextCnfPassword = findViewById(R.id.editTextPhone);
        buttonLogin=findViewById(R.id.UserDetails_title_button);
        UserDetails=findViewById(R.id.UserDetails_title);
        listview=findViewById(R.id.ListView);
        db= DataBaseInstance.getDatabaseInstance();

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String role=editText
                Cursor userDetailsqueryResult = db.getUserDetails();
                //listview
                User user = new User();
                Log.d("IDdetails", String.valueOf(user.getId()));
            }
        });


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);

        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.menuitem_login:
                Toast.makeText(this, "login user...", Toast.LENGTH_SHORT).show();
                activityIntentLogin = new Intent(this, UserDetailsActivity.class);
                startActivity(activityIntentLogin);
                return true;
            case R.id.menuitem_register:
                Toast.makeText(this, "Register user...", Toast.LENGTH_SHORT).show();
                activityIntentRegisterUser = new Intent(this, RegisterUserActivity.class);
                startActivity(activityIntentRegisterUser);
                return true;
            case R.id.menuitem_search:
                Toast.makeText(this, "searching...", Toast.LENGTH_SHORT).show();
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
