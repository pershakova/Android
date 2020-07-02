package com.badlogic.weatherapp;

import android.content.res.Resources;
import android.content.res.TypedArray;

import java.util.ArrayList;
import java.util.List;

public class PictureDataSource {
    private List<PictureData> dataSource;   // строим этот источник данных
    private Resources resources;    // ресурсы приложения

    public PictureDataSource(Resources resources) {
        dataSource = new ArrayList<>(6);
        this.resources = resources;
    }

    public PictureDataSource init(){
        String[] descriptions = resources.getStringArray(R.array.date);
        int[] pictures = getImageArray();

        for (int i = 0; i < descriptions.length; i++) {
            dataSource.add(new PictureData(descriptions[i], pictures[i]));
        }
        return this;
    }

    public PictureData getSoc(int position) {
        return dataSource.get(position);
    }

    public int size(){
        return dataSource.size();
    }

    private int[] getImageArray(){
        TypedArray pictures = resources.obtainTypedArray(R.array.pictures);
        int length = pictures.length();
        int[] answer = new int[length];
        for(int i = 0; i < length; i++){
            answer[i] = pictures.getResourceId(i, 0);
        }
        return answer;
    }

}
