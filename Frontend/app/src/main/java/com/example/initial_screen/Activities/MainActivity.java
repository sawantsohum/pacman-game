package com.example.initial_screen.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.initial_screen.R;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Button btnLogIn;
    Button btnSignUp;
    ImageButton btnPlay;
    EditText username;

    /**
     * This loads the initial screen view. The user can either play as a guest, log in
     * or sign up
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnLogIn = (Button) findViewById(R.id.loginBtn);
        btnSignUp = (Button) findViewById(R.id.signupBtn);
        btnPlay = (ImageButton) findViewById(R.id.playAsGuestImgBtn);
        username = (EditText) findViewById(R.id.guestPlayerNameEditText);

        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, LogIn.class);

                String userName = username.getText().toString();
                if(!userName.isEmpty()) {
                    i.putExtra("username", userName);
                }

                startActivity(i);
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, SignUp.class);

                String userName = username.getText().toString();
                if(!userName.isEmpty()) {
                    i.putExtra("username", userName);
                }

                startActivity(i);
            }
        });

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, PacmanActivity.class);

                String userNme = username.getText().toString();
                if(userNme.equals("")) {
                    userNme = getSaltString();
                }
                i.putExtra("username", userNme);
                startActivity(i);
            }
        });
    }

    /**
     * this generates a random username for the user if they are playing as a guest.
     * @return saltStr
     */
    protected String getSaltString() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
        StringBuilder salt = new StringBuilder();
        Random rand = new Random();
        while(salt.length() < 14) {
            int index = (int) (rand.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;
    }
}
