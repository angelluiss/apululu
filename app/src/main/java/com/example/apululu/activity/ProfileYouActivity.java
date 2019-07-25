package com.example.apululu.activity;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.apululu.R;
import com.example.apululu.helper.SQliteHelper;
import com.example.apululu.utils.URLS;
import com.fangxu.allangleexpandablebutton.AllAngleExpandableButton;
import com.fangxu.allangleexpandablebutton.ButtonData;
import com.fangxu.allangleexpandablebutton.ButtonEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.widget.Toast.LENGTH_LONG;

public class ProfileYouActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_you);



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
                String userId = c.getString(c.getColumnIndex("userId"));
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

                TextView names = (TextView) findViewById(R.id.tvProfileTittle);
                TextView ocupattion = (TextView) findViewById(R.id.tvOccupationProfile);
                TextView cityProfile = (TextView) findViewById(R.id.tvCityProfile);
                TextView descriptionProfile = (TextView)findViewById(R.id.tvDescriptionProfile);
                names.setText(String.format("%s %s", firstName, lastName));
                if (!work.equals("null")){ocupattion.setText(work);}else{ocupattion.setText("");}
                if (!city.equals("null")){cityProfile.setText(city);}else{cityProfile.setText("");}
                if (!description.equals("null")){descriptionProfile.setText(description);}else{descriptionProfile.setText("");}

                CircleImageView profilePicture = (CircleImageView)findViewById(R.id.profile_image);
                ///* *****  Imagen con picasso
                Picasso.get() .load(URLS.UPLOAD_IMAGE + image).error(R.drawable.rounded_button_gradient_solid).placeholder(R.drawable.heart_1_like).into(profilePicture);

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
        int[] drawable = {R.drawable.plus,R.drawable.refresh, R.drawable.mark, R.drawable.settings,R.drawable.mark};
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
                        Toast.makeText(ProfileYouActivity.this, "button " + i + " Clicked, Case 1", LENGTH_LONG).show();
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
}
