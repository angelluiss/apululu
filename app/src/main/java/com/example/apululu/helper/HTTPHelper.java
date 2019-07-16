package com.example.apululu.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class GetProfilesToMatch<preferences> {
    private Context context;
    private SharedPreferences preferences;
    public GetProfilesToMatch(Context context){
        this.context = context;

    }


    public void getData(String url, final String token, String petition, JSONObject dataJSON, Response.Listener responseListener, Response.ErrorListener errorListener){
        RequestQueue requstQueue = Volley.newRequestQueue(context);
        int method = -99;

        switch (petition){
            case "GET":
                method = Request.Method.GET;
                break;
            case "POST":
                method = Request.Method.POST;
                break;
            case "PATCH":
                method = Request.Method.PATCH;
                break;
        }
        JsonObjectRequest jsonobj = new JsonObjectRequest(method, url,dataJSON, responseListener,errorListener){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                //params.put("Content-Type", "application/json; charset=UTF-8");
                params.put("Authorization", "Bearer " + token);
                return params;
            }
        };
        jsonobj.setRetryPolicy(new DefaultRetryPolicy(4000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requstQueue.add(jsonobj);
    }

    public void getDataArray(String url, final String token, String petition, JSONArray dataJSON, Response.Listener responseListener, Response.ErrorListener errorListener){

        RequestQueue requstQueue = Volley.newRequestQueue(context);
        int method = -99;

        switch (petition){
            case "GET":
                method = Request.Method.GET;
                break;
            case "POST":
                method = Request.Method.POST;
                break;
            case "PATCH":
                method = Request.Method.PATCH;
                break;
        }
        JsonArrayRequest jsonobj = new JsonArrayRequest(method, url,dataJSON, responseListener,errorListener){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                //params.put("Content-Type", "application/json; charset=UTF-8");
                params.put("Authorization", "Bearer " + token);
                return params;
            }
        };
        jsonobj.setRetryPolicy(new DefaultRetryPolicy(4000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requstQueue.add(jsonobj);
    }
}

