package com.example.android.quakereport;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.graphics.drawable.GradientDrawable;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class EarthquakeAdapter extends ArrayAdapter<Earthquake> {
    public EarthquakeAdapter(Context context, List<Earthquake> earthquakeList) {
        super(context, 0, earthquakeList);
    }

    @Nullable
    @Override
    public Earthquake getItem(int position) {
        return super.getItem(position);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View v = null;
        if (convertView == null) {
            v = LayoutInflater.from(getContext()).inflate(R.layout.earthquake_entry_item, parent, false);
        }

        else {
            v = convertView;
        }

        Earthquake eq = getItem(position);
        DecimalFormat decimalFormatter= new DecimalFormat("0.0");
        TextView magText = (TextView) v.findViewById(R.id.mag_text);
        TextView locationOffsetText = (TextView) v.findViewById(R.id.location_offset);
        TextView locationText = (TextView) v.findViewById(R.id.location_text);
        TextView dateText = (TextView) v.findViewById(R.id.date_text);
        TextView timeText = (TextView) v.findViewById(R.id.time_text);

        magText.setText(decimalFormatter.format(new Float(eq.getMagnitude())));
        String rawLocation = eq.getLocation();

        if (rawLocation.contains(" of ")) {
            String[] locationSplit = rawLocation.split(" of ");
            locationOffsetText.setText(locationSplit[0]);
            locationText.setText(locationSplit[1]);
        }

        else {
            locationOffsetText.setText(getContext().getString(R.string.near_the));
            locationText.setText(rawLocation);
        }

        dateText.setText(eq.getDate());
        timeText.setText(eq.getTime());

        GradientDrawable gd = (GradientDrawable) magText.getBackground();
        gd.setColor(getMagnitudeColor(new Float(eq.getMagnitude())));

        return v;
    }

    private int getMagnitudeColor(Float aFloat) {
        int color = 0;
        if (aFloat >= 1.0 && aFloat <= 1.99) {
            color = ContextCompat.getColor(getContext(), R.color.magnitude1);
        }

        else if (aFloat >= 2.0 && aFloat <= 2.99) {
            color = ContextCompat.getColor(getContext(), R.color.magnitude2);
        }

        else if (aFloat >= 3.0 && aFloat <= 3.99) {
            color = ContextCompat.getColor(getContext(), R.color.magnitude3);
        }

        else if (aFloat >= 4.0 && aFloat <= 4.99) {
            color = ContextCompat.getColor(getContext(), R.color.magnitude4);
        }

        else if (aFloat >= 5.0 && aFloat <= 5.99) {
            color = ContextCompat.getColor(getContext(), R.color.magnitude5);
        }

        else if (aFloat >= 6.0 && aFloat <= 6.99) {
            color = ContextCompat.getColor(getContext(), R.color.magnitude6);
        }

        else if (aFloat >= 7.0 && aFloat <= 7.99) {
            color = ContextCompat.getColor(getContext(), R.color.magnitude7);
        }

        else if (aFloat >= 8.0 && aFloat <= 8.99) {
            color = ContextCompat.getColor(getContext(), R.color.magnitude8);
        }

        else if (aFloat >= 9.0 && aFloat <= 9.99) {
            color = ContextCompat.getColor(getContext(), R.color.magnitude9);
        }

        else if (aFloat >= 10.0) {
            color = ContextCompat.getColor(getContext(), R.color.magnitude10plus);

        }
        return color;
    }


}
