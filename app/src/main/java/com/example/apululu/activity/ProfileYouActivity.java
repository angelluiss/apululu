package com.example.apululu.activity;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.apululu.R;
import com.example.apululu.adapter.ViewProfilesDatesAdapter;
import com.example.apululu.fragment.Profile1Fragment;
import com.example.apululu.fragment.Profile2Fragment;
import com.example.apululu.utils.NotificationHandler;
import com.fangxu.allangleexpandablebutton.AllAngleExpandableButton;
import com.fangxu.allangleexpandablebutton.ButtonData;
import com.fangxu.allangleexpandablebutton.ButtonEventListener;

import java.util.ArrayList;
import java.util.List;

import static android.widget.Toast.LENGTH_LONG;

public class ProfileYouActivity extends AppCompatActivity implements Profile1Fragment.OnFragmentInteractionListener,
        Profile2Fragment.OnFragmentInteractionListener {

    private NotificationHandler notificationHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_you);

        final ViewPager views = (ViewPager)findViewById(R.id.vpDatosProfile1);

        // *** Instancia del los Widgets del menú
        LinearLayout buttonFeed = (LinearLayout) findViewById(R.id.llFeedButton);
        SwitchCompat playGame = (SwitchCompat) findViewById(R.id.scPlayGame);

        //*** Creacion del adaptador para el view de los parametros de la persona
        PagerAdapter adapter = new ViewProfilesDatesAdapter(getSupportFragmentManager(), 2);


        notificationHandler = new NotificationHandler(ProfileYouActivity.this);

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

        views.setAdapter(adapter);

        // **** Botón Menu Redondo
        installButton110to250();


    }


    // Funcion de creacion del Botòn Menu Redondo
    private void installButton110to250() {
        final AllAngleExpandableButton button = (AllAngleExpandableButton) findViewById(R.id.button_expandable_110_250);
        final List<ButtonData> buttonDatas = new ArrayList<>();
        int[] drawable = {R.drawable.plus,R.drawable.refresh, R.drawable.mark, R.drawable.settings,R.drawable.plus};
        int[] color = {R.color.colorPrimary, R.color.color_gray, R.color.color_gray, R.color.color_gray, R.color.color_gray};

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
                        sendNotification("Hello");
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

    private void sendNotification(String message){
        String title = "Like";

        if(!TextUtils.isEmpty(title) && !TextUtils.isEmpty(message)){
            boolean highNotification = true;
            Notification.Builder nb = notificationHandler.createNotification(title,message, highNotification);
            notificationHandler.getManager().notify(1,nb.build());
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
