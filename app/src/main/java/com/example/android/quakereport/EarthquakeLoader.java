package com.example.android.quakereport;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import java.util.List;

import static com.example.android.quakereport.EarthquakeActivity.LOG_TAG;

public class EarthquakeLoader extends AsyncTaskLoader<List<Earthquake>> {
    private String mURL = "";
    public EarthquakeLoader(Context context, String url) {
        super(context);
        mURL = url;
    }

    @Override
    public List<Earthquake> loadInBackground() {
        Log.d(LOG_TAG, "Invoking background downloader!!");
        return DownloadUtility.fetchEarthquakeData(mURL);
    }
}
