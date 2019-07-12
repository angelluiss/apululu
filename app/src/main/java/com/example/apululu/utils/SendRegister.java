package com.example.apululu.utils;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.apululu.activity.InsertEmailActivity;
import com.example.apululu.activity.VerificationEmailActivity;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import es.dmoral.toasty.Toasty;

public class SendRegister {
    private Context context;
    public SendRegister(Context context){
        this.context = context;
    }

    public static JSONObject buildJSONobject(String[] registroArray){

        JSONObject registerJSON = new JSONObject();
        JSONObject profile = new JSONObject();

        String email = registroArray[0];
        String password = registroArray[1];
        String tokenVerification = registroArray[2];
        String firtsName = registroArray[3];
        String lastName = registroArray[4];
        String birthay = registroArray[5];
        String sex = registroArray[6];
        String phoneNumber = registroArray[7];
        String profession = registroArray[8];
        String study = registroArray[9];
        String other = registroArray[10];
        String city = registroArray[11];
        String aboutYou = registroArray[12];

        try {
            profile.put("firstName", firtsName);
            profile.put("lastName", lastName);
            profile.put("birthdate",birthay);
            profile.put("city", firtsName);
            profile.put("sex", lastName);
            profile.put("profession",birthay);
            profile.put("study", firtsName);
            profile.put("other", lastName);
            profile.put("phoneNumber",birthay);
            profile.put("description",birthay);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            registerJSON.put("username", email);
            registerJSON.put("password", password);
            registerJSON.put("verificationCOde",tokenVerification);
            registerJSON.put("profile",profile);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String register = registerJSON.toString();
        Log.d("registro", register);

        return registerJSON;
    }

    public void postData(String url,JSONObject dataJSON){

        RequestQueue requstQueue = Volley.newRequestQueue(context);

        JsonObjectRequest jsonobj = new JsonObjectRequest(Request.Method.POST, url,dataJSON,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String respuesta = response.toString();
                        Log.d("registerResponse",respuesta);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("registerResponse","Error de registro");
                    }
                }
        ){
            //here I want to post data to sever
        };
        jsonobj.setRetryPolicy(new DefaultRetryPolicy(4000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                Log.d("prueba","Ejecucionsssss");
        requstQueue.add(jsonobj);
    }

}
