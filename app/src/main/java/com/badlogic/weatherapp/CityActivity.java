package com.badlogic.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import static com.badlogic.weatherapp.Constants.CITY;

public class CityActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.city_layout);

        selectCity();
        startMainActivity();
    }

    private void selectCity(){
        Spinner spinner = findViewById(R.id.spinner);
        final TextView city = findViewById(R.id.city);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent,
                                       View itemSelected, int selectedItemPosition, long selectedId) {

                String[] choose = getResources().getStringArray(R.array.cities);
                Toast toast = Toast.makeText(getApplicationContext(),
                        "You selected: " + choose[selectedItemPosition], Toast.LENGTH_SHORT);
                toast.show();

                city.setText(choose[selectedItemPosition]);
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void startMainActivity(){
        Button backButton = findViewById(R.id.buttonBack);
        final TextView city = findViewById(R.id.city);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CityActivity.this, MainActivity.class);
                intent.putExtra(CITY, city.getText().toString());
                startActivity(intent);
            }
        });
    }
}