package com.example.initial_screen.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.initial_screen.R;

public class Profile extends AppCompatActivity {
    private String username;

    /**
     * this activity loads the user profile
     * it has username, rank, score
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Intent i = getIntent();
        if(!i.getExtras().get("username").toString().isEmpty()) {
            username = i.getExtras().get("username").toString();
        }

        EditText etUsername = (EditText) findViewById(R.id.usernameET);
        EditText etPassword = (EditText) findViewById(R.id.passwordET);

        Button saveUsername = (Button) findViewById(R.id.btnSaveUsername);
        Button savePassword = (Button) findViewById(R.id.btnSavePassword);
        Button back = (Button) findViewById(R.id.btnProfileBack);

        TextView highscore = (TextView) findViewById(R.id.highscoreTV);
        TextView rank = (TextView) findViewById(R.id.rankTV);

    }
}
