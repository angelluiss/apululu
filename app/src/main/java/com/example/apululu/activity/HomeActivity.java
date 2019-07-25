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
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;

import android.os.Handler;
import android.provider.Settings;

import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import android.util.Pair;
import android.view.View;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.db.rossdeckview.FlingChief;
import com.db.rossdeckview.FlingChiefListener;
import com.db.rossdeckview.RossDeckView;
import com.example.apululu.utils.Cards;
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

import java.util.ArrayList;
import java.util.List;


import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;
import link.fls.swipestack.SwipeStack;


public class HomeActivity extends AppCompatActivity implements FlingChiefListener.Actions, FlingChiefListener.Proximity {

    ////
    private final static int DELAY = 1000;

    private List<Cards> mItems;

    private DeckAdapter mAdapter;

    private View mLeftView;

    private View mUpView;

    private View mRightView;

    private View mDownView;

    private int[] mColors;

    private int mCount = 0;
    ////

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

        //// ***** ------- Location Manager instancer
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        //// ***** ------- Array para los matches instancer
        matches = new JSONArray();

        //// ***** ------- Matches peticion instancer
        getMatches = new HTTPHelper(this);

        //// ***** ------- Obtener Preferencias instancer
        preferences = getSharedPreferences("Preferences", Context.MODE_PRIVATE);

        ////// **** -----  Obtener profiles con funcion get
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
        mColors  = getResources().getIntArray(R.array.cardsBackgroundColors);
        mItems = new ArrayList<>();

        mAdapter = new DeckAdapter(this, mItems);

        RossDeckView mDeckLayout = (RossDeckView) findViewById(R.id.decklayout);
        mDeckLayout.setAdapter(mAdapter);
        mDeckLayout.setActionsListener(this);
        mDeckLayout.setProximityListener(this);

        mLeftView = findViewById(R.id.left);
        mUpView = findViewById(R.id.up);
        mRightView = findViewById(R.id.right);
        mDownView = findViewById(R.id.down);

        //// _________________________---------

    }


    // **** GET HTTP Request para perfiles

    private void getProfilesMatch(){

        getMatches.getDataArray(URLS.GET_MATCHES,Util.getTokenPrefs(preferences),"GET", new JSONArray(),new Response.Listener<JSONArray>() {
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


                // Swipe Card Inicialization del Array list
                // Swipe Card Home Variables
            //    DownloadImageFromPath(urls.MAIN_URL_IMAGES + image);




                ///* *****  Imagen con picasso

               try{Picasso.get() .load(URLS.UPLOAD_IMAGE + image)
                           .error(R.drawable.rounded_button_gradient_solid)
                           .placeholder(R.drawable.heart_1_like).into(new Target() {
                           @Override
                           public void onBitmapLoaded (final Bitmap bitmap, Picasso.LoadedFrom from){
                               /* Save the bitmap or do something with it here */
                               int userID = Integer.parseInt(userId);
                               //Set it in the ImageView
                               String firtsAndlastName = firstName + " " + lastName;
                               newItemWithDelay( 0, firtsAndlastName, String.valueOf(distance), userID, bitmap);
                           }

                           @Override
                           public void onBitmapFailed(Exception e, Drawable errorDrawable) {

                           }

                           @Override
                           public void onPrepareLoad(Drawable placeHolderDrawable) {

                           }

                       });
               }catch (Exception IOException){

               }





            }
        }catch (JSONException exception){
            Log.d("arrayerror", exception.toString());
        }
    } /// ****** Parse http Request END-------------------------------------



//-----START GET DB SQLITE DATES__________---------------------------------------------------------------------------------------------------------

    public void getDBData() {
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
    }//-------------------------------------------------------------------------------------------END__________---------


    ////  *****  DB Put files
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

    /////__________________________------------

    @Override
    public boolean onDismiss(@NonNull FlingChief.Direction direction, @NonNull View view) {
        Cards actualCard = mItems.get(0);
        switch (direction) {
            case RIGHT:

                    HTTPHelper helper = new HTTPHelper(HomeActivity.this);
                    int userId = actualCard.getUserID();
                    final JSONObject dataJSON = new JSONObject();
                    try {
                        dataJSON.put("user", userId);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    helper.petitionData(URLS.USER_LIKE, Util.getTokenPrefs(preferences), "POST", dataJSON,new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    Toast.makeText(HomeActivity.this, response.toString() + " Liked", Toast.LENGTH_SHORT).show();
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(HomeActivity.this, error.toString() + " Liked", Toast.LENGTH_SHORT).show();

                                }
                            });
                break;
            case LEFT:
                HTTPHelper helperDislike = new HTTPHelper(HomeActivity.this);


                int userIdDislike = actualCard.getUserID();
                final JSONObject dataJSONDislike = new JSONObject();
                try {
                    dataJSONDislike.put("user", userIdDislike);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                helperDislike.petitionData(URLS.USER_DISLIKE, Util.getTokenPrefs(preferences), "POST", dataJSONDislike,new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Toast.makeText(HomeActivity.this, response.toString() + " Dislike", Toast.LENGTH_SHORT).show();

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(HomeActivity.this, error.toString() + " Disliked", Toast.LENGTH_SHORT).show();

                            }
                        });
                break;
            case TOP:

                break;
        }
        Toast.makeText(this, "Dismiss to " + direction, Toast.LENGTH_SHORT).show();
        return true;
    }

    @Override
    public boolean onDismissed(@NonNull View view) {

        mItems.remove(0);
        mAdapter.notifyDataSetChanged();
        //newItemWithDelay(DELAY);
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
        mDownView.setScaleX((1 - proximities[3] >= 0) ? 1 - proximities[3] : 0);
    }


    private Cards newItem(Bitmap imagen,String name, String location, Integer userID){

        Cards res = new Cards(imagen, name, location, userID);
        return res;
    }


    private void newItemWithDelay(int delay, String name,String location,int userID, Bitmap imagen){

        final Cards res = newItem(imagen, name, location,userID);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mItems.add(res);
                mAdapter.notifyDataSetChanged();
            }
        }, delay);
    }




    ///////////////////_______________-------------

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
