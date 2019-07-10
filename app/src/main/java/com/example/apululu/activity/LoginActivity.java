package com.example.apululu.activity;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.apululu.R;

import org.json.JSONException;
import org.json.JSONObject;

import es.dmoral.toasty.Toasty;

public class LoginActivity extends AppCompatActivity {

    TextInputEditText userEmail;
    TextInputEditText userPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        LinearLayout buttonNext = (LinearLayout) findViewById(R.id.buttonNextLogin);
        userEmail = (TextInputEditText) findViewById(R.id.tiUser);
        userPassword = (TextInputEditText) findViewById(R.id.tiPassword);

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final JSONObject dataJSON = new JSONObject();
                try {
                    dataJSON.put("username", userEmail.getText().toString());
                    dataJSON.put("password", userPassword.getText().toString());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                postData(url, dataJSON);
            }
        });


    }

    String url ="http://192.168.2.117:3000/api/auth/login";
    // *** POST ***
    public void postData(String url, JSONObject dataJSON){
        RequestQueue requstQueue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonobj = new JsonObjectRequest(Request.Method.POST, url,dataJSON,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Toasty.success(LoginActivity.this,response.getString("token"),Toasty.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toasty.error(LoginActivity.this,"token has no response",Toasty.LENGTH_LONG).show();
                        Log.d("Error de Email","ErrorAnge" + error.toString());
                    }
                }
        ){
            //here I want to post data to sever
        };
        jsonobj.setRetryPolicy(new DefaultRetryPolicy(4000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requstQueue.add(jsonobj);
    }

}
