package com.example.apululu.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.apululu.R;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileLikeDislikeActivity extends AppCompatActivity {

    CircleImageView imageProfile = (CircleImageView) findViewById(R.id.profile_image);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_like_dislike);

        Picasso.get().load(R.drawable.imagen_perfil).into(imageProfile);
    }
}
