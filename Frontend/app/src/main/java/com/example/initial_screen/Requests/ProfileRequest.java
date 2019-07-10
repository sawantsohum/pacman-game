package com.example.initial_screen.Requests;

import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

public class ProfileRequest extends JsonObjectRequest {
    private static final String PROFILE_REQUEST_URL = "http://cs309-sd-4.misc.iastate.edu:8080/user";

    /**
     * @param userInfo
     * @param listener
     * @param cLayout
     */
    public ProfileRequest(JSONObject userInfo, Response.Listener<JSONObject> listener, final ConstraintLayout cLayout) {
        super(Method.GET, PROFILE_REQUEST_URL, userInfo, listener, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Snackbar.make(cLayout, "Error Loading", Snackbar.LENGTH_LONG).show();
            }
        });
    }
}
