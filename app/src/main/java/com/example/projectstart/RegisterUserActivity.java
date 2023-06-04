package com.example.projectstart;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import Data.DatabaseHelper;
import Model.User;
import Utill.Constants;

public class RegisterUserActivity extends AppCompatActivity {
    DatabaseHelper db;
    EditText editTextUsername, editTextRole, editTextPassword, editTextCnfPassword,editTextEmail;
    Button buttonRegister;


    Intent activityIntentRegisterUser,activityIntentLogin;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);
        //Create new empty DB
        db = new DatabaseHelper(this);
        editTextUsername = findViewById(R.id.editTextUserName);
        //editTextEmail = findViewById(R.id.editTextTextEmailAddress);
        editTextPassword = findViewById(R.id.PasswordEdit);
        //editTextCnfPassword = findViewById(R.id.editTextPhone);
        buttonRegister=findViewById(R.id.button);
        editTextEmail=findViewById(R.id.EmailEdit);
        editTextRole=findViewById(R.id.editRole);


        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = new User();
                String userName = editTextUsername.getText().toString().trim();
                String email = editTextEmail.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();
                String role = editTextRole.getText().toString().trim();

                if (password.equals(Constants.KEY_PASSWORD)) {
                    user.setUserName(userName);
                    user.setEmail(email);
                    user.setRole(role);
                    user.setPassword(password);
                    long val = db.addUser(user);
                    if (val > 0) {
                        Toast.makeText(RegisterUserActivity.this, "You have registered", Toast.LENGTH_SHORT).show();
                        Intent moveToLogin = new Intent(RegisterUserActivity.this, MainActivity.class);


                        startActivity(moveToLogin);
                    } else
                        Toast.makeText(RegisterUserActivity.this, "Registeration Error", Toast.LENGTH_SHORT).show();

                    Log.d("Item Added ID : ", String.valueOf(db.getUsersCount()));
                } else {
                    Toast.makeText(RegisterUserActivity.this, "Password is not matching", Toast.LENGTH_SHORT).show();

                }
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
                activityIntentLogin = new Intent(this, LoginActivity.class);
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
            case R.id.menuitem_exit:
                Toast.makeText(this, "Exiting...", Toast.LENGTH_SHORT).show();
                this.finishAffinity();
                return true;

        }

        return super.onOptionsItemSelected(item);
    }
}