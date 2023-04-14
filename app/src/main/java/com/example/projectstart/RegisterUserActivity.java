package com.example.projectstart;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import Data.DatabaseHelper;
import Model.User;

public class RegisterUserActivity extends AppCompatActivity {
    DatabaseHelper db;
    EditText editTextUsername, editTextEmail, editTextPassword, editTextCnfPassword;
    Button buttonRegister;

    TextView textViewLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        db = new DatabaseHelper(this);
        editTextUsername = findViewById(R.id.editTextTextPersonName);
        editTextEmail = findViewById(R.id.editTextTextEmailAddress);
        editTextPassword = findViewById(R.id.editTextTextPassword);
        editTextCnfPassword = findViewById(R.id.editTextPhone);
        buttonRegister = findViewById(R.id.button);



        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = new User();
                String userName = editTextUsername.getText().toString().trim();
                String email = editTextEmail.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();
                String passwordConf = editTextCnfPassword.getText().toString().trim();

                if (password.equals(passwordConf)) {
                    user.setUserName(userName);
                    user.setEmail(email);

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

}