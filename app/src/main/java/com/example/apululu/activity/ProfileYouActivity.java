package com.example.apululu.activity;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.apululu.R;
import com.fangxu.allangleexpandablebutton.AllAngleExpandableButton;
import com.fangxu.allangleexpandablebutton.ButtonData;
import com.fangxu.allangleexpandablebutton.ButtonEventListener;

import java.util.ArrayList;
import java.util.List;

import static android.widget.Toast.LENGTH_LONG;

public class ProfileYouActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_you);

        SwitchCompat playGame = (SwitchCompat) findViewById(R.id.scPlayGame);

        playGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentPlayGame = new Intent(ProfileYouActivity.this,HomeActivity.class);
                startActivity(intentPlayGame);
            }
        });

        // **** Botón Menu Redondo
        installButton110to250();




    }


    // Funcion de creacion del Botòn Menu Redondo
    private void installButton110to250() {
        final AllAngleExpandableButton button = (AllAngleExpandableButton) findViewById(R.id.button_expandable_110_250);
        final List<ButtonData> buttonDatas = new ArrayList<>();
        int[] drawable = {R.drawable.edit,R.drawable.refresh, R.drawable.mark, R.drawable.settings};
        int[] color = {R.color.black, R.color.color_white, R.color.color_white, R.color.color_white};

        for (int i = 0; i < 4; i++){
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
                    case 2:Toast.makeText(ProfileYouActivity.this, "button " + i + " Clicked, Case 2", LENGTH_LONG).show();
                        break;
                    case 3:Toast.makeText(ProfileYouActivity.this, "button " + i + " Clicked, Case 3", LENGTH_LONG).show();
                        break;
                }
            }

            @Override
            public void onExpand() {
                Toast.makeText(ProfileYouActivity.this,"Se expandio", LENGTH_LONG).show();
            }

            @Override
            public void onCollapse() {

            }
        });
    }
}
