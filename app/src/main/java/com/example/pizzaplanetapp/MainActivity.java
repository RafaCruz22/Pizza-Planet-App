package com.example.pizzaplanetapp;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {

    VideoView videov;
    MediaController mediaC;
    public Button pizzaMenu;
    public ImageView blastOff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playVideo();

        blastOff = (ImageView) findViewById(R.id.blastOff);
        blastOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, PizzaMenu.class));
            }

        });


/*        pizzaMenu = (Button) findViewById(R.id.pizzaMenuButton);
        pizzaMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, PizzaMenu.class));
            }

        });*/


    }

    public void playVideo() {
        videov = (VideoView) findViewById(R.id.videoView);
        mediaC = new MediaController(this);
        videov.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.toystorypizzaplanet);
        mediaC.setAnchorView(videov);
        videov.setMediaController(mediaC);
        videov.start();
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