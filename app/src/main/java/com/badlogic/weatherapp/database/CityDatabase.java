package com.badlogic.weatherapp.database;
import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.badlogic.weatherapp.dao.CityDao;
import com.badlogic.weatherapp.models.City;

@Database(entities = {City.class}, version = 1)
public abstract class CityDatabase extends RoomDatabase {
    public abstract CityDao getCityDao();
}
