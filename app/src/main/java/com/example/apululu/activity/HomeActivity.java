package com.example.apululu.activity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.apululu.helper.SQliteHelper;
import com.example.apululu.utils.cards;
import com.google.gson.Gson;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.db.rossdeckview.FlingChief;
import com.db.rossdeckview.FlingChiefListener;
import com.db.rossdeckview.RossDeckView;
import com.example.apululu.R;
import com.example.apululu.adapter.DeckAdapter;
import com.example.apululu.helper.HTTPHelper;
import com.example.apululu.utils.URLS;
import com.example.apululu.utils.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Wrapper;
import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;


public class HomeActivity extends AppCompatActivity implements FlingChiefListener.Actions, FlingChiefListener.Proximity  {

    private final static int DELAY = 1000;

    private List<Pair<String, Integer>> mItems;

    private DeckAdapter mAdapter;

    private View mLeftView;

    private View mUpView;

    private View mRightView;

    private View mDownView;

    private int[] mColors;

    private int mCount = 0;

    private HTTPHelper getMatches;

    private SharedPreferences preferences;

    private JSONArray matches;

    private String firstName;
    private String lastName;
    private String image;
    private String birthdate;
    private String userId;
    private String sex;
    private int distance;

    public Drawable drawableImagen;
    public InputStream inputstream;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        matches = new JSONArray();

        URLS urls = new URLS();
        getMatches = new HTTPHelper(this);
        preferences = getSharedPreferences("Preferences", Context.MODE_PRIVATE);

        getProfilesMatch();

        LinearLayout buttonProfile1 = (LinearLayout) findViewById(R.id.llProfileButton);
        LinearLayout buttonFeed1 = (LinearLayout) findViewById(R.id.llFeedButton);

