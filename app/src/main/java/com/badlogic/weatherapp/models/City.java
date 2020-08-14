package com.badlogic.weatherapp.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(indices = {@Index(value = {"city"})})
public class City {
    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo(name = "city")
    public String city;

    @ColumnInfo(name = "temperature")
    public int temperature;
}
