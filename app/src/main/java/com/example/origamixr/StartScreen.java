package com.example.origamixr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class StartScreen extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPreferences = getSharedPreferences("Login", MODE_PRIVATE);
        if (sharedPreferences.getBoolean("logged_in", false)) {
            startActivity(new Intent(getApplicationContext(), MainHomeActivity.class));
            this.finish();
        }

        setContentView(R.layout.start_screen);
    }

    public void sendToLogin(View v)
    {
        //Toast.makeText(getApplicationContext(), "Should go to start", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getApplicationContext(), LoginScreen.class));
        this.finish();
        //overridePendingTransition();
    }
}
