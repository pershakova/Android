package com.badlogic.weatherapp.database;

import com.badlogic.weatherapp.dao.CityDao;
import com.badlogic.weatherapp.models.City;

import java.util.List;

public class CitySource {
    private final CityDao cityDao;

    private List<City> cities;

    public CitySource(CityDao cityDao){
        this.cityDao = cityDao;
    }

    public List<City> getCities(){
        if (cities == null){
            LoadCities();
        }
        return cities;
    }

    public void LoadCities(){
        cities = cityDao.getAllCities();
    }

    public long getCountStudents(){
        return cityDao.getCountCities();
    }

    public void addCity(City city){
        cityDao.insertCity(city);
        LoadCities();
    }

    public void updateCity(City city){
        cityDao.updateCity(city);
        LoadCities();
    }

    public void removeCity(long id){
        cityDao.deleteCityById(id);
        LoadCities();
    }
}
