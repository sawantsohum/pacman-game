package com.example.initial_screen.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.initial_screen.App.AppController;
import com.example.initial_screen.App.FriendAdapter;
import com.example.initial_screen.App.FriendController;
import com.example.initial_screen.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Friends extends AppCompatActivity {
    private String username;
    private RecyclerView rView;
    private RecyclerView.Adapter adapter;
    private static final String FRIEND_REQUEST_URL = "http://cs309-sd-4.misc.iastate.edu:8080/user/friends"; //change to correct one

    private List<FriendController> fList;
    private JSONArray fArray = new JSONArray();

    /**
     * this view displays all the friends that the user has
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);
        Intent intent = getIntent();
        if(intent.hasExtra("username")) {
            username = intent.getExtras().get("username").toString();
        }

        rView = findViewById(R.id.rvFriends);
        rView.setHasFixedSize(true);
        rView.setLayoutManager(new LinearLayoutManager(this));

        fList = new ArrayList<>();

        JSONObject curUser = new JSONObject();
        try {
            curUser.put("user_name", username);
            fArray.put(0, curUser);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),
                    "Error: " + e.getMessage(),
                    Toast.LENGTH_LONG ).show();
        }

        loadRecyclerViewData(fArray);

        Button addFriend = (Button) findViewById(R.id.friendsAddFbtn);
        addFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToFindFriend = new Intent(Friends.this, FindFriend.class);
                goToFindFriend.putExtra("username", username);
                startActivity(goToFindFriend);
            }
        });
    }

    /**
     * this takes in the json array of the user who's friends you want to load
     * GET request to get all the friends and load them into the recycler view
     * @param fArray
     */
    private void loadRecyclerViewData(JSONArray fArray){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading Data.");
        progressDialog.show();

        JsonArrayRequest fRequest = new JsonArrayRequest(Request.Method.GET,
                FRIEND_REQUEST_URL, fArray, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                progressDialog.dismiss();
                try {
                    for(int i = 0; i < response.length(); i++){
                        JSONObject friendObj = response.getJSONObject(i);
                        FriendController fData = new FriendController(
                                friendObj.getString("user_name"));
                        fList.add(fData);
                    }
                    adapter = new FriendAdapter(fList, getApplicationContext(), username);
                    rView.setAdapter(adapter);
                }catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),
                            "Error: " + e.getMessage(),
                            Toast.LENGTH_LONG ).show();
                    }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),
                        "Error: " + error.getMessage(),
                        Toast.LENGTH_LONG ).show();
            }
        });
        AppController.getInstance().addToRequestQueue(fRequest);
    }
}
