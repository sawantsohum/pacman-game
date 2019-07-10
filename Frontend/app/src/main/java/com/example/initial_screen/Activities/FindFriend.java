package com.example.initial_screen.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.initial_screen.App.AppController;
import com.example.initial_screen.App.FriendSearchAdapter;
import com.example.initial_screen.App.FriendSearchController;
import com.example.initial_screen.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FindFriend extends AppCompatActivity {


    private Button fsBackBtn;
    private EditText fSearch;
    private ImageButton ibFriendSearch;
    private LinearLayout fsLLayout;
    private RecyclerView rView;
    private RecyclerView.Adapter adapter;
    private static final String FRIEND_SEARCH_URL = "http://cs309-sd-4.misc.iastate.edu:8080/user/friend";
    private String username;

    private List<FriendSearchController> fsList;

    /**
     * This activity is to search for a friend to add
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_friend);

        Intent intent = getIntent();
        if(intent.hasExtra("username")) {
            username = intent.getExtras().get("username").toString();
        }

        fsBackBtn = (Button) findViewById(R.id.fsBackBtn);
        fSearch = (EditText) findViewById(R.id.fsET);
        ibFriendSearch = (ImageButton) findViewById(R.id.fsIB);
        fsLLayout = (LinearLayout) findViewById(R.id.lLayoutFS);
        rView = (RecyclerView) findViewById(R.id.rvFriendSearch);
        rView.setHasFixedSize(true);
        rView.setLayoutManager(new LinearLayoutManager(this));
        fsList = new ArrayList<>();

        fsBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToFriends = new Intent(FindFriend.this, Friends.class );
                goToFriends.putExtra("username", username);
                startActivity(goToFriends);
            }
        });

        ibFriendSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String friend = fSearch.getText().toString();
                if(friend.isEmpty()){
                    Snackbar.make(fsLLayout, "No Username Entered", Snackbar.LENGTH_LONG).show();
                }
                else{
                    JSONObject fObj = new JSONObject();
                    try {
                        fObj.put("user_name", friend);
                        loadFriendData(fObj);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(),
                                "Error: " + e.getMessage(),
                                Toast.LENGTH_LONG ).show();
                    }
                }
            }
        });
    }

    /**
     * this method takes in the friend you want to search for and does a GET request to
     * receive their information
     * @param friend
     * @throws JSONException
     */
    private void loadFriendData(JSONObject friend) throws JSONException {
        final JSONArray fArray = new JSONArray();
        fArray.put(0, friend);
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading Data.");
        progressDialog.show();

        JsonArrayRequest fsRequest = new JsonArrayRequest(Request.Method.GET,
                FRIEND_SEARCH_URL, fArray,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        progressDialog.dismiss();
                        if(response.length() != 0) {
                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    JSONObject f = response.getJSONObject(i);
                                    FriendSearchController fData = new FriendSearchController(
                                            f.getInt("myrank"),
                                            f.getInt("score"),
                                            f.getString("user_name")
                                    );

                                    fsList.add(fData);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Toast.makeText(getApplicationContext(),
                                            "Error: " + e.getMessage(),
                                            Toast.LENGTH_LONG ).show();
                                }
                            }
                            adapter = new FriendSearchAdapter(fsList, getApplicationContext(), username);
                            rView.setAdapter(adapter);
                        }
                        else{
                            Toast.makeText(getApplicationContext(),
                                    "User Not Found",
                                    Toast.LENGTH_LONG ).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Snackbar.make(fsLLayout, "Error Loading", Snackbar.LENGTH_LONG).show();
            }
        });
        AppController.getInstance().addToRequestQueue(fsRequest);
    }
}
