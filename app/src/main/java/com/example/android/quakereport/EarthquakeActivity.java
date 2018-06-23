/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.quakereport;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import android.support.v4.app.LoaderManager;
public class EarthquakeActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Earthquake>> {
    public final String URL_LINK = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=6&limit=10";
    public static final String LOG_TAG = EarthquakeActivity.class.getName();
    private ListView earthquakeListView;
    private EarthquakeAdapter earthquakeAdapter;
    private TextView empty_text;
    private ProgressBar loading_spin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);

        earthquakeAdapter = new EarthquakeAdapter(this, new ArrayList<Earthquake>());

        // Find a reference to the {@link ListView} in the layout
        earthquakeListView = (ListView) findViewById(R.id.list);
        earthquakeListView.setAdapter(earthquakeAdapter);

        earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Earthquake e = earthquakeAdapter.getItem(position);
                Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                browserIntent.setData(Uri.parse(e.getUrl()));

                Intent chooser = browserIntent.createChooser(browserIntent, "Open URL with..");
                if (browserIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(chooser);
                }
                //Toast.makeText(EarthquakeActivity.this, new Integer(position).toString(), Toast.LENGTH_SHORT).show();
            }
        });

        loading_spin = (ProgressBar) findViewById(R.id.loading_spin);
        empty_text = (TextView) findViewById(R.id.empty_text);
        earthquakeListView.setEmptyView(empty_text);

        ConnectivityManager cm = (ConnectivityManager)getBaseContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

        if (isConnected) {
            loading_spin.setVisibility(View.VISIBLE);
            getSupportLoaderManager().initLoader(0, null, this).forceLoad();
        } else
            empty_text.setText("No internet connection!");
    }

    public void updateUi(final List<Earthquake> earthquakes) {
        earthquakeAdapter.clear();

        if (earthquakes != null && !earthquakes.isEmpty())
            earthquakeAdapter.addAll(earthquakes);
    }

    @Override
    public android.support.v4.content.Loader<List<Earthquake>> onCreateLoader(int id, Bundle args) {
        return new EarthquakeLoader(this, URL_LINK);
    }

    @Override
    public void onLoadFinished(android.support.v4.content.Loader<List<Earthquake>> loader, List<Earthquake> data) {
        loading_spin.setVisibility(View.GONE);
        empty_text.setText("No earthquake found!");
        earthquakeAdapter.clear();

        if (data != null && !data.isEmpty()) {
            earthquakeAdapter.addAll(data);
            //earthquakeAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onLoaderReset(android.support.v4.content.Loader<List<Earthquake>> loader) {
        earthquakeAdapter.clear();
    }


    private class DownloadAsyncTask extends AsyncTask<URL, Void, List<Earthquake>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected List<Earthquake> doInBackground(URL... urls) {
            if (urls.length < 1 || urls[0] == null)
                return null;

            return DownloadUtility.fetchEarthquakeData(urls[0].toString());
        }

        @Override
        protected void onPostExecute(List<Earthquake> earthquakes) {
            super.onPostExecute(earthquakes);

            if (earthquakes == null)
                return;

            updateUi(earthquakes);
        }
    }
}
