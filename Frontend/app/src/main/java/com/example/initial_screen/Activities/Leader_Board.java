package com.example.initial_screen.Activities;

import android.app.LauncherActivity;
import android.app.ProgressDialog;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.initial_screen.App.AppController;
import com.example.initial_screen.App.LeaderboardAdapter;
import com.example.initial_screen.App.LeaderboardController;
import com.example.initial_screen.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Leader_Board extends AppCompatActivity {

    private ConstraintLayout lbCLayout;
    private RecyclerView rView;
    private RecyclerView.Adapter adapter;
    private static final String LEADERBOARD_REQUEST_URL = "http://cs309-sd-4.misc.iastate.edu:8080/user/leaderboard"; //change to correct one

    private List<LeaderboardController> lbList;

    /**
     * this creates the activity for  leaderboard
     * Shows the rank and high score of all the users who have played
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leader__board);

        lbCLayout = (ConstraintLayout) findViewById(R.id.cLayoutLB);
        rView = (RecyclerView) findViewById(R.id.rvLBoard);
        rView.setHasFixedSize(true);
        rView.setLayoutManager(new LinearLayoutManager(this));

        lbList = new ArrayList<>();

        loadRecyclerViewData();
    }

    /**
     * loads the recycler view with the correct data to be displayed
     */
    private void loadRecyclerViewData(){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading Data.");
        progressDialog.show();

        JsonArrayRequest lbRequest = new JsonArrayRequest(Request.Method.GET,
                LEADERBOARD_REQUEST_URL, null,
                new Response.Listener<JSONArray>(){
                    @Override
                    public void onResponse(JSONArray response) {
                        progressDialog.dismiss();
                        try {
                            for(int i = 0; i < response.length(); i++){
                                JSONObject user = response.getJSONObject(i);
                                LeaderboardController lbData = new LeaderboardController(user.getInt("myrank"),
                                        user.getString("user_name"),
                                        user.getInt("score"));

                                lbList.add(lbData);
                            }
                            adapter = new LeaderboardAdapter(lbList, getApplicationContext());
                            rView.setAdapter(adapter);
                        } catch (JSONException e) {
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
                Snackbar.make(lbCLayout, "Error Loading", Snackbar.LENGTH_LONG).show();
            }
        });
        AppController.getInstance().addToRequestQueue(lbRequest);
    }
}
