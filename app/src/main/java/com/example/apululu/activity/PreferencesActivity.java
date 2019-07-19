package com.example.apululu.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.example.apululu.R;
import com.github.tonywills.loadingbutton.HorizontalLoadingButton;
import com.github.tonywills.loadingbutton.LoadingButton;


public class PreferencesActivity extends AppCompatActivity {

    int clickedMalePreference = 0;
    int clickedFemalePreference = 1;
    int casesOther = 0;

    private LoadingButton loadingButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);

        //** Botones de seleccion
        final LinearLayout buttonMale = (LinearLayout) findViewById(R.id.llGenderPreferenceMale);
        final LinearLayout buttonFemale = (LinearLayout) findViewById(R.id.llGenderPreferenceFemale);
        final RelativeLayout buttonWork = (RelativeLayout) findViewById(R.id.llWorkPreference);
        final RelativeLayout buttonStudent = (RelativeLayout) findViewById(R.id.llStudyPreference);
        final RelativeLayout buttonOther = (RelativeLayout) findViewById(R.id.llOtherPreference);
        final CheckBox cbWork =  (CheckBox) findViewById(R.id.cbWorkPreference);
        final CheckBox cbStudent =  (CheckBox) findViewById(R.id.cbStudentPreference);
        final CheckBox cbOther =  (CheckBox) findViewById(R.id.cbOtherPreference);

        //** Boton de Save

        loadingButton = (LoadingButton) findViewById(R.id.loading_button);
        loadingButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                loadingButton.setLoading(true);
                new Handler().postDelayed(new Runnable() {
                    @Override public void run() {
                        loadingButton.setLoading(false);

                    }
                }, 3000);
            }
        });




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

        //*** Configuracion  de los efectos Male Female
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

        // Profession Click Listeners.... Eventos de cambios en la UI
        cbWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!cbWork.isChecked()){
                    cbWork.setChecked(true);
                    casesOther++;
              //      selector [0] = "profession";
                    buttonWork.setBackgroundResource(R.drawable.button_rounded_blue);
                }else {
                    cbWork.setChecked(false);
                    casesOther--;
            //        selector [0] = "";
                    buttonWork.setBackgroundResource(R.drawable.button_rounded_dark_gray);
                }
            }
        });
        buttonWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!cbWork.isChecked()){
                    cbWork.setChecked(true);
                    casesOther++;
                //    selector [0] = "profession";
                    buttonWork.setBackgroundResource(R.drawable.button_rounded_blue);
                }else {
                    cbWork.setChecked(false);
                    casesOther--;
              //      selector [0] = "";
                    buttonWork.setBackgroundResource(R.drawable.button_rounded_dark_gray);
                }
            }
        });

        // Student Click Listeners.... Eventos de cambios en la UI
        cbStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!cbStudent.isChecked()){
                    cbStudent.setChecked(true);
                    casesOther++;
                 //   selector [1] = "student";
                    buttonStudent.setBackgroundResource(R.drawable.button_rounded_blue);
                }else {
                    cbStudent.setChecked(false);
                    casesOther--;
               //     selector [1] = "";
                    buttonStudent.setBackgroundResource(R.drawable.button_rounded_dark_gray);
                }
            }
        });
        buttonStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!cbStudent.isChecked()){
                    cbStudent.setChecked(true);
                    casesOther++;
                 //   selector [1] = "student";
                    buttonStudent.setBackgroundResource(R.drawable.button_rounded_blue);
                }else {
                    cbStudent.setChecked(false);
                    casesOther--;
                //    selector [1] = "";
                    buttonStudent.setBackgroundResource(R.drawable.button_rounded_dark_gray);
                }
            }
        });

        // Student Click Listeners.... Eventos de cambios en la UI
        cbOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!cbOther.isChecked()){
                    cbOther.setChecked(true);
                    casesOther++;
                  //  selector [2] = "other";
                    buttonOther.setBackgroundResource(R.drawable.button_rounded_blue);
                }else {
                    cbOther.setChecked(false);
                    casesOther--;
                  //  selector [2] = "";
                    buttonOther.setBackgroundResource(R.drawable.button_rounded_dark_gray);
                }
            }
        });
        buttonOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!cbOther.isChecked()){
                    cbOther.setChecked(true);
                    casesOther++;
                 //   selector [2] = "other";
                    buttonOther.setBackgroundResource(R.drawable.button_rounded_blue);
                }else {
                    cbOther.setChecked(false);
                    casesOther--;
                  //  selector [2] = "";
                    buttonOther.setBackgroundResource(R.drawable.button_rounded_dark_gray);
                }
            }
        });




    }
}
