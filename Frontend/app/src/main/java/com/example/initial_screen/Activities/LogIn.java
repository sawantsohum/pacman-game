package com.example.initial_screen.Activities;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Response;
import com.example.initial_screen.App.AppController;
import com.example.initial_screen.R;
import com.example.initial_screen.Requests.LogInRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LogIn extends AppCompatActivity {

    /**
     *This activity handles the user log in. It sends the  json request to LogInRequest class
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        final EditText etUsername = (EditText) findViewById(R.id.etUsername);

        Intent i = getIntent();
        if(!i.getExtras().get("username").toString().isEmpty()) {
            etUsername.setText(i.getExtras().get("username").toString());
        }

        final EditText etPassword = (EditText) findViewById(R.id.etPassword);
        final Button btnLogIn = (Button) findViewById(R.id.btnLogIn);
        final Map<String, String> params = new HashMap<>();
        final ConstraintLayout CLayout = (ConstraintLayout) findViewById(R.id.loginCLayout);

        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = etUsername.getText().toString();
                final String password = etPassword.getText().toString();
                if(username.equals(null)){
                    AlertDialog.Builder builder = new AlertDialog.Builder(LogIn.this); //CHANGE LATER TO MATCH
                    builder.setMessage("No Username Entered")
                            .setNegativeButton("Retry", null)
                            .create()
                            .show();
                }
                else if(password.equals(null)){
                    AlertDialog.Builder builder = new AlertDialog.Builder(LogIn.this); //CHANGE LATER TO MATCH
                    builder.setMessage("No Password Entered")
                            .setNegativeButton("Retry", null)
                            .create()
                            .show();
                }
                else{
                    params.put("user_name", username);
                    params.put("password", password);
                    JSONObject userInfo = new JSONObject(params);
                    Response.Listener<JSONObject> responseListener = new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                boolean success = response.getBoolean("success"); //checking if string is true or false
                                if (success) {
                                    Intent i = new Intent(LogIn.this, UserMainActivity.class);

                                    i.putExtra("username", username);

                                    startActivity(i);
                                } else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(LogIn.this); //CHANGE LATER TO MATCH
                                    builder.setMessage("Log In Failed")
                                            .setNegativeButton("Retry", null)
                                            .create()
                                            .show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG ).show();
                            }
                        }
                    };
                    LogInRequest logInRequest = new LogInRequest( userInfo, responseListener, CLayout);
                    AppController.getInstance().addToRequestQueue(logInRequest);
                }

            }
        });
    }
}
