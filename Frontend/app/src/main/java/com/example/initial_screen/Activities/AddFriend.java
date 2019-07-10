package com.example.initial_screen.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.initial_screen.App.AppController;
import com.example.initial_screen.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class AddFriend extends AppCompatActivity {

    private Button backbtn;
    private Button addFriendBtn;
    private static final String ADD_FRIEND_URL = "http://cs309-sd-4.misc.iastate.edu:8080/user/addFriend";
    private String username;
    private TextView friendName;
    private TextView friendRank;
    private TextView friendScore;

    /**
     * This creats the activity to add a user.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);

        backbtn = findViewById(R.id.AFbackBtn);
        addFriendBtn = findViewById(R.id.AFaddfriendBtn);
        friendName = findViewById(R.id.AFuser);
        friendRank = findViewById(R.id.AFrank);
        friendScore = findViewById(R.id.AFscore);

        getIncomingIntent();

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToFindFriend = new Intent(AddFriend.this, FindFriend.class);
                goToFindFriend.putExtra("username", username);
                startActivity(goToFindFriend);
            }
        });

        addFriendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFriend();
            }
        });
    }

    /**
     * this method gets the incoming intent and sets the variables to send to displayFriend
     */
    private void getIncomingIntent(){
        if(getIntent().hasExtra("username") &&
                getIntent().hasExtra("rank") &&
                getIntent().hasExtra("hscore") &&
                getIntent().hasExtra("friendname")) {
            String friend = getIntent().getStringExtra("friendname");
            String rank = getIntent().getStringExtra("rank");
            String score = getIntent().getStringExtra("hscore");
            username = getIntent().getStringExtra("username");

            displayFriend(friend, rank,score);
        }
        else{
            Toast.makeText(getApplicationContext(),
                    "Couldn't Load Details", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * The views are for friend are set in this method
     * @param user
     * @param rank
     * @param score
     */
    private void displayFriend(String user, String rank, String score){
        friendName.setText(user);
        friendRank.setText(rank);
        friendScore.setText(score);
    }

    /**
     * This method is called to add friend. Sends a post request to backend when added
     */
    private void addFriend(){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        final String friendname = friendName.getText().toString();
        JSONObject friend = new JSONObject();
        JSONObject curUser = new JSONObject();
        JSONArray friendsArr;
        friendsArr = new JSONArray();
        try {
            friend.put("user_name", friendname);
            curUser.put("user_name", username);
            friendsArr.put(0, friend);
            friendsArr.put(1, curUser);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG ).show();
        }
        JsonArrayRequest afRequest = new JsonArrayRequest(Request.Method.POST,
                ADD_FRIEND_URL,
                friendsArr,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        progressDialog.dismiss();
                                Intent goToFindFriend = new Intent(AddFriend.this, FindFriend.class);
                                goToFindFriend.putExtra("username", username);
                                startActivity(goToFindFriend);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Error: " + error.getMessage(), Toast.LENGTH_LONG ).show();
                    }
                });
        AppController.getInstance().addToRequestQueue(afRequest);
    }

}

