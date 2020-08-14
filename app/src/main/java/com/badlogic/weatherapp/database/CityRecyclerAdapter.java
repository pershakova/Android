package com.badlogic.weatherapp.database;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.badlogic.weatherapp.R;
import com.badlogic.weatherapp.models.City;

import java.util.List;

public class CityRecyclerAdapter
        extends RecyclerView.Adapter<CityRecyclerAdapter.ViewHolder> {

    private Activity activity;

    private CitySource dataSource;

    private long menuPosition;

    public CityRecyclerAdapter(CitySource dataSource, Activity activity){
        this.dataSource = dataSource;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_city, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        List<City> cities = dataSource.getCities();
        City city = cities.get(position);
        holder.city.setText(city.city);
        holder.temperature.setText( Integer.toString(city.temperature));

        holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                menuPosition = position;
                return false;
            }
        });

        if (activity != null){
            activity.registerForContextMenu(holder.cardView);
        }
    }

    @Override
    public int getItemCount() {
        return (int) dataSource.getCountStudents();
    }

    public long getMenuPosition() {
        return menuPosition;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView city;
        private TextView temperature;
        private View cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView;
            city = cardView.findViewById(R.id.textCity);
            temperature = cardView.findViewById(R.id.textTemperature);
        }
    }
}

