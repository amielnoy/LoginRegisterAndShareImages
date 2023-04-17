package com.example.projectstart;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import Data.DataBaseInstance;
import Data.DatabaseHelper;
import Model.User;

public class LoginActivity extends AppCompatActivity {
    EditText editTextUserName, editTextPassword;
    Button buttonLogin;
    TextView textViewRegister;
    DatabaseHelper db;
    Intent activityIntentLogin, activityIntentRegisterUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        editTextUserName = findViewById(R.id.editTextUserName);
        editTextPassword = findViewById(R.id.password);
        //editTextCnfPassword = findViewById(R.id.editTextPhone);
        buttonLogin=findViewById(R.id.login_button);
        db=DataBaseInstance.getDatabaseInstance();

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextUserName.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();
                //String role=editText

                Boolean res = db.checkUser(email, password,"");
                User user = new User();
                Log.d("IDdetails", String.valueOf(user.getId()));


                if (res == true) {
                    Intent HomePage = new Intent(LoginActivity.this, MainActivity.class);
                    Bundle b = new Bundle();
                    b.putString("textViewEmail", editTextUserName.getText().toString());
                    b.putString("textViewPassword", editTextPassword.getText().toString());

                    String y = db.selectOneUserSendUserName(email, password);
                    int x = db.selectOneUserSendId(email,password);
                    Log.d("TAG" , "ID =  " + x);

                    b.putString("textViewUsername",y);
                    b.putString("textViewId", String.valueOf(x));

                    HomePage.putExtras(b);
                    startActivity(HomePage);
                } else {
                    Toast.makeText(LoginActivity.this, "Login Error", Toast.LENGTH_LONG).show();
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
