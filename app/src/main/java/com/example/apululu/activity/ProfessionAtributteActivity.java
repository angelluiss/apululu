package com.example.apululu.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Toast;
import es.dmoral.toasty.Toasty;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.apululu.R;
import com.example.apululu.utils.SendRegister;

import org.json.JSONObject;


public class ProfessionAtributteActivity extends AppCompatActivity {

    String[] registro;
    MultiAutoCompleteTextView profession;
    SendRegister postdata;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profession_atributte);

        postdata = new SendRegister(this);

        final LinearLayout buttonNext = (LinearLayout)findViewById(R.id.llBtnProfessionNext);
        profession = (MultiAutoCompleteTextView) findViewById(R.id.professionArea);


        Bundle parametros = this.getIntent().getExtras();
        assert parametros != null;
        registro = getIntent().getExtras().getStringArray("registro");

        String[] professionParameter = new String[0];
        if (parametros != null) {
            professionParameter = parametros.getStringArray("Selected");
        }

        final String[] finalProfessionParameter = professionParameter;



        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                assert finalProfessionParameter != null;
                registro[8] = profession.getText().toString();


                for (String s : finalProfessionParameter) {
                    if (s.equals("student")) {
                        buttonNext.setBackgroundResource(R.drawable.rounded_button_gradient_solid);
                        Intent intent = new Intent(ProfessionAtributteActivity.this,StudentAtributteActivity.class);
                        intent.putExtra("registro",registro);
                        Toast.makeText(ProfessionAtributteActivity.this, "student", Toast.LENGTH_LONG).show();
                        startActivity(intent);
                        break;
                    } else if (s.equals("other")) {
                        buttonNext.setBackgroundResource(R.drawable.rounded_button_gradient_solid);
                        Intent intent1 = new Intent(ProfessionAtributteActivity.this, OtherAtributteActivity.class);
                        intent1.putExtra("registro",registro);
                        Toast.makeText(ProfessionAtributteActivity.this, "other", Toast.LENGTH_LONG).show();
                        startActivity(intent1);
                    }else {


                        postdata.postData("http://192.168.2.117:3000/api/auth/register", postdata.buildJSONobject(registro), new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                String respuesta = response.toString();
                                Log.d("registerResponse",respuesta);
                                Toasty.success(ProfessionAtributteActivity.this, R.string.register_success, Toasty.LENGTH_SHORT, true).show();
                                Intent intent = new Intent(ProfessionAtributteActivity.this, HomeActivity.class);
                                startActivity(intent);
                            }
                        }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Toasty.error(ProfessionAtributteActivity.this, "register error", Toasty.LENGTH_SHORT, true).show();
                                        Log.d("registerResponse","Error de registro");
                                    }
                                });
                    }
                }
            }
        });



    }
}
