package com.example.projectstart.MainActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projectstart.LoginActivity.LoginActivity;
import com.example.projectstart.R;


public class MainActivity extends AppCompatActivity {
    Intent activityIntentLogin;

    TextView playMessageTextView;

    Button startButton,stopButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        playMessageTextView=findViewById(R.id.message_music);
        startButton=findViewById(R.id.button_play);
        stopButton=findViewById(R.id.button_stop);
    }

    public void onStart(View view) {
        Intent intent=new Intent(getApplicationContext(), MusicService.class);
        startService(intent);
    }

    public void onStop(View view) {
        Intent intent=new Intent(getApplicationContext(),MusicService.class);
        stopService(intent);
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

            case R.id.menuitem_exit:
                Toast.makeText(this, "Exiting...", Toast.LENGTH_SHORT).show();
                this.finishAffinity();
                return true;

        }

        return super.onOptionsItemSelected(item);
    }
}