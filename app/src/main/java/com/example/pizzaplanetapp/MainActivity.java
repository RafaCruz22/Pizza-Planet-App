package com.example.pizzaplanetapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;

public class MainActivity extends AppCompatActivity {

    public ImageView blastOff;
    VideoView videov;
    MediaController mediaC;

    DatabaseReference database;

    public static RelativeLayout videoLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        videoLayout = findViewById(R.id.rLayout);

        int gridColumnCount = getResources().getInteger(R.integer.grid_column_count);

        playVideo();

        blastOff = (ImageView) findViewById(R.id.blastOff);
        blastOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Menu.class));
            }

        });

        changeVideo();

    }

    public void playVideo() {
        videov = (VideoView) findViewById(R.id.videoView);
        mediaC = new MediaController(this);
        //videov.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.pizzaplanetintro);
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

    ///Setting up menu_main
    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void changeVideo() {

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        String radio_video = sharedPref.getString("videos", "short video");
        if (radio_video.equals("short video")) {
            videov.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.pizzaplanetintro);
        }
        if (radio_video.equals("medium video")) {
            videov.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.pizzaplanetmediumvideo);
        }
        if (radio_video.equals("long video")) {
            videov.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.toystorypizzaplanet);
        }

    }

}