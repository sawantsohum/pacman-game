package com.example.initial_screen.Activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;

import com.example.initial_screen.Game.PacmanEngine;

import java.util.Random;

public class PacmanActivity extends Activity {
    PacmanEngine pacmanEngine;
    private String username;

    /**
     * this is the activity called when the user starts to play the pacman game
     * opens the pacman game engine
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent i = getIntent();
        if(!i.getExtras().get("username").toString().isEmpty()) {
            username = i.getExtras().get("username").toString();
        }

        Display display = getWindowManager().getDefaultDisplay();

        Point size = new Point();
        display.getSize(size);

        pacmanEngine = new PacmanEngine(this, size);
        pacmanEngine.username = username;

        //Make pacman engine the view of the activity
        setContentView(pacmanEngine);
    }

    /**
     * called to resume the game if it is paused
     */
    @Override
    protected void onResume() {
        super.onResume();
        pacmanEngine.resume();
    }


    /**
     * pauses the game if the user pauses it
     */
    @Override
    protected void onPause() {
        super.onPause();
        pacmanEngine.pause();
    }
}
