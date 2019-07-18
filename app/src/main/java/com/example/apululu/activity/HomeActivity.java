package com.example.apululu.activity;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;

import android.provider.Settings;

import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import android.view.View;

import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.apululu.adapter.Cards;
import com.example.apululu.helper.SQliteHelper;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.apululu.R;
import com.example.apululu.adapter.DeckAdapter;
import com.example.apululu.helper.HTTPHelper;
import com.example.apululu.utils.URLS;
import com.example.apululu.utils.Util;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


import es.dmoral.toasty.Toasty;
import link.fls.swipestack.SwipeStack;


public class HomeActivity extends AppCompatActivity  {

    private final static int DELAY = 1000;


    private DeckAdapter mAdapter;

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
    private float distance;

    private SwipeStack cardStack;
    private DeckAdapter cardsAdapter;
    private ArrayList<Cards> cardItems;
    private View btnCancel;
    private View btnLove;
    private int currentPosition;

    public Drawable drawableImagen;
    private Target target;
    private Bitmap mIcon;

    LocationManager locationManager;
    double longitudeNetwork, latitudeNetwork;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        matches = new JSONArray();


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

        // *** Inicializar Swipes Widgets
        cardStack = (SwipeStack) findViewById(R.id.container);
        btnCancel = findViewById(R.id.cancel);
        btnLove = findViewById(R.id.love);

        setCardStackAdapter(R.drawable.button_rounded_gray, firstName, lastName);
        currentPosition = 0;

        //Handling swipe event of Cards stack
        cardStack.setListener(new SwipeStack.SwipeStackListener() {
            @Override
            public void onViewSwipedToLeft(int position) {
                currentPosition = position + 1;
            }

            @Override
            public void onViewSwipedToRight(int position) {
                currentPosition = position + 1;
            }

            @Override
            public void onStackEmpty() {

            }
        });

        /// **** Crear Swipe Cards
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cardStack.swipeTopViewToRight();
            }
        });

        btnLove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(HomeActivity.this, "You liked " + cardItems.get(currentPosition).getName(),
                        Toast.LENGTH_SHORT).show();
                cardStack.swipeTopViewToLeft();
            }
        });


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





    }

    private void setCardStackAdapter(int imagenID, String name, String lastName) {
        cardItems = new ArrayList<>();

    //    cardItems.add(new Cards(imagenID, name, lastName));
        cardItems.add(new Cards(R.drawable.rounded_button_gradient_one, "Do Ha", "Nghe An"));
        cardItems.add(new Cards(R.drawable.rounded_button_gradient_one, "Dong Nhi", "Hue"));
        cardItems.add(new Cards(R.drawable.rounded_button_gradient_one, "Le Quyen", "Sai Gon"));
        cardItems.add(new Cards(R.drawable.rounded_button_gradient_one, "Phuong Linh", "Thanh Hoa"));
        cardItems.add(new Cards(R.drawable.rounded_button_gradient_one, "Phuong Vy", "Hanoi"));
        cardItems.add(new Cards(R.drawable.rounded_button_gradient_one, "Ha Ho", "Da Nang"));
        Log.d("cards", cardItems.toString());

        cardsAdapter = new DeckAdapter(this, cardItems);
        cardStack.setAdapter(cardsAdapter);
    }

    // **** GET HTTP Request para perfiles

    private void getProfilesMatch(){

        final URLS urls = new URLS();

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
    }// ------ GET HTTP Request para perfiles ------------------------------------------------------------------



    //// ********   Eventos de Geolocalización    -------------------------------------------------------------

    private boolean checkLocation() {
        if (!isLocationEnabled())
            showAlert();
        return isLocationEnabled();
    }

    private void showAlert() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Enable Location")
                .setMessage("Su ubicación esta desactivada.\npor favor active su ubicación " +
                        "usa esta app")
                .setPositiveButton("Configuración de ubicación", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(myIntent);
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    }
                });
        dialog.show();
    }

    private boolean isLocationEnabled() {
        return locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }


    public void toggleNetworkUpdates() {
        if (!checkLocation())
            return;
       // Button button = (Button) view; button.getText().equals(getResources().getString(R.string.pause));
        if (!checkLocation()) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            }
            locationManager.removeUpdates(locationListenerNetwork);
         //   button.setText(R.string.resume);
        }
        else {
            locationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER, 20 * 1000, 10, locationListenerNetwork);
            Toast.makeText(this, "Network provider started running", Toast.LENGTH_LONG).show();
           // button.setText(R.string.pause);
        }
    }

    private final LocationListener locationListenerNetwork = new LocationListener() {
        public void onLocationChanged(Location location) {
            longitudeNetwork = location.getLongitude();
            latitudeNetwork = location.getLatitude();

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.d("longitud", String.valueOf(longitudeNetwork));
                    Log.d("latitud", String.valueOf(latitudeNetwork));
            //        longitudeValueNetwork.setText(longitudeNetwork + "");
            //        latitudeValueNetwork.setText(latitudeNetwork + "");
                    Toast.makeText(HomeActivity.this, "Network Provider update", Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {
        }

        @Override
        public void onProviderEnabled(String s) {

        }
        @Override
        public void onProviderDisabled(String s) {

        }
    };//// ------------   Eventos de Geolocalización    ---------------------------------------------------------------------------------



    /// ****** Parse http Request-------------------------------------

    private void parseJSONArray(JSONArray array){

        try {
            URLS urls = new URLS();

            for (int count = 0; count < array.length(); count++){
                JSONObject object =  array.getJSONObject(count);
                userId = object.get("userId").toString();
                firstName = object.get("firstName").toString();
                lastName = object.get("lastName").toString();
                sex =  object.get("sex").toString();
                image = object.get("image").toString();
                birthdate = object.get("birthdate").toString();
                distance = Float.parseFloat(object.get("distance").toString());

       //         putDBDates(sex, firstName, lastName, image, distance,birthdate);

                int tamaño = object.length();

                // Swipe Card Inicialization del Array list
                // Swipe Card Home Variables
            //    DownloadImageFromPath(urls.MAIN_URL_IMAGES + image);


                Log.d("arraydatos1",Integer.toString(tamaño));

            }
        }catch (JSONException exception){
            Log.d("arrayerror", exception.toString());
        }
    }

    public void putDBDates(String sex, String firstname, String lastname, String image, float distance,String birthadate){

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

  /*  public Bitmap DownloadImageFromPath(String path){

        target = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                Log.d("Bitmap_Ejecuto", "on bitmap loaded");
                Bitmap mIcon = bitmap;
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                Log.d("Error_bitmap", String.valueOf(errorDrawable));
            }


            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
                Log.d("PlaceholderBitmap", String.valueOf(placeHolderDrawable));
                Drawable mIcon = placeHolderDrawable;
            }
        };

        Picasso.get()
                .load(path)
                .into(target);
        Log.d("target", String.valueOf(target));

        return mIcon;
    }

    @Override
    public void onDestroy() {  // could be in onPause or onStop
        Picasso.get().cancelRequest(target);
        super.onDestroy();
    } */
}
