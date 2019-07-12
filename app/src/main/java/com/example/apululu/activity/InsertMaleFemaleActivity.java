package com.example.apululu.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apululu.R;

public class InsertMaleFemaleActivity extends AppCompatActivity {

    // Variables para clickeo
    int clickedMale = 0;
    int clickedFemale = 1;
    boolean maleClicked = false;
    boolean femaleClicked = false;
    String[] registro;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_male_female);

        // Instaciaciòn de los Widgets
        final LinearLayout buttonNext = (LinearLayout) findViewById(R.id.llButtonMaleFemale);
        final LinearLayout buttonMale = (LinearLayout) findViewById(R.id.llButtonMale);
        final TextView male = (TextView) findViewById(R.id.tvMale);
        final LinearLayout buttonFemale = (LinearLayout) findViewById(R.id.llButtonFemale);
        final TextView female = (TextView) findViewById(R.id.tvFemale);

        Bundle parametros = this.getIntent().getExtras();
        assert parametros != null;
        registro = getIntent().getExtras().getStringArray("registro");

        //-------------- Click Male Button Cambia de color
        buttonMale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((clickedMale != clickedFemale)){
                    clickedMale++;
                    buttonFemale.setBackgroundResource(R.drawable.button_rounded_gray);
                    female.setTextColor(getResources().getColor(R.color.color_gray));
                    buttonMale.setBackgroundResource(R.drawable.button_rounded_blue);
                    male.setTextColor(getResources().getColor(R.color.black));
                    maleClicked = true;
                    femaleClicked = false;
                }
            }
        });
        //-------------- Click Female Button Cambia de color
        buttonFemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((clickedFemale == clickedMale) || clickedFemale >= 1){
                    clickedFemale++;
                    buttonMale.setBackgroundResource(R.drawable.button_rounded_gray);
                    male.setTextColor(getResources().getColor(R.color.color_gray));
                    buttonFemale.setBackgroundResource(R.drawable.button_rounded_blue);
                    female.setTextColor(getResources().getColor(R.color.black));
                    maleClicked = false;
                    femaleClicked = true;
                }
            }
        });


        //-------- Boton Next Verificación de estado de los botones
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(maleClicked){
                    registro[6] = "male";
                }else if (femaleClicked){
                    registro[6] = "female";
                }
                if (clickedMale > 0 || clickedFemale > 1){
                    buttonNext.setBackgroundResource(R.drawable.rounded_button_gradient_solid);
                    Intent intent = new Intent(InsertMaleFemaleActivity.this, InsertPhoneActivity.class);
                    intent.putExtra("registro",registro);
                    startActivity(intent);
                }else{
                    Toast.makeText(InsertMaleFemaleActivity.this, "Select a gender", Toast.LENGTH_LONG).show();
                }

            }
        });
    }
}
