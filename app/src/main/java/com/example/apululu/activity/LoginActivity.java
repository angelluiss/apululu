package com.example.apululu.activity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;
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
import com.example.apululu.helper.HTTPHelper;
import com.example.apululu.helper.SQliteHelper;
import com.example.apululu.utils.URLS;
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
                            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
                         //   SharedPreferences.Editor editor = preferences.edit();
                            saveOnPreferences(userEmail.getText().toString(),userPassword.getText().toString(),tokenLogin);
                            Log.d("preferencias", preferences.toString());

                            /// ********************* BASE DE DATOS / DATA BASE ************************
                            SQliteHelper dbHelper = new SQliteHelper(LoginActivity.this);
                            final SQLiteDatabase db = dbHelper.getWritableDatabase();

                            ///// Query to db
                         //   Cursor c = db.rawQuery("SELECT _id, userId,firstName, lastName, age, city, sex, work, study, other, image, phoneNumber, description  FROM profiles", null);
                         //   Log.d("usuariooss",c.toString());

                                    //Asignamos el valor en nuestras variables para usarlos en lo que necesitemos

                                    HTTPHelper profilePetitions = new HTTPHelper(LoginActivity.this);
                                    profilePetitions.petitionData(URLS.GET_USER_DETAIL,tokenLogin, "GET", null,new Response.Listener<JSONObject>() {
                                                    @Override
                                                    public void onResponse(JSONObject response) {

                                                        try {
                                                            int userId = Integer.parseInt(response.getString("userId"));
                                                            String firstName = response.getString("firstName");
                                                            String lastName = response.getString("lastName");
                                                            String birthdate = response.getString("birthdate");
                                                            String city = response.getString("city");
                                                            String sex =  response.getString("sex");
                                                            String work =  response.getString("work");
                                                            String study =  response.getString("study");
                                                            String other =  response.getString("other");
                                                            String image = response.getString("image");
                                                            String phoneNumber = response.getString("phoneNumber");
                                                            String description = response.getString("description");


                                                            /// ***** UPDATE en la DB
                                                            ContentValues cv = new ContentValues();
                                                            cv.put("userId",userId);
                                                            cv.put("firstName",firstName);
                                                            cv.put("lastName",lastName);
                                                            cv.put("age",birthdate);
                                                            cv.put("city",city);
                                                            cv.put("sex",sex);
                                                            cv.put("work",work);
                                                            cv.put("study",study);
                                                            cv.put("other",other);
                                                            cv.put("image",image);
                                                            cv.put("phoneNumber",phoneNumber);
                                                            cv.put("description",description);

                                                            db.insert("profiles", null, cv);

                                                           /* String[] args = new String []{ "userId"};
                                                            db.update("profiles", cv, "userId=?", args);
                                                            String[] args1 = new String []{ "firstName"};
                                                            db.update("profiles", cv, "firstName=?", args1);
                                                            String[] args3 = new String []{ "lastName"};
                                                            db.update("profiles", cv, "lastName=?", args3);
                                                            String[] args4 = new String []{ "age"};
                                                            db.update("profiles", cv, "age=?", args4);
                                                            String[] args5 = new String []{ "city"};
                                                            db.update("profiles", cv, "city=?", args5);
                                                            String[] args6 = new String []{ "sex"};
                                                            db.update("profiles", cv, "sex=?", args6);
                                                            String[] args7 = new String []{ "work"};
                                                            db.update("profiles", cv, "work=?", args7);
                                                            String[] args8 = new String []{ "study"};
                                                            db.update("profiles", cv, "study=?", args8);
                                                            String[] args9 = new String []{ "other"};
                                                            db.update("profiles", cv, "other=?", args9);
                                                            String[] args10 = new String []{ "image"};
                                                            db.update("profiles", cv, "image=?", args10);
                                                            String[] args11 = new String []{ "phoneNumber"};
                                                            db.update("profiles", cv, "phoneNumber=?", args11);
                                                            String[] args12 = new String []{ "description"};
                                                            db.update("profiles", cv, "description=?", args12);*/
                                                            db.close();


                                                         }catch (JSONException exception){
                                                            Log.d("ParseProfileError", exception.toString());
                                                        }

                                                        Log.d("Respuesta de perfil",response.toString());
                                                    }
                                                },
                                                new Response.ErrorListener() {
                                                    @Override
                                                    public void onErrorResponse(VolleyError error) {
                                                        Log.d("ErrorRespuestaPerfil",error.toString());
                                                    }
                                                } );



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
