package com.badlogic.weatherapp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.badlogic.weatherapp.interfaces.OpenWeather;
import com.badlogic.weatherapp.models.WeatherRequest;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.badlogic.weatherapp.Constants.CITY;

public class WeatherFragment extends Fragment {
    private static final float AbsoluteZero = -273.15f;
    private OpenWeather openWeather;

    private final String TAG = "Info";

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

        initRetorfit();
        initEvents();
        loadWeatherPicture();
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

    private void initEvents() {
        Button button = view.findViewById(R.id.refresh);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestRetrofit(cityTextView.getText().toString(), getString(R.string.Key));
            }
        });
    }

    private void initRetorfit() {
        Retrofit retrofit;
        retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.URL))
                .addConverterFactory(GsonConverterFactory.create()).build();
        openWeather = retrofit.create(OpenWeather.class);
    }

    private void requestRetrofit(String city, String keyApi) {
        openWeather.loadWeather(city, keyApi)
                .enqueue(new Callback<WeatherRequest>() {
                    @Override
                    public void onResponse(Call<WeatherRequest> call, Response<WeatherRequest> response) {
                        if (response.body() != null) {
                            float result = response.body().getMain().getTemp() + AbsoluteZero;
                            temperatureTextView.setText(Float.toString(result));
                        }
                    }

                    @Override
                    public void onFailure(Call<WeatherRequest> call, Throwable t) {
                        temperatureTextView.setText("Error");
                    }
                });
    }

    private void loadWeatherPicture(){
        ImageView imageView = view.findViewById(R.id.imageDescriptionPicture);
        Picasso.get()
                .load("https://c1.staticflickr.com/1/186/31520440226_175445c41a_b.jpg")
                .into(imageView);
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
