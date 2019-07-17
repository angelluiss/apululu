package com.example.apululu.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.apululu.R;
import com.squareup.picasso.Picasso;

import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileLikeDislikeActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_like_dislike);

        CircleImageView imageProfile = (CircleImageView) findViewById(R.id.profile_image);
        CircleImageView like = (CircleImageView) findViewById(R.id.civLike);

        Picasso.get().load(R.drawable.imagen_perfil).into(imageProfile);

        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }
}
