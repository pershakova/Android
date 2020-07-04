package com.badlogic.weatherapp;

public class PictureData {
    private String description;
    private int picture;

    public PictureData(String description, int picture){
        this.description=description;
        this.picture=picture;
    }

    public String getDescription(){
        return description;
    }

    public int getPicture(){
        return picture;
    }
}
