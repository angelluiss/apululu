package com.example.apululu.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.apululu.R;
import com.example.apululu.utils.InternetStateService;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;


public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

            }
        }, 10000);

        Context thisContex =MainActivity.this;
        startService(new Intent(thisContex,InternetStateService.class));

      //  RequestQueue queue = Volley.newRequestQueue(this);


        // Add the request to the RequestQueue.
       // queue.add(stringRequest);
        Intent intent = new Intent(MainActivity.this,LoginOrSingUPActivity.class);
        startActivity(intent);
        //this.postData(this.url, this.data);
    }
    // Here ends onCreate
    //** GET
   // String url ="http://192.168.2.115:3000/api/hello-world";
    // Request a string response from the provided URL.
    /*StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    // Display the first 500 characters of the response string.
                    //textView.setText("Response is: "+ response);
                }
            }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            //textView.setText("That didn't work!");
        }
    });

    // *** POST ***
    public void postData(String url,JSONObject data){
        RequestQueue requstQueue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonobj = new JsonObjectRequest(Request.Method.POST, url,data,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(MainActivity.this, response.toString(),Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, error.toString(),Toast.LENGTH_LONG).show();
                    }
                }
        ){
            //here I want to post data to sever
        };
        requstQueue.add(jsonobj);

    } */
}
