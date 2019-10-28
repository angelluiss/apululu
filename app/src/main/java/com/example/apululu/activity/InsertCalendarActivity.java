package com.example.apululu.activity;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.apululu.R;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static com.example.apululu.R.id.calendarView1;

public class InsertCalendarActivity extends AppCompatActivity {
    String[] registro;
    EditText age;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_calendar);

        final LinearLayout buttonNext = (LinearLayout) findViewById(R.id.llInsertAge);
        final LinearLayout buttonNext2 = (LinearLayout) findViewById(R.id.llnextAge);
        age = (EditText) findViewById(calendarView1);

        Bundle parametros = this.getIntent().getExtras();
        assert parametros != null;
        registro = getIntent().getExtras().getStringArray("registro");
        
        age.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int passwordString = Integer.parseInt(age.getText().toString());
                if (passwordString < 18 || passwordString > 80){
                    age.setError("You must be around of 18 and 80 years old");
                }
            }
        });
        
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String birthay = age.getText().toString();
                int ageInt = Integer.parseInt(age.getText().toString());
                if ( ageInt < 18|| ageInt > 80) {
                    buttonNext2.setBackgroundResource(R.drawable.rounded_button_gradient_solid);
                    Intent intent = new Intent(InsertCalendarActivity.this, InsertMaleFemaleActivity.class);
                    registro[5] = convertIntToDate(ageInt).toString();
                    intent.putExtra("registro", registro);
                    startActivity(intent);
                }
            }
        });


    }

    public Date convertIntToDate(Integer intDate) {

        if (intDate < 100000 || intDate > 999999) {
            Log.e("Cannot Parse Date", String.valueOf(intDate));
            return null;
        }

        int intYear = intDate/100;
        int intMonth = intDate - (intYear * 100);

        Calendar result = new GregorianCalendar();
        result.set(intYear, intMonth - 1, 1, 0, 0, 0);

        return result.getTime();
    }
}
