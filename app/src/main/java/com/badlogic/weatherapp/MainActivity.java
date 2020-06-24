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
    private final String temperatureKey = "temperature";
    private TextView temperatureTextView;
    private TextView cityTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log("onCreate()");

        temperatureTextView = findViewById(R.id.textTemperature);
        cityTextView = findViewById(R.id.textCountry);

        startCityActivity();
        getDataFromIntent();
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

    private void startCityActivity(){
        cityTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CityActivity.class);
                startActivity(intent);
            }
        });
    }

    private void getDataFromIntent(){
        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            return;
        }

        String text = bundle.getString(CITY);
        if (!text.isEmpty()){
            cityTextView.setText(text);
        }
    }
}
