package com.example.apululu.activity;


import android.annotation.TargetApi;
import android.app.Dialog;
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
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.apululu.R;
import com.example.apululu.helper.HTTPHelper;
import com.example.apululu.helper.SQliteHelper;
import com.example.apululu.utils.URLS;
import com.example.apululu.utils.Util;
import com.fangxu.allangleexpandablebutton.AllAngleExpandableButton;
import com.fangxu.allangleexpandablebutton.ButtonData;
import com.fangxu.allangleexpandablebutton.ButtonEventListener;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.widget.Toast.LENGTH_LONG;

public class ProfileYouActivity extends AppCompatActivity {


    CircleImageView imageProfile;
    SharedPreferences prefs;


    Dialog myDialog;

    private static String APP_DIRECTORY = "Apululu/";
    private static String MEDIA_DIRECTORY = APP_DIRECTORY + "PictureApp";

    private final int MY_PERMISSIONS = 100;
    private final int PHOTO_CODE = 200;
    private final int SELECT_PICTURE = 300;
    private LinearLayout mRlView;
    private String mPath;
    int imageSelected;
    String idImage;
    HTTPHelper httpImages;
    int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_you);

        mRlView = (LinearLayout) findViewById(R.id.rlGallery);
        myDialog = new Dialog(ProfileYouActivity.this);
        prefs = getSharedPreferences("Preferences", Context.MODE_PRIVATE);

        // *** Instancia del los Widgets del menú
        LinearLayout buttonFeed = (LinearLayout) findViewById(R.id.llFeedButton);
        SwitchCompat playGame = (SwitchCompat) findViewById(R.id.scPlayGame);

        playGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentPlayGame = new Intent(ProfileYouActivity.this,HomeActivity.class);
                startActivity(intentPlayGame);
            }
        });

        buttonFeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileYouActivity.this, ChataAndNotificationActivity.class);
                startActivity(intent);
            }
        });

        ///Consulta de datos de la DB

        SQliteHelper dbHelper = new SQliteHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Cursor c = db.rawQuery("SELECT _id, userId,firstName, lastName, age, city, sex, work, study, other, image, phoneNumber, description  FROM profiles", null);

        if (c != null) {
            c.moveToFirst();
            do {
                //Asignamos el valor en nuestras variables para usarlos en lo que necesitemos
                userId = Integer.parseInt(c.getString(c.getColumnIndex("userId")));
                String firstName = c.getString(c.getColumnIndex("firstName"));
                String lastName = c.getString(c.getColumnIndex("lastName"));
                String age = c.getString(c.getColumnIndex("age"));
                String city = c.getString(c.getColumnIndex("city"));
                String sex = c.getString(c.getColumnIndex("sex"));
                String work = c.getString(c.getColumnIndex("work"));
                String study = c.getString(c.getColumnIndex("study"));
                String other = c.getString(c.getColumnIndex("other"));
                String image = c.getString(c.getColumnIndex("image"));
                String phoneNumber = c.getString(c.getColumnIndex("phoneNumber"));
                String description = c.getString(c.getColumnIndex("description"));

                Log.d("Imagennn123",image);


                TextView names = (TextView) findViewById(R.id.tvProfileTittle);
                TextView ocupattion = (TextView) findViewById(R.id.tvOccupationProfile);
                TextView cityProfile = (TextView) findViewById(R.id.tvCityProfile);
                TextView descriptionProfile = (TextView)findViewById(R.id.tvDescriptionProfile);
                TextView ageProfile = (TextView)findViewById(R.id.tvAgeProfile);
                names.setText(String.format("%s %s", firstName, lastName));
                if (!work.equals("null")){ocupattion.setText(work);}else{ocupattion.setText("");}
                if (!city.equals("null")){cityProfile.setText(city);}else{cityProfile.setText("");}
                if (!description.equals("null")){descriptionProfile.setText(description);}else{descriptionProfile.setText("");}



                SimpleDateFormat formatter6=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                Date date6 = null;
                try {
                    date6= formatter6.parse(age);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Calendar lCal = Calendar.getInstance();
                lCal.setTime(date6);
                int lYear = lCal.get(Calendar.YEAR);
                int lMonth = lCal.get(Calendar.MONTH) + 1;
                int lDay = lCal.get(Calendar.DATE);

                int age1 = (2019 - lYear);

                String ageProfiles = age1 + ",";


                if (!ageProfile.equals("null")){ageProfile.setText(ageProfiles);}else{ageProfile.setText("");}
                CircleImageView profilePicture = (CircleImageView)findViewById(R.id.profile_image);
                ///* *****  Imagen con picasso
                Picasso.get() .load(URLS.UPLOAD_IMAGE + image).error(R.drawable.ic_sinfoto).placeholder(R.drawable.ic_sinfoto).into(profilePicture);

                Log.d("Imagennn ",image);

            } while (c.moveToNext());
        }
        c.close();
        db.close();



        // **** Botón Menu Redondo
        installButton110to250();




    }

    // Funcion de creacion del Botòn Menu Redondo
    private void installButton110to250() {
        final AllAngleExpandableButton button = (AllAngleExpandableButton) findViewById(R.id.button_expandable_110_250);
        final List<ButtonData> buttonDatas = new ArrayList<>();
        int[] drawable = {R.drawable.refresh,R.drawable.ic_diamante, R.drawable.ic_camera, R.drawable.ic_preferencia,R.drawable.ic_ajuste};
        int[] color = {R.color.cardGreenColor, R.color.colorPrimary, R.color.colorPrimary, R.color.colorPrimary, R.color.colorPrimary};

        for (int i = 0; i < 5; i++){
            ButtonData buttonData;
            if(i==0){
                buttonData = ButtonData.buildIconButton(this, drawable[i],15);
            }else{
                buttonData = ButtonData.buildIconButton(this, drawable[i], 0);
            }
            buttonData.setBackgroundColorId(this, color[i]);
            buttonDatas.add(buttonData);
        }
        button.setButtonDatas(buttonDatas);
        setListener(button);
    }

    private void setListener(AllAngleExpandableButton button) {
        button.setButtonEventListener(new ButtonEventListener() {
            @Override
            public void onButtonClicked(int i) {
                switch (i){
                    case 1:
                        Toasty.info(ProfileYouActivity.this, "VIP Zone is disabled for this version", LENGTH_LONG).show();
                        break;
                    case 2:
                        Intent intent = new Intent(ProfileYouActivity.this,GalleryProfileActivity.class);
                        startActivity(intent);
                        break;
                    case 3:
                        Intent intent2 = new Intent(ProfileYouActivity.this,PreferencesActivity.class);
                        startActivity(intent2);
                        break;
                    case 4:
                        Intent intent3 = new Intent(ProfileYouActivity.this,SettingsProfileActivity.class);
                        startActivity(intent3);
                        break;
                }
            }

            @Override
            public void onExpand() {

            }

            @Override
            public void onCollapse() {

            }
        });
    }


    ///____________________________ Change Image Profile

    public void ShowPopup(View v) {
        myDialog.setContentView(R.layout.pop_up_gallery);

        TextView close = (TextView) myDialog.findViewById(R.id.tvClosePopup);
        ImageView camera = (ImageView) myDialog.findViewById(R.id.ivCameraPopup);
        RelativeLayout gallery = (RelativeLayout) myDialog.findViewById(R.id.llPhotoGallery);
        RelativeLayout delete = (RelativeLayout) myDialog.findViewById(R.id.llDelete);


        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(intent.createChooser(intent, "Selecciona app de imagen"), SELECT_PICTURE);
                myDialog.dismiss();
            }
        });

        if(mayRequestStoragePermission())
            camera.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openCamera();
                    myDialog.dismiss();
                }
            });
        else
            Toasty.info(ProfileYouActivity.this, "Please, accept permissions", Toasty.LENGTH_LONG).show();

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
    }

    private boolean mayRequestStoragePermission() {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
            return true;

        if ((checkSelfPermission(WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) &&
                (checkSelfPermission(CAMERA) == PackageManager.PERMISSION_GRANTED))
            return true;

        if ((shouldShowRequestPermissionRationale(WRITE_EXTERNAL_STORAGE)) || (shouldShowRequestPermissionRationale(CAMERA))) {
            Snackbar.make(mRlView, "Los permisos son necesarios para poder usar la aplicación",
                    Snackbar.LENGTH_INDEFINITE).setAction(android.R.string.ok, new View.OnClickListener() {
                @TargetApi(Build.VERSION_CODES.M)
                @Override
                public void onClick(View v) {
                    requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, CAMERA}, MY_PERMISSIONS);
                }
            });
        } else {
            requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, CAMERA}, MY_PERMISSIONS);
        }
        return false;
    }

    private void openCamera() {
        File file = new File(Environment.getExternalStorageDirectory(), MEDIA_DIRECTORY);
        boolean isDirectoryCreated = file.exists();

        if(!isDirectoryCreated)
            isDirectoryCreated = file.mkdirs();

        if(isDirectoryCreated){
            Long timestamp = System.currentTimeMillis() / 1000;
            String imageName = timestamp.toString() + ".jpg";

            mPath = Environment.getExternalStorageDirectory() + File.separator + MEDIA_DIRECTORY
                    + File.separator + imageName;

            File newFile = new File(mPath);

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(newFile));
            startActivityForResult(intent, PHOTO_CODE);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("file_path", mPath);
        outState.putString("id_image", idImage);
        outState.putInt("click_image", imageSelected);
        outState.putInt("userID", userId);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mPath = savedInstanceState.getString("file_path");
        idImage = savedInstanceState.getString("id_image");
        imageSelected = savedInstanceState.getInt("click_image");
        userId = savedInstanceState.getInt("userID");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        httpImages = new HTTPHelper(ProfileYouActivity.this);
        imageProfile = (CircleImageView)findViewById(R.id.profile_image);
        if(resultCode == RESULT_OK){
            switch (requestCode){
                case PHOTO_CODE:
                    MediaScannerConnection.scanFile(this,
                            new String[]{mPath}, null,
                            new MediaScannerConnection.OnScanCompletedListener() {
                                @Override
                                public void onScanCompleted(String path, Uri uri) {
                                    Log.i("ExternalStorage", "Scanned " + path + ":");
                                    Log.i("ExternalStorage", "-> Uri = " + uri);
                                }
                            });

                    Bitmap bitmap = BitmapFactory.decodeFile(mPath);
                    Log.d("imagenTomada", bitmap.toString());
                    //   Drawable d = new BitmapDrawable(getResources(), bitmap);
                    switch (imageSelected){
                        case 1:
                            break;
                        case 2:Log.d("imagecliked",String.valueOf(imageSelected));
                            break;
                    }
                    Log.d("imagecliked",String.valueOf(imageSelected));

                    imageProfile.setImageBitmap(bitmap);
                    BitmapFactory.Options opt = new BitmapFactory.Options();
                    opt.inJustDecodeBounds = true;
                    BitmapFactory.decodeFile(mPath, opt);
                    Log.d("mimetype",opt.outMimeType);
                    String imageBase64 = getStringImagen(bitmap);
                    Log.d("concatenaresult",imageBase64);


                    JSONObject imageObjet = new JSONObject();
                    try {
                        imageObjet.put("image",imageBase64);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    httpImages.petitionData(URLS.MAIN_URL + "me/profile/image",Util.getTokenPrefs(prefs), "PATCH",imageObjet, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d("uploadFromCAmeera",response.toString());
                            try {
                                String imagenProfile = response.getString("image");
                                SQliteHelper dbHelper = new SQliteHelper(getApplicationContext());
                                SQLiteDatabase db = dbHelper.getWritableDatabase();
                                ContentValues cv = new ContentValues();
                                cv.put("image", imagenProfile);
                                String[] args = new String []{ String.valueOf(userId)};
                                db.update("profiles", cv, "userId=?", args);
                                Log.d("userID",String.valueOf(userId));
                                db.close();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    },  new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("uplResponse",error.toString());
                        }
                    });

                    break;
                case SELECT_PICTURE:
                    Uri path = data.getData();
                    imageProfile.setImageURI(path);
                    Bitmap bitmap2 = ((BitmapDrawable)imageProfile.getDrawable()).getBitmap();
                    BitmapFactory.Options opt1 = new BitmapFactory.Options();
                    opt1.inJustDecodeBounds = true;
                    BitmapFactory.decodeFile(path.toString(), opt1);
                    //     Log.d("mpath",mPath);
                    //   Log.d("mimetype",path.toString());
                    //   Log.d("mimetype231", opt1.outMimeType);
                    String imageBase6412 = getStringImagen(bitmap2);


                    JSONObject imageObjetCamera = new JSONObject();
                    try {
                        imageObjetCamera.put("image",imageBase6412);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    httpImages.petitionData(URLS.MAIN_URL + "me/profile/image",Util.getTokenPrefs(prefs), "PATCH",imageObjetCamera, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d("uploadFromGallery",response.toString());
                            try {
                                String imagenProfile = response.getString("image");
                                SQliteHelper dbHelper = new SQliteHelper(getApplicationContext());
                                SQLiteDatabase db = dbHelper.getWritableDatabase();
                                ContentValues cv = new ContentValues();
                                cv.put("image", imagenProfile);
                                String[] args = new String []{ String.valueOf(userId)};
                                db.update("profiles", cv, "userId=?", args);
                                Log.d("userID",String.valueOf(userId));
                                db.close();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },  new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("uplResponse",error.toString());
                        }
                    });
                    break;

            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == MY_PERMISSIONS){
            if(grantResults.length == 2 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(ProfileYouActivity.this, "Permisos aceptados", Toast.LENGTH_SHORT).show();
                //  mOptionButton.setEnabled(true);
            }
        }else{
            showExplanation();
        }
    }

    private void showExplanation() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ProfileYouActivity.this);
        builder.setTitle("Permisos denegados");
        builder.setMessage("Para usar las funciones de la app necesitas aceptar los permisos");
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getPackageName(), null);
                intent.setData(uri);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });

        builder.show();
    }

    public String getStringImagen(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.NO_WRAP);
        Log.d("imagen321", encodedImage);
        return "data:" + "image/jpeg" + ";base64," + encodedImage;
    } ///____________________________  ///____________________________ Change Image Profile

}
