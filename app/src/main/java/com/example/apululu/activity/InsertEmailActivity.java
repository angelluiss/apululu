package com.example.apululu.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

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

public class InsertEmailActivity extends AppCompatActivity {
    String emailVerification = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_email);

        // Instancias de los Widgets
        final LinearLayout buttonNext = (LinearLayout) findViewById(R.id.llInsertEmailBtn);
        final EditText email = (EditText) findViewById(R.id.etEmail);
        final CheckBox termAndagreedments = (CheckBox) findViewById(R.id.cbPrivacy);


        // Evento click de boton Next
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // POST del Email
                final JSONObject dataJSON = new JSONObject();
                emailVerification = email.getText().toString();

                try {
                    dataJSON.put("email", emailVerification);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                // Verificación de email valido
                final String emailText = email.getText().toString().trim();
                final String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                boolean termsChecked = termAndagreedments.isChecked();
                if (emailText.matches(emailPattern) && termsChecked)
                {
                    InsertEmailActivity.this.postData(InsertEmailActivity.this.url, dataJSON);   // Envía el post del Email
                    buttonNext.setBackgroundResource(R.drawable.rounded_button_gradient_solid);
                }
                else if (!emailText.matches(emailPattern)){
                    Toast.makeText(getApplicationContext(),"Invalid email address",Toast.LENGTH_SHORT).show();
                }else if(!termsChecked){
                    Toast.makeText(getApplicationContext(),"You must accept Terms and Conditions",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    String url ="http://192.168.2.117:3000/api/auth/verify-email";
    // *** POST ***
    public void postData(String url,JSONObject dataJSON){
        RequestQueue requstQueue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonobj = new JsonObjectRequest(Request.Method.POST, url,dataJSON,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Intent intent2 = new Intent(InsertEmailActivity.this,VerificationEmailActivity.class);
                        intent2.putExtra("email",emailVerification);
                        startActivity(intent2);
                        Toasty.success(InsertEmailActivity.this,"Check your mail to find the Verification code",Toasty.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toasty.error(InsertEmailActivity.this,"Email was not send",Toasty.LENGTH_LONG).show();
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
