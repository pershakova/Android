package com.badlogic.weatherapp.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.badlogic.weatherapp.models.City;

import java.util.List;
@Dao
public interface CityDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCity(City city);

    @Update
    void updateCity(City city);

    @Delete
    void deleteCity(City city);

    @Query("DELETE FROM City WHERE id = :id")
    void deleteCityById(long id);

    @Query("SELECT * FROM City")
    List<City> getAllCities();

    @Query("SELECT * FROM City WHERE id = :id")
    City getCityById(long id);

    @Query("SELECT COUNT() FROM City")
    long getCountCities();
}
