package com.example.apululu.utils;

import android.content.Context;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;

import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;


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
            profile.put("city", city);
            profile.put("sex", sex);
            profile.put("profession",profession);
            profile.put("study", study);
            profile.put("other", other);
            profile.put("phoneNumber",phoneNumber);
            profile.put("description",aboutYou);
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

    public void postData(String url, JSONObject dataJSON, Response.Listener responseListener, Response.ErrorListener errorListener){

        RequestQueue requstQueue = Volley.newRequestQueue(context);

        JsonObjectRequest jsonobj = new JsonObjectRequest(Request.Method.POST, url,dataJSON,
                responseListener,errorListener
        ){
            //here I want to post data to sever
        };
        jsonobj.setRetryPolicy(new DefaultRetryPolicy(4000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requstQueue.add(jsonobj);
    }

}
