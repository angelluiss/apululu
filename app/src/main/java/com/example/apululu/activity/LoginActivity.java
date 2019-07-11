package com.example.apululu.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
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
import com.example.apululu.utils.Util;

import org.json.JSONException;
import org.json.JSONObject;

import es.dmoral.toasty.Toasty;

public class LoginActivity extends AppCompatActivity {

    private SharedPreferences preferences;
    private String tokenLogin;
    TextInputEditText userEmail;
    TextInputEditText userPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        LinearLayout buttonNext = (LinearLayout) findViewById(R.id.buttonNextLogin);
        userEmail = (TextInputEditText) findViewById(R.id.tiUser);
        userPassword = (TextInputEditText) findViewById(R.id.tiPassword);

        preferences = getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        setCredentialsIfExist();

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(login(userEmail.getText().toString(),userPassword.getText().toString())){
                    JSONObject dataJSON1;
                    dataJSON1 = createJSONObject(userEmail, userPassword);
                    postData(url, dataJSON1);


                }
            }
        });
    }

    private void setCredentialsIfExist() {
        String email = Util.getUserMailPrefs(preferences);
        String password = Util.getUserPasswordPrefs(preferences);
        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)){
            userEmail.setText(email);
            userEmail.setText(password);
        }
    }

    private boolean isValidEmail(String email){
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean login(String email, String password){
        if(!isValidEmail(email)){
            Toasty.error(this, "Email is not valid, please try again", Toasty.LENGTH_SHORT).show();
            return false;
        }else if(!isValidPassword(password)){
            Toasty.error(this, "Password is not valid, please try again", Toasty.LENGTH_SHORT).show();
            return false;
        }else{
            return true;
        }
    }

    private boolean isValidPassword(String password){
        return password.length() >= 8;
    }

    private void saveOnPreferences(String email, String password,String token){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("email", email);
        editor.putString("pass",password);
        editor.putString("token",token);
        editor.apply();
    }

    public JSONObject createJSONObject (TextInputEditText userMAIL, TextInputEditText userPASWORD ){
        final JSONObject dataJSON = new JSONObject();
        try {
            dataJSON.put("username", userMAIL.getText().toString());
            dataJSON.put("password", userPASWORD.getText().toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return dataJSON;
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
                            tokenLogin = response.getString("token");
                            saveOnPreferences(userEmail.getText().toString(),userPassword.getText().toString(),tokenLogin);
                            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toasty.error(LoginActivity.this,"Error with server",Toasty.LENGTH_LONG).show();
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
