package com.example.initial_screen.Requests;

import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CreateAccountRequest extends JsonObjectRequest {
    private static final String CREATE_ACCOUNT_REQUEST_URL = "http://cs309-sd-4.misc.iastate.edu:8080/user/new"; //NEED OUR URL HERE
    private Map<String, String> params;

    public CreateAccountRequest(JSONObject userInfo, Response.Listener<JSONObject> listener, final ConstraintLayout cLayout) {
        super(Method.POST, CREATE_ACCOUNT_REQUEST_URL, userInfo, listener, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Snackbar.make(cLayout, "Error Loading", Snackbar.LENGTH_LONG).show();
            }
        });
        params = new HashMap<>();
    }

    @Override
    public Map<String, String> getParams(){
            return params;
        }
}
