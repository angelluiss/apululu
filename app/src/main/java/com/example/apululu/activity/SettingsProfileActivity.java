package com.example.apululu.activity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.apululu.R;
import com.github.tonywills.loadingbutton.LoadingButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class SettingsProfileActivity extends AppCompatActivity {

    EditText etBirthday;
    Calendar calendario = Calendar.getInstance();
    LinearLayout logOutButton;

    private LoadingButton loadingButton;

    int clickedMalePreference = 0;
    int clickedFemalePreference = 1;
    int casesOther = 0;

    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_profile);

        logOutButton = (LinearLayout) findViewById(R.id.llLogout);

        //** Botones de seleccion
        final LinearLayout buttonMale = (LinearLayout) findViewById(R.id.llGenderAjustesMale);
        final LinearLayout buttonFemale = (LinearLayout) findViewById(R.id.llGenderAjustesFemale);
        final RelativeLayout buttonWork = (RelativeLayout) findViewById(R.id.llWorkAjustes);
        final RelativeLayout buttonStudent = (RelativeLayout) findViewById(R.id.llStudyAjustes);
        final RelativeLayout buttonOther = (RelativeLayout) findViewById(R.id.llOtherAjustes);
        final CheckBox cbWork =  (CheckBox) findViewById(R.id.cbWorkAjustes);
        final CheckBox cbStudent =  (CheckBox) findViewById(R.id.cbStudentAjustes);
        final CheckBox cbOther =  (CheckBox) findViewById(R.id.cbOtherAjustes);

        //*** Instancia de los Widgets del Menu
        LinearLayout buttonProfile = (LinearLayout) findViewById(R.id.llProfileButton);
        LinearLayout buttonFeed = (LinearLayout) findViewById(R.id.llFeedButton);
        SwitchCompat scPlay = (SwitchCompat) findViewById(R.id.scPlayGame);


        // *** Click listener para Menu //--------------------------------------------------------------------------------------------
        buttonProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsProfileActivity.this, ProfileYouActivity.class);
                startActivity(intent);
            }
        });

        buttonFeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsProfileActivity.this, ChataAndNotificationActivity.class);
                startActivity(intent);
            }
        });

        scPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsProfileActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });//--------------------------------------------------------------------------------------------


        //** Boton de Save

        loadingButton = (LoadingButton) findViewById(R.id.loading_button_ajustes);
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
        }); /////_____________________-----------------------------------------------------------

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



        //**** Log Out Button --- Cerrar Sesión
        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logOut();
                removeSharedPreference();
            }
        });

        //// Instancia de las preferencias Obtener Preferencias
        prefs = getSharedPreferences("Preferences", Context.MODE_PRIVATE);


        etBirthday = findViewById(R.id.etBirthday);
        etBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(SettingsProfileActivity.this, date, calendario
                        .get(Calendar.YEAR), calendario.get(Calendar.MONTH),
                        calendario.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }







    /// Metodo la del calendario para ejecutarse
    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            calendario.set(Calendar.YEAR, year);
            calendario.set(Calendar.MONTH, monthOfYear);
            calendario.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            actualizarInput();
        }

        private void actualizarInput() {
            String formatoDeFecha = "MM/dd/yy"; //In which you need put here
            SimpleDateFormat sdf = new SimpleDateFormat(formatoDeFecha, Locale.US);

            etBirthday.setText(sdf.format(calendario.getTime()));
        }
    };


    //// Funcion Log Out
    private void logOut(){
        Intent intent = new Intent(SettingsProfileActivity.this, LoginOrSingUPActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    /// Funcion para remover las preferencias de tu cuenta y hacer un Log out
    private void removeSharedPreference(){
        prefs.edit().clear().apply();
    }

}
