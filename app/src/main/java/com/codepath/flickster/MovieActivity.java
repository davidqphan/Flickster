package com.codepath.flickster;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.codepath.flickster.adapters.MovieAdapter;
import com.codepath.flickster.models.Config;
import com.codepath.flickster.models.Movie;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

public class MovieActivity extends AppCompatActivity {

    public final static String API_BASE_URL = "https://api.themoviedb.org/3";
    public final static String API_KEY_PARAM = "api_key";
    public final static String TAG = "MovieActivity";

    AsyncHttpClient client;
    ArrayList<Movie> movies;
    @BindView(R.id.rvMovies)
    RecyclerView rvItems;
    MovieAdapter movieAdapter;
    Config config;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        ButterKnife.bind(this);
        client = new AsyncHttpClient();

        attachAdapterToRecyclerView();

        getConfiguration();
    }

    private void attachAdapterToRecyclerView() {
        movies = new ArrayList<>();
        movieAdapter = new MovieAdapter(movies);
        rvItems.setLayoutManager(new LinearLayoutManager(this));
        rvItems.setAdapter(movieAdapter);
    }

    private void getConfiguration() {
        String url = API_BASE_URL + "/configuration";
        RequestParams params = getApiParams();

        client.get(url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    config = new Config(response);
                    movieAdapter.setConfig(config);
                    getNowPlaying();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });
    }

    private void getNowPlaying() {
        String url = API_BASE_URL + "/movie/now_playing";
        RequestParams params = getApiParams();

        client.get(url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                JSONArray movieJsonResults = null;
                try {
                    movieJsonResults = response.getJSONArray("results");
                    movies.addAll(Movie.fromJSONArray(movieJsonResults));
                    movieAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });
    }

    private RequestParams getApiParams(){
        RequestParams params = new RequestParams();
        params.put(API_KEY_PARAM, getString(R.string.moviedb_api_key));
        return params;
    }
}