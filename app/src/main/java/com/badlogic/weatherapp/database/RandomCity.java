package com.badlogic.weatherapp.database;

import android.content.res.Resources;

import com.badlogic.weatherapp.R;
import com.badlogic.weatherapp.models.City;

import java.util.Random;

public class RandomCity {
    private Resources resources;
    private Random rnd = new Random();

    public RandomCity(Resources resources){
        this.resources = resources;
    }

    public City rndUpdateCity(City city){
        city.city = randomCity();
        city.temperature = randomTemperature();
        return city;
    }

    public City rndCity(){
        City city = new City();
        return rndUpdateCity(city);
    }

    private String randomCity(){
        String[] cities = resources.getStringArray(R.array.cities);
        return cities[rnd.nextInt(cities.length)];
    }

    private int randomTemperature(){
        String[] temperatures = resources.getStringArray(R.array.temperatures);
        return Integer.parseInt(temperatures[rnd.nextInt(temperatures.length)]);
    }
}
