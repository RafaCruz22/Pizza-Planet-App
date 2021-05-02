package com.example.pizzaplanetapp;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    VideoView videov;
    MediaController mediaC;
    public ImageView blastOff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int gridColumnCount = getResources().getInteger(R.integer.grid_column_count);

        playVideo();

        blastOff = (ImageView) findViewById(R.id.blastOff);
        blastOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Menu.class));
            }

        });

    }

    public void playVideo() {
        videov = (VideoView) findViewById(R.id.videoView);
        mediaC = new MediaController(this);
        videov.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.pizzaplanetintro);
        mediaC.setAnchorView(videov);
        videov.setMediaController(mediaC);
        videov.start();
    }

    @Override
    protected void onStop(){
        super.onStop();
        videov.stopPlayback();
        videov.resume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        videov.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        videov.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        videov.suspend();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        videov.stopPlayback();
    }


}