        // **** Evento clik de Profile Button Menu
        buttonFeed1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentFeed = new Intent(HomeActivity.this, ChataAndNotificationActivity.class);
                startActivity(intentFeed);
            }
        });

        // **** Evento clik de Profile Button Menu
        buttonProfile1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentProfile = new Intent(HomeActivity.this, ProfileYouActivity.class);
                startActivity(intentProfile);
            }
        });

        // *** Inicializar
        mColors  = getResources().getIntArray(R.array.cardsBackgroundColors);
        mItems = new ArrayList<>();

        /// **** Crear Swipe Cards
        mItems.add(newItem());

        mAdapter = new DeckAdapter(this, mItems);

        RossDeckView mDeckLayout = (RossDeckView) findViewById(R.id.decklayout);
        mDeckLayout.setAdapter(mAdapter);
        mDeckLayout.setActionsListener(this);
        mDeckLayout.setProximityListener(this);

        mLeftView = findViewById(R.id.left);
        mUpView = findViewById(R.id.up);
        mRightView = findViewById(R.id.right);
     //   mDownView = findViewById(R.id.down);

        //-----START GET DB SQLITE DATES__________---------------------------------------------------------------------------------------------------------
        SQliteHelper dbHelper = new SQliteHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Cursor c = db.rawQuery("SELECT _id, sex,firtsName, lastName, image, distance, birthdate FROM people_matches", null);

        if (c != null) {
            c.moveToFirst();
            do {
                //Asignamos el valor en nuestras variables para usarlos en lo que necesitemos
                String sex = c.getString(c.getColumnIndex("sex"));
                String firtsName = c.getString(c.getColumnIndex("firtsName"));
                String lastName = c.getString(c.getColumnIndex("lastName"));
                String image = c.getString(c.getColumnIndex("image"));
                String distance = c.getString(c.getColumnIndex("distance"));
                String birthdate = c.getString(c.getColumnIndex("birthdate"));

            } while (c.moveToNext());
        }

        //Cerramos el cursor y la conexion con la base de datos
        c.close();
        db.close();
        //-------------------------------------------------------------------------------------------END__________---------


        DownloadImageFromPath(urls.UPLOAD_IMAGE + image);


    }

    // **** GET HTTP Request para perfiles

    private void getProfilesMatch(){

        URLS urls = new URLS();

        getMatches.getDataArray(urls.GET_MATCHES,Util.getTokenPrefs(preferences),"GET", new JSONArray(),new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d("match respuesta",response.toString());

                matches = response;
                parseJSONArray(matches);
            }
    },  new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toasty.error(HomeActivity.this, error.toString(), Toasty.LENGTH_LONG, true).show();
                Log.d("matchResponse",error.toString());
            }
        });
    }
    // ------ GET HTTP Request para perfiles ------------------------------------------------------------------


    //// ********   Eventos de Swipe Cards    -------------------------------------------------------------

    @Override
    public boolean onDismiss(@NonNull FlingChief.Direction direction, @NonNull View view) {
        Toast.makeText(this, "Dismiss to " + direction, Toast.LENGTH_SHORT).show();
        return true;
    }

    @Override
    public boolean onDismissed(@NonNull View view) {
        mItems.remove(0);
        mAdapter.notifyDataSetChanged();
        newItemWithDelay(DELAY);
        return true;
    }

    @Override
    public boolean onReturn(@NonNull View view) {
        return true;
    }

    @Override
    public boolean onReturned(@NonNull View view) {
        return true;
    }

    @Override
    public boolean onTapped() {
        Toast.makeText(this, "Tapped", Toast.LENGTH_SHORT).show();
        return true;
    }

    @Override
    public boolean onDoubleTapped() {
        Toast.makeText(this, "Double tapped", Toast.LENGTH_SHORT).show();
        return true;
    }

    @Override
    public void onProximityUpdate(@NonNull float[] proximities, @NonNull View view) {
        mLeftView.setScaleY((1 - proximities[0] >= 0) ? 1 - proximities[0] : 0);
        mUpView.setScaleX((1 - proximities[1] >= 0) ? 1 - proximities[1] : 0);
        mRightView.setScaleY((1 - proximities[2] >= 0) ? 1 - proximities[2] : 0);
    //    mDownView.setScaleX((1 - proximities[3] >= 0) ? 1 - proximities[3] : 0);
    }

    private Pair<String, Integer> newItem(){

        Pair<String, Integer> res = new Pair<>(firstName+ " " + lastName, mColors[mCount]);
        mCount = (mCount >= mColors.length - 1) ? 0 : mCount + 1;
        return res;
    }


    private void newItemWithDelay(int delay){

        final Pair<String, Integer> res = newItem();
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mItems.add(res);
                mAdapter.notifyDataSetChanged();
            }
        }, delay);
    }
    //// ------------   Eventos de Swipe Cards    ---------------------------------------------------------------------------------


    /// ****** Parse http Request-------------------------------------

    private void parseJSONArray(JSONArray array){

        try {

            for (int count = 0; count < array.length(); count++){
                JSONObject object =  array.getJSONObject(count);
                userId = object.get("userId").toString();
                firstName = object.get("firstName").toString();
                lastName = object.get("lastName").toString();
                sex =  object.get("sex").toString();
                image = object.get("image").toString();
                birthdate = object.get("birthdate").toString();
                distance = Integer.parseInt(object.get("distance").toString());

                putDBDates(sex, firstName, lastName, image, distance,birthdate);

                int tamaño = object.length();

                // Swipe Card Inicialization del Array list
                // Swipe Card Home Variables


                Log.d("arraydatos1",Integer.toString(tamaño));

            }
        }catch (JSONException exception){
            Log.d("arrayerror", exception.toString());
        }
    }

    public void putDBDates(String sex, String firstname, String lastname, String image, int distance,String birthadate){

        SQliteHelper dbHelper = new SQliteHelper(this);

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        if (db != null) {

            // Insert con ContentValues
            ContentValues cv = new ContentValues();
            cv.put("sex", sex);
            cv.put("firtsName", firstname);
            cv.put("lastName", lastname);
            cv.put("image", image);
            cv.put("distance", distance);
            cv.put("birthdate", birthadate);

            db.insert("people_matches", null, cv);
        }
    }

    public void DownloadImageFromPath(String path){
        InputStream in =null;
        Bitmap bmp=null;
      //  ImageView iv = (ImageView)findViewById(R.id.img1);
        int responseCode = -1;
        try{
            URL url = new URL(path);//"http://192.xx.xx.xx/mypath/img1.jpg
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setDoInput(true);
            con.connect();
            responseCode = con.getResponseCode();
            if(responseCode == HttpURLConnection.HTTP_OK)
            {
                //download

                in = con.getInputStream();
                bmp = BitmapFactory.decodeStream(in);
                drawableImagen = new BitmapDrawable(getResources(),bmp);
                in.close();
            }
        }
        catch(Exception ex){
            Log.e("Exception",ex.toString());
        }
    }
}
