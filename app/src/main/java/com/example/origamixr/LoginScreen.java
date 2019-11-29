package com.example.origamixr;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginScreen extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);
    }

    public void signIntoMainActivity(View v)
    {
        TextView username = findViewById(R.id.username_field);
        TextView email = findViewById(R.id.email_field);
        TextView password = findViewById(R.id.password_field);

        if(username.getText().toString().equals(""))
            Toast.makeText(getApplicationContext(), "Please provide a valid username", Toast.LENGTH_SHORT).show();
        else if(email.getText().toString().equals(""))
            Toast.makeText(getApplicationContext(), "Please provide a valid email", Toast.LENGTH_SHORT).show();
        else if(password.getText().toString().equals(""))
            Toast.makeText(getApplicationContext(), "Please provide a valid password", Toast.LENGTH_SHORT).show();
        else
            startActivity(new Intent(getApplicationContext(), MainHomeActivity.class));
    }
}