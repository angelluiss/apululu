package com.example.apululu.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.LinearLayout;

import com.example.apululu.R;

import java.util.Calendar;

import static com.example.apululu.R.id.calendarView1;

public class InsertCalendarActivity extends AppCompatActivity {
    String[] registro;
    CalendarView simpleCalendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_calendar);

        final LinearLayout buttonNext = (LinearLayout) findViewById(R.id.llInsertAge);
        simpleCalendarView = (CalendarView) findViewById(calendarView1);

        Bundle parametros = this.getIntent().getExtras();
        assert parametros != null;
        registro = getIntent().getExtras().getStringArray("registro");

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long yourmilliseconds = simpleCalendarView.getDate();
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(yourmilliseconds);

                int mYear = calendar.get(Calendar.YEAR);
                int mMonth = calendar.get(Calendar.MONTH);
                int mDay = calendar.get(Calendar.DAY_OF_MONTH);

                String birthay = mYear + "-" + mMonth + "-" + mDay;

                Intent intent = new Intent(InsertCalendarActivity.this,InsertMaleFemaleActivity.class);
                registro[5] = birthay;
                intent.putExtra("registro", registro);
                startActivity(intent);
            }
        });


    }
}
