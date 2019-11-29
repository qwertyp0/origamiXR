package com.example.origamixr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class StartScreen extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_screen);
    }

    public void sendToLogin(View v)
    {
        //Toast.makeText(getApplicationContext(), "Should go to start", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getApplicationContext(), LoginScreen.class));

        //overridePendingTransition();
    }
}
