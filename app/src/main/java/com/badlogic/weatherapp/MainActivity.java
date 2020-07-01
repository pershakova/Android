package com.badlogic.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import static com.badlogic.weatherapp.Constants.CITY;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "Info";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log("onCreate()");

        WeatherFragment details = new WeatherFragment();
        details.setArguments(getIntent().getExtras());
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, details).commit();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log("onStart()");
    }

    @Override
    protected void onRestoreInstanceState(Bundle saveInstanceState){
        Log("onRestoreInstanceState()");
        super.onRestoreInstanceState(saveInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log("onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log("onPause()");
    }

    @Override
    protected void onSaveInstanceState(Bundle saveInstanceState){
        Log("onSaveInstanceState()");
        super.onSaveInstanceState(saveInstanceState);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log("onStop()");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log("onRestart()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log("onDestroy()");
    }

    private void Log(String message){
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onRestart()");
    }

}
