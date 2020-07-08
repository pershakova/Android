package com.badlogic.weatherapp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.snackbar.Snackbar;
import java.util.Objects;

import static com.badlogic.weatherapp.Constants.CITY;

public class WeatherFragment extends Fragment {
    private final String TAG = "Info";
    private final String temperatureKey = "temperature";
    private TextView temperatureTextView;
    private TextView cityTextView;
    private RecyclerView recyclerView;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.first_screen_fragment, container, false);

        temperatureTextView = view.findViewById(R.id.textTemperature);
        cityTextView = view.findViewById(R.id.textCountry);

        initCityFragment();

        PictureDataSource sourceData = new PictureDataSource(getResources());
        initRecyclerView(sourceData.init());
        initDecorator();
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle bundle = this.getArguments();

        if(bundle != null){
            cityTextView.setText(bundle.getString(CITY));
        }
    }

    @Override
    public void onSaveInstanceState(Bundle saveInstanceState){
        Log("onSaveInstanceState()");
        super.onSaveInstanceState(saveInstanceState);
    }

    private void Log(String message){
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onRestart()");
    }

    private void initCityFragment(){
        cityTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new CityFragment();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment);
                fragmentTransaction.commit();
            }
        });
    }

    private void initRecyclerView(PictureDataSource sourceData){
        recyclerView = view.findViewById(R.id.recycler_view);

        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        SocnetAdapter adapter = new SocnetAdapter(sourceData);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new SocnetAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Snackbar.make(view, String.format("Позиция - %d", position), Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    private void initDecorator(){
        DividerItemDecoration itemDecoration = new DividerItemDecoration(Objects.requireNonNull(getContext()),  LinearLayoutManager.VERTICAL);
        itemDecoration.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.separator));
        recyclerView.addItemDecoration(itemDecoration);
    }
}
