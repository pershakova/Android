package com.badlogic.weatherapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.badlogic.weatherapp.Constants.CITY;

public class CityFragment extends Fragment {
    private Spinner spinner;
    private TextView city;
    private Button backButton;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.city_layout, container, false);

        spinner = view.findViewById(R.id.spinner);
        city = view.findViewById(R.id.city);
        backButton = view.findViewById(R.id.buttonBack);
        clickBackButton();

        view.findViewById(R.id.buttonService).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataService.startDataService(getContext(), new SimpleDateFormat("yyyy/MM/dd").format(new Date()));
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initSpinnerCity();
    }

    private void initSpinnerCity(){
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent,
                                       View itemSelected, int selectedItemPosition, long selectedId) {

                String[] choose = getResources().getStringArray(R.array.cities);
                Snackbar.make(view,
                        "You selected: " + choose[selectedItemPosition], Snackbar.LENGTH_SHORT).show();

                city.setText(choose[selectedItemPosition]);
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void clickBackButton(){
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString(CITY, city.getText().toString());

                Fragment fragment = new WeatherFragment();
                fragment.setArguments(bundle);
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment);
                fragmentTransaction.commit();
            }
        });
    }
}