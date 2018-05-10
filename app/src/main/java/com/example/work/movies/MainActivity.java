package com.example.work.movies;


import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    /* Resources:
     Udacity Android Developer NanoDegree Content
 */
    ArrayList<Movie> movieList;
    GridView gridView;
    Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        updateUI();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("movie", movieList);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.popular_sort) {
            if (NetworkUtils.isNetworkAvailable(getBaseContext())) {
                new GetMoviesTask().execute(NetworkUtils.buildUrl(Constants.SORT_BY_POPULARITY).toString());
            }
            return true;
        }

        if (id == R.id.top_rated_sort) {
            if (NetworkUtils.isNetworkAvailable(getBaseContext())) {
                new GetMoviesTask().execute(NetworkUtils.buildUrl(Constants.SORT_BY_RATING).toString());
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public class GetMoviesTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            URL url = null;
            String urlResponse = null;

            try {
                url = new URL(params[0]);

            } catch (MalformedURLException e) {
                e.printStackTrace();
                // Log.e(TAG,"EXCEPTION MESSAGE: ",e );
            }
            try {
                assert url != null;
                urlResponse = NetworkUtils.getResponseFromHttpUrl(url);
                // Log.i(TAG, "CONTENTS OF RESPONSE: " + response);

            } catch (IOException e) {
                // Log.e("ERROR: ", e.getMessage(), e);
                e.printStackTrace();
            }

            return urlResponse;

        }

        @Override
        protected void onPostExecute(String s) {
            // super.onPostExecute(s);
            if (s != null)
                try {
                    JSONObject obj = new JSONObject(s);
                    movieList = new ArrayList<>();

                    JSONArray jsonArray = obj.getJSONArray("results");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject objInJsonArray = jsonArray.optJSONObject(i);
                        String setPoster_path = objInJsonArray.getString("poster_path");
                        String setOriginal_title = objInJsonArray.getString("original_title");
                        String setRelease_date = objInJsonArray.getString("release_date");
                        Double setVote_average = objInJsonArray.getDouble("vote_average");
                        String setOverview = objInJsonArray.getString("overview");

                        Movie movieItem = new Movie(setPoster_path, setOriginal_title, setRelease_date, setVote_average, setOverview);
                        movieList.add(movieItem);

                        MovieAdapter movieAdapter = new MovieAdapter(MainActivity.this, movieList);
                        gridView.setAdapter(movieAdapter);
                    }

                    //Log.i(TAG, "MOVIE LIST SIZE:" + movieList.size());

                } catch (final JSONException e) {
                    e.printStackTrace();
                }
        }
    }

    private void updateUI() {

        if (NetworkUtils.isNetworkAvailable(this)) {
            gridView = findViewById(R.id.posterGrid);

            // display Popular Movies on app launch screen
            new GetMoviesTask().execute(NetworkUtils.buildUrl(Constants.SORT_BY_POPULARITY).toString());

            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    movie = movieList.get(position);
                    Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                    intent.putExtra("movieList", movie);
                    startActivity(intent);
                }
            });
        } else {
            Toast.makeText(this, getString(R.string.connectivity_message), Toast.LENGTH_SHORT).show();
        }
    }
}
