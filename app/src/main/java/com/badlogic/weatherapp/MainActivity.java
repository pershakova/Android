package com.badlogic.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "Info";
    private final String temperatureKey = "temperature";
    private TextView temperatureTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log("onCreate()");
        temperatureTextView = findViewById(R.id.textTemperature);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log("onStart()");
    }

    @Override
    protected void onRestoreInstanceState(Bundle saveInstanceState){
        Log("onRestoreInstanceState()");
        restoreData(saveInstanceState);
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
        saveData(saveInstanceState);
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

    private void saveData(Bundle saveInstanceState){
        saveInstanceState.putString(temperatureKey, temperatureTextView.getText().toString());
    }

    private void restoreData(Bundle saveInstanceState){
        temperatureTextView.setText(saveInstanceState.getString(temperatureKey));
    }
}
