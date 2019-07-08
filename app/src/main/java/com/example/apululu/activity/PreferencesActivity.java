package com.example.apululu.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.example.apululu.R;

public class PreferencesActivity extends AppCompatActivity {

    int clickedMalePreference = 0;
    int clickedFemalePreference = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);

        //** Botones de seleccion
        final LinearLayout buttonMale = (LinearLayout) findViewById(R.id.llGenderPreferenceMale);
        final LinearLayout buttonFemale = (LinearLayout) findViewById(R.id.llGenderPreferenceFemale);
        LinearLayout buttonWork = (LinearLayout) findViewById(R.id.llWorkPreference);
        LinearLayout buttonStudent = (LinearLayout) findViewById(R.id.llStudyPreference);
        LinearLayout buttonOther = (LinearLayout) findViewById(R.id.llOtherPreference);

        //*** Instancia de los Widgets del Menu
        LinearLayout buttonProfile = (LinearLayout) findViewById(R.id.llProfileButton);
        LinearLayout buttonFeed = (LinearLayout) findViewById(R.id.llFeedButton);
        SwitchCompat scPlay = (SwitchCompat) findViewById(R.id.scPlayGame);

        //** Instancia del Seek bar Age
        final SeekBar seekBarAge = (SeekBar) findViewById(R.id.sbAgePreference);
        final TextView seekbarText = (TextView) findViewById(R.id.tvSBAge);

        //** Instancia del Seek bar Distancia
        final SeekBar seekBarDistance = (SeekBar)findViewById(R.id.sbDistancePreference);
        final TextView seekbarTextDistance = (TextView) findViewById(R.id.tvSBDistance);



        //*** Mostrar el valor de la barra de edad //--------------------------------------------------------------------------------------------
        seekBarAge.setProgress(18);
        seekbarTextDistance.setText("200");
        seekBarDistance.setProgress(200);

        seekBarAge.setOnSeekBarChangeListener (new OnSeekBarChangeListener() {

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                final int seekBarAgeValue = seekBarAge.getProgress();
                final String sBAgeVAlue = Integer.toString(seekBarAgeValue);
                seekbarText.setText(sBAgeVAlue);
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        }); //--------------------------------------------------------------------------------------------

        //*** Mostrar el valor de la barra de edad //--------------------------------------------------------------------------------------------
        seekBarDistance.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                final int seekBarDistanceValue = seekBarDistance.getProgress();
                final String sBdistanceValue = Integer.toString(seekBarDistanceValue);
                seekbarTextDistance.setText(sBdistanceValue);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });  //--------------------------------------------------------------------------------------------


        // *** Click listener para Menu //--------------------------------------------------------------------------------------------
        buttonProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PreferencesActivity.this, ProfileYouActivity.class);
                startActivity(intent);
            }
        });

        buttonFeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PreferencesActivity.this, ChataAndNotificationActivity.class);
                startActivity(intent);
            }
        });

        scPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PreferencesActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });//--------------------------------------------------------------------------------------------

        //*** Configuracion  de los efectos de los Botones de Preferencias
        buttonMale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((clickedMalePreference != clickedFemalePreference)){
                    clickedMalePreference++;
                    buttonFemale.setBackgroundResource(R.drawable.button_rounded_dark_gray);
                    buttonMale.setBackgroundResource(R.drawable.button_rounded_blue);
                }
            }
        });

        buttonFemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((clickedFemalePreference == clickedMalePreference) || clickedFemalePreference >= 1){
                    clickedFemalePreference++;
                    buttonMale.setBackgroundResource(R.drawable.button_rounded_dark_gray);
                    buttonFemale.setBackgroundResource(R.drawable.button_rounded_blue);
                }
            }
        });


    }
}
