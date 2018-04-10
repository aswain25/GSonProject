package com.example.admin.gsonproject;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

public class ProfileActivity extends AppCompatActivity  implements Handler.Callback {

    final static String MY_PROFILE_URL = "https://api.github.com/users/";
    GithubProfile model;
    ImageView ivAvatar;
    TextView tvName;
    TextView tvCreated;
    TextView tvUpdated;
    String profileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ivAvatar = findViewById(R.id.ivAvatar);
        tvName = findViewById(R.id.tvName);
        tvCreated = findViewById(R.id.tvCreated);
        tvUpdated = findViewById(R.id.tvUpdated);
        profileName = getIntent().getStringExtra("profileName");

        HandlerUtils.getDefult().setReceiver(new Handler(this));
        new NativeClient(MY_PROFILE_URL + profileName).start();
    }

    @Override
    public boolean handleMessage(Message msg) {
        Gson gson = new Gson();
        model = gson.fromJson(msg.getData().getString("results"), GithubProfile.class);
        //Glide
        Glide.with(this).load(model.getAvatarUrl()).into(ivAvatar);

        tvName.setText(model.getLogin());
        tvCreated.setText(model.getCreatedAt());
        tvUpdated.setText(model.getUpdatedAt());
        tvName.setText(model.getLogin());
        return false;
    }

    public void btnRepos_Clicked(View view)
    {
        Intent intent = new Intent(getApplicationContext(), RepositoryActivity.class);
        intent.putExtra("profileName", profileName);
        startActivity(intent);
    }
}
