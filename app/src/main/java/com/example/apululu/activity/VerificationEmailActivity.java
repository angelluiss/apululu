package com.example.apululu.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

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

public class VerificationEmailActivity extends AppCompatActivity {
    private LinearLayout buttonNext = null;
    private String[] registro;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification_email);


        Bundle parametros = this.getIntent().getExtras();
        assert parametros != null;
        registro = getIntent().getExtras().getStringArray("registro");


        this.buttonNext = (LinearLayout) findViewById(R.id.llVerificationBtn);
        final EditText inputText = (EditText) findViewById(R.id.etVerification);

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tokenCode = inputText.getText().toString().trim();
                registro[2] = tokenCode;
                if (!tokenCode.equals(""))
                {
                    // Declaracion del JSON OBJECT
                    final JSONObject dataJSON = new JSONObject();
                    Bundle email = getIntent().getExtras();
                    assert email != null;
                    String emailInputed = email.getString("email");

                    try {
                        dataJSON.put("email", emailInputed);
                        dataJSON.put("token",tokenCode);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    VerificationEmailActivity.this.postData(VerificationEmailActivity.this.url, dataJSON); // Envio de POST
                }else{
                    Toasty.error(VerificationEmailActivity.this, "Insert the Verification code", Toasty.LENGTH_LONG).show();
                }


            }
        });
    }

    String url ="http://192.168.2.117:3000/api/auth/verify-email/check-token";
    // *** POST ***
    public void postData(String url, JSONObject dataJSON){
        RequestQueue requstQueue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonobj = new JsonObjectRequest(Request.Method.POST, url,dataJSON,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.getBoolean("valid")) {
                                Toasty.success(VerificationEmailActivity.this,"Verification success",Toasty.LENGTH_SHORT).show();
                                buttonNext.setBackgroundResource(R.drawable.rounded_button_gradient_solid);
                                Intent intentEmail = new Intent(VerificationEmailActivity.this, InsertPasswordActivity.class);
                                intentEmail.putExtra("registro",registro);
                                startActivity(intentEmail);

                            }else{
                                Toasty.error(VerificationEmailActivity.this,"Invalid Code",Toasty.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toasty.error(VerificationEmailActivity.this,"Code Verification error",Toasty.LENGTH_LONG).show();
                    }
                }
        ){
            //here I want to post data to sever
        };
        requstQueue.add(jsonobj);
    }
}
