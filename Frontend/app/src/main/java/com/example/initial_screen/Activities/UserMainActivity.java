package com.example.initial_screen.Activities;

import android.content.Intent;
import android.media.Image;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.initial_screen.R;

public class UserMainActivity extends AppCompatActivity {
    private String username;

    /**
     * This is the main user activity that is opened when the user has signed up or
     * logged in. here the user can either go to friends, leaderboard, profile, or play
     * the game. 
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main);

        Intent i = getIntent();
        if(!i.getExtras().get("username").toString().isEmpty()) {
            username = i.getExtras().get("username").toString();
        }


        Button playPacmanbtn = (Button) findViewById(R.id.playPacmanBtn);
        ImageButton playPacmanAlsobtn = (ImageButton) findViewById(R.id.playPacmanImgBtn);


        Button storebtn = (Button) findViewById(R.id.storeBtn);
        storebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoStore = new Intent(UserMainActivity.this, Store.class);
                gotoStore.putExtra("username", username);
                startActivity(gotoStore);
            }
        });

        Button profilebtn = (Button) findViewById(R.id.profileBtn);
        profilebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoProfile = new Intent(UserMainActivity.this, Profile.class);
                gotoProfile.putExtra("username", username);
                startActivity(gotoProfile);
            }
        });

        Button friendsbtn = (Button) findViewById(R.id.friendsBtn);
        friendsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoFriends = new Intent(UserMainActivity.this, Friends.class);
                gotoFriends.putExtra("username", username);
                startActivity(gotoFriends);
            }
        });

        Button leaderboardbtn = (Button) findViewById(R.id.leaderboardBtn);
        leaderboardbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoLeaderboards = new Intent(UserMainActivity.this, Leader_Board.class);
                gotoLeaderboards.putExtra("username", username);
                startActivity(gotoLeaderboards);
            }
        });
    }
}
