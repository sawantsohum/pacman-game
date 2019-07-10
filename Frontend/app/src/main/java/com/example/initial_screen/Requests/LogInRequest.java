package com.example.initial_screen.Requests;


import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;


public class LogInRequest extends JsonObjectRequest {
    private static final String LOGIN_REQUEST_URL = "http://cs309-sd-4.misc.iastate.edu:8080/user"; //NEED OUR URL HERE

    public LogInRequest(JSONObject userInfo, Response.Listener<JSONObject> listener, final ConstraintLayout cLayout) {
        super(Method.GET, LOGIN_REQUEST_URL, userInfo, listener, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Snackbar.make(cLayout, "Error Loading", Snackbar.LENGTH_LONG).show();
            }
        });

    }

}
