package com.badlogic.weatherapp.fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.badlogic.weatherapp.App;
import com.badlogic.weatherapp.PictureDataSource;
import com.badlogic.weatherapp.R;
import com.badlogic.weatherapp.SocnetAdapter;
import com.badlogic.weatherapp.dao.CityDao;
import com.badlogic.weatherapp.database.CityRecyclerAdapter;
import com.badlogic.weatherapp.database.CitySource;
import com.badlogic.weatherapp.database.RandomCity;
import com.badlogic.weatherapp.interfaces.OpenWeather;
import com.badlogic.weatherapp.models.City;
import com.badlogic.weatherapp.models.WeatherRequest;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.functions.FirebaseFunctions;
import com.google.firebase.functions.FirebaseFunctionsException;
import com.google.firebase.functions.HttpsCallableResult;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.LOCATION_SERVICE;
import static com.badlogic.weatherapp.Constants.CITY;

public class WeatherFragment extends Fragment {
    public static final String APP_PREFERENCES = "WEATHERAPP";

    private static final float AbsoluteZero = -273.15f;

    private OpenWeather openWeather;

    private final String TAG = "Info";
    private final String ERRORTAG = "Error";

    private TextView temperatureTextView;
    private TextView cityTextView;
    private RecyclerView recyclerView;
    private View view;

    private CityRecyclerAdapter adapter;
    private CitySource citySource;

    private SharedPreferences mSettings;
    private final String cityKeyPreferences = "City";

    private static final int PERMISSION_REQUEST_CODE = 10;

    private FirebaseFunctions mFunctions;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.first_screen_fragment, container, false);

        temperatureTextView = view.findViewById(R.id.textTemperature);
        cityTextView = view.findViewById(R.id.textCountry);

        mSettings = getActivity().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);

        initCityFragment();
        loadCity();

       // PictureDataSource sourceData = new PictureDataSource(getResources());
       // initRecyclerView(sourceData.init());
        //initRecyclerView();
        //initDecorator();

        initRetorfit();
        initEvents();
       // loadWeatherPicture();

        initRequestWeatherByCoordinates();
        mFunctions = FirebaseFunctions.getInstance();
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
        saveCity();
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

    private void initRecyclerView() {
        recyclerView = view.findViewById(R.id.recycler_list);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        CityDao cityDao = App
                .getInstance()
                .getCityDao();

        citySource = new CitySource(cityDao);

        adapter = new CityRecyclerAdapter(citySource, (Activity)getContext());
        recyclerView.setAdapter(adapter);

        City city = new RandomCity(getResources())
                .rndCity();

       citySource.addCity(city);
        adapter.notifyDataSetChanged();
    }

    private void saveCity(){
        SharedPreferences.Editor editor = mSettings.edit();
        editor.putString(cityKeyPreferences, cityTextView.getText().toString());
        editor.apply();
    }

    private void loadCity(){
        if(mSettings.contains(cityKeyPreferences)) {
            cityTextView.setText(mSettings.getString(cityKeyPreferences, ""));
        }
    }

    private void initRequestWeatherByCoordinates() {
        Button button = view.findViewById(R.id.buttonWeatherByCoordinates);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestPermissions();
            }
        });
    }

    private void requestPermissions() {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            requestLocation();
        } else {
            requestLocationPermissions();
        }
    }

    private void requestLocation() {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            return;
        }

        LocationManager locationManager = (LocationManager)getActivity().getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_COARSE);

        String provider = locationManager.getBestProvider(criteria, true);
        if (provider != null) {
            locationManager.requestLocationUpdates(provider, 10000, 10, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    LatLng coordinates = new LatLng(location.getLatitude(), location.getLongitude());
                    requestWeatherByCoordinates(coordinates, getString(R.string.Key));
                    setCityByCoordinates(coordinates);
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {
                }

                @Override
                public void onProviderEnabled(String provider) {
                }

                @Override
                public void onProviderDisabled(String provider) {
                }
            });
        }
    }

    private void requestLocationPermissions() {
        if (!ActivityCompat.shouldShowRequestPermissionRationale((Activity) getContext(), Manifest.permission.CALL_PHONE)) {
            ActivityCompat.requestPermissions((Activity) getContext(),
                    new String[]{
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION
                    },
                    PERMISSION_REQUEST_CODE);
        }
    }

    private void requestWeatherByCoordinates(LatLng location, String keyApi) {
        openWeather.loadWeatherByCoordinates(location.latitude, location.latitude, keyApi)
                .enqueue(new Callback<WeatherRequest>() {
                    @Override
                    public void onResponse(Call<WeatherRequest> call, Response<WeatherRequest> response) {
                        if (response.body() != null) {
                            float result = response.body().getMain().getTemp() + AbsoluteZero;
                            temperatureTextView.setText(String.format("%d",(long)result)+"°C");
                            addPushNotification(result);
                        }
                    }

                    @Override
                    public void onFailure(Call<WeatherRequest> call, Throwable t) {
                        temperatureTextView.setText("Error");
                    }
                });
    }

    private void setCityByCoordinates(final LatLng location){
        final Geocoder geocoder = new Geocoder(getContext());

        final TextView cityTextView = view.findViewById(R.id.textCountry);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final List<Address> addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1);

                    cityTextView.post(new Runnable() {
                        @Override
                        public void run() {
                            cityTextView.setText(addresses.get(0).getLocality() +", " + addresses.get(0).getCountryCode());
                        }
                    });

                } catch (IOException e) {
                    Log.d(ERRORTAG, e.getMessage());
                    Toast.makeText(getContext(), "Can not get location", Toast.LENGTH_SHORT).show();
                }
            }
        }).start();
    }

    private void addPushNotification(float temperature){
        if (temperature<0){
            addMessage("Extremely cold today, stay at home please").addOnCompleteListener(new OnCompleteListener<String>() {
                @Override
                public void onComplete(@NonNull Task<String> task) {
                    if (!task.isSuccessful()) {
                        Exception e = task.getException();
                        if (e instanceof FirebaseFunctionsException) {
                            FirebaseFunctionsException ffe = (FirebaseFunctionsException) e;

                            Object details = ffe.getDetails();
                            if (details!=null){
                                Log.d(ERRORTAG, details.toString());
                            }

                        }
                        Log.d(ERRORTAG, e.getMessage());
                    }
                }
            });;
        }
    }

    private Task<String> addMessage(String text) {
        Map<String, Object> data = new HashMap<>();
        data.put("text", text);
        data.put("push", true);

        return mFunctions
                .getHttpsCallable("addMessage")
                .call(data)
                .continueWith(new Continuation<HttpsCallableResult, String>() {
                    @Override
                    public String then(@NonNull Task<HttpsCallableResult> task) throws Exception {
                        String result = (String) task.getResult().getData();
                        return result;
                    }
                });
    }
}
