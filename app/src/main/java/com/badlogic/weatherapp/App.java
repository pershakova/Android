package com.badlogic.weatherapp;

import android.app.Application;

import androidx.room.Room;

import com.badlogic.weatherapp.dao.CityDao;
import com.badlogic.weatherapp.database.CityDatabase;

public class App extends Application {

    private static App instance;

    private CityDatabase db;

    public static App getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;

        db = Room.databaseBuilder(
                getApplicationContext(),
                CityDatabase.class,
                "city_database")
                .build();
    }

    public CityDao getCityDao() {
        return db.getCityDao();
    }
}

