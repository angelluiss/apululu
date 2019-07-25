package com.example.apululu.activity;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.apululu.R;
import com.example.apululu.helper.HTTPHelper;
import com.example.apululu.utils.URLS;
import com.example.apululu.utils.Util;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;

import es.dmoral.toasty.Toasty;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class GalleryProfileActivity extends AppCompatActivity {

    Dialog myDialog;

    private static String APP_DIRECTORY = "Apululu/";
    private static String MEDIA_DIRECTORY = APP_DIRECTORY + "PictureApp";

    private final int MY_PERMISSIONS = 100;
    private final int PHOTO_CODE = 200;
    private final int SELECT_PICTURE = 300;

    private ImageView mSetImage;
    private LinearLayout mRlView;
    private String mPath;
    private ImageView image1;
    ImageView image2;
    ImageView image3;
    ImageView image4;
    ImageView image5;
    ImageView image6;
    HTTPHelper httpImages;
    String idImage;
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_profile);

        // *** Instancia de las imagenes
        image1 = (ImageView)findViewById(R.id.ivPhoto1);
        image2 = (ImageView)findViewById(R.id.ivPhoto2);
        image3 = (ImageView)findViewById(R.id.ivPhoto3);
        image4 = (ImageView)findViewById(R.id.ivPhoto4);
        image5 = (ImageView)findViewById(R.id.ivPhoto5);
        image6 = (ImageView)findViewById(R.id.ivPhoto6);
        ImageView image7 = (ImageView)findViewById(R.id.ivPhoto7);


        mRlView = (LinearLayout) findViewById(R.id.rlGallery);

        prefs = getSharedPreferences("Preferences", Context.MODE_PRIVATE);

        httpImages = new HTTPHelper(GalleryProfileActivity.this);

        httpImages.getDataArray(URLS.MAIN_URL + "/me/photos", Util.getTokenPrefs(prefs),"GET", null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int ite = 0; ite < response.length(); ite++){
                    try {
                        JSONObject imageObject = response.getJSONObject(ite);
                        idImage = imageObject.getString("id");
                        String imageData = imageObject.getString("filename");
                        final int ite2 = ite;
                        Picasso.get().load(URLS.MAIN_URL_IMAGES + "uploads/photo/" + imageData).into(new Target() {
                            @Override
                            public void onBitmapLoaded (final Bitmap bitmap, Picasso.LoadedFrom from){
                                /* Save the bitmap or do something with it here */
                                switch (ite2){
                                    case 0:
                                        image1.setImageBitmap(bitmap);
                                        break;
                                    case 1:
                                        image2.setImageBitmap(bitmap);
                                        break;
                                    case 2:
                                        image3.setImageBitmap(bitmap);
                                        break;
                                    case 3:
                                        image4.setImageBitmap(bitmap);
                                        break;
                                    case 4:
                                        image5.setImageBitmap(bitmap);
                                        break;
                                    case 5:
                                        image6.setImageBitmap(bitmap);
                                        break;
                                }
                            }

                            @Override
                            public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                            }

                            @Override
                            public void onPrepareLoad(Drawable placeHolderDrawable) {

                            }

                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        },  new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("matchResponse",error.toString());
            }
        });

        // *** Instancia del los Widgets del menú
        LinearLayout buttonProfile = (LinearLayout) findViewById(R.id.llProfileButton);
        LinearLayout buttonFeed = (LinearLayout) findViewById(R.id.llFeedButton);
        SwitchCompat scPlay = (SwitchCompat) findViewById(R.id.scPlayGame);

        buttonProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GalleryProfileActivity.this, ProfileYouActivity.class);
                startActivity(intent);
            }
        });

        buttonFeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GalleryProfileActivity.this, ChataAndNotificationActivity.class);
                startActivity(intent);
            }
        });

        scPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GalleryProfileActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        myDialog = new Dialog(GalleryProfileActivity.this);

    }

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
                    Log.d("Camera", "stos");
                }
            });
        else
            Toasty.info(GalleryProfileActivity.this, "Please, accept permissions", Toasty.LENGTH_LONG).show();

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
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mPath = savedInstanceState.getString("file_path");
        idImage = savedInstanceState.getString("id_image");
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

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
                    image1.setImageBitmap(bitmap);
                    BitmapFactory.Options opt = new BitmapFactory.Options();
                    opt.inJustDecodeBounds = true;
                    BitmapFactory.decodeFile(mPath, opt);
                    Log.d("mimetype",opt.outMimeType);
                    String imageBase64 = "data:" + opt.outMimeType + ";base64," + getStringImagen(bitmap);
                    ;
                    httpImages.getDataArray(URLS.MAIN_URL + "/me/photos/" + idImage,Util.getTokenPrefs(prefs), "DELETE",null, new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            Log.d("Resposefadfa",response.toString());
                        }
                    },  new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("deleteResponse",error.toString());
                        }
                    });

                    JSONObject imageObjet = new JSONObject();
                    try {
                        imageObjet.put("image",imageBase64);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    httpImages.petitionData(URLS.MAIN_URL + "me/photos",Util.getTokenPrefs(prefs), "POST",imageObjet, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d("uploadFromCAmeera",response.toString());
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
                    image1.setImageURI(path);
                    Bitmap bitmap2 = ((BitmapDrawable)image1.getDrawable()).getBitmap();

                    httpImages.getDataArray(URLS.MAIN_URL + "/me/photos/" + idImage,Util.getTokenPrefs(prefs), "DELETE",null, new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            Log.d("Resposefadfa",response.toString());
                        }
                    },  new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("deleteResponse",error.toString());
                        }
                    });

                    JSONObject imageObjetCamera = new JSONObject();
                    try {
                        imageObjetCamera.put("image",getStringImagen(bitmap2));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    httpImages.petitionData(URLS.MAIN_URL + "/me/photos/",Util.getTokenPrefs(prefs), "POST",imageObjetCamera, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d("uploadFromGallery",response.toString());
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
                Toast.makeText(GalleryProfileActivity.this, "Permisos aceptados", Toast.LENGTH_SHORT).show();
              //  mOptionButton.setEnabled(true);
            }
        }else{
            showExplanation();
        }
    }

    private void showExplanation() {
        AlertDialog.Builder builder = new AlertDialog.Builder(GalleryProfileActivity.this);
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
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        Log.d("imagen", encodedImage);
        return encodedImage;
    }


}
