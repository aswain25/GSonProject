package com.example.admin.gsonproject;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.gson.Gson;

public class RepositoryActivity extends AppCompatActivity implements Handler.Callback {

    final static String MY_PROFILE_URL = "https://api.github.com/users/";
    Repository[] model;
    RecyclerView rvRepos;
    String profileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repository);

        rvRepos = findViewById(R.id.rvRepos);

        profileName = getIntent().getStringExtra("profileName");

        HandlerUtils.getDefult().setReceiver(new Handler(this));
        new NativeClient(MY_PROFILE_URL + profileName + "/repos").start();
    }

    @Override
    public boolean handleMessage(Message msg) {
        Gson gson = new Gson();
        model = gson.fromJson(msg.getData().getString("results"), Repository[].class);

        String[] repoNames = new String[model.length];

        for (int i = 0; i < repoNames.length; i++)
            repoNames[i] = model[i].getName();

        rvRepos.setHasFixedSize(false);
        rvRepos.setLayoutManager(new LinearLayoutManager(this));
        rvRepos.setAdapter(new MyAdapter(repoNames));

        return false;
    }

    public void btnRepos_Clicked(View view)
    {
        Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
        intent.putExtra("profileName", profileName);
        startActivity(intent);
    }
}
