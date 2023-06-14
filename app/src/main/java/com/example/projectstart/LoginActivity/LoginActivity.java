package com.example.projectstart.LoginActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.example.projectstart.MainActivity.MainActivity;
import com.example.projectstart.R;
import com.example.projectstart.RegisterUserActivity.RegisterUserActivity;
import com.example.projectstart.TakeAPictureAndShowOnList.TakePictureActivity;

import Data.DatabaseHelper;
import Model.User;

public class LoginActivity extends AppCompatActivity {
    EditText editTextUserName, editTextPassword;
    Button buttonLogin;
    DatabaseHelper db;
    Intent activityIntentLogin, activityIntentRegisterUser,activityIntentMain;
    private static String currentLoggedInUserName;
    private static String currentLoggedInEmail;

    private static Boolean res=false;
    private static boolean is_master_user_password=false;

    public static String getCurrentLoggedInUserName(){
        return currentLoggedInUserName;
    }

    public static boolean getCurrentLoggedInMaster(){
        return is_master_user_password;
    }

    public static boolean getCurrentUserIsLogged(){
        if(is_master_user_password || res)
            return true;
        else
            return false;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        db = new DatabaseHelper(LoginActivity.this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        editTextUserName = findViewById(R.id.editTextUserName);
        editTextPassword = findViewById(R.id.password);
        buttonLogin=findViewById(R.id.login_button);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                User user = new User();
                String email = editTextUserName.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();




                if((email.equals("Password")) && (password.equals("Password")))
                    is_master_user_password=true;
                else
                    is_master_user_password=false;

                Log.d("IDdetails", String.valueOf(user.getId()));
                if(!is_master_user_password)
                    res = db.checkUser(email, password);
                if (res == true || is_master_user_password) {
                        Intent AdminHomePage = new Intent(LoginActivity.this, TakePictureActivity.class);
                        Bundle b = new Bundle();
                        b.putString("textViewEmail", editTextUserName.getText().toString());
                        b.putString("textViewPassword", editTextPassword.getText().toString());

                        String userName;
                        int userId;
                        if(is_master_user_password==false) {
                            userName = db.selectOneUserSendUserName(email, password);
                            currentLoggedInUserName = userName;
                            currentLoggedInEmail = email;
                            userId = db.selectOneUserSendId(email, password);
                        }else {
                            userName = "Admin";
                            currentLoggedInUserName = userName;
                            userId=10000;
                        }
                        Log.d("TAG", "ID =  " + userId);

                        b.putString("textViewUsername", userName);
                        b.putString("textViewId", String.valueOf(userId));

                        AdminHomePage.putExtras(b);
                        startActivity(AdminHomePage);
                    } else {
                        Toast.makeText(LoginActivity.this, "Login Error", Toast.LENGTH_LONG).show();
                    }
                }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        if(is_master_user_password)
            getMenuInflater().inflate(R.menu.menu_login,menu);
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
                if(is_master_user_password) {
                    Toast.makeText(this, "Register user...", Toast.LENGTH_SHORT).show();
                    activityIntentRegisterUser = new Intent(this, RegisterUserActivity.class);
                    startActivity(activityIntentRegisterUser);
                    return true;
                }
            case R.id.menuitem_exit:
                Toast.makeText(this, "Exiting...", Toast.LENGTH_SHORT).show();
                this.finishAffinity();
                return true;

        }

        return super.onOptionsItemSelected(item);
    }

}
