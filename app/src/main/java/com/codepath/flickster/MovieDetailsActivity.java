package com.codepath.flickster;

import android.os.Bundle;
import android.util.Log;
import android.widget.RatingBar;
import android.widget.TextView;

import com.codepath.flickster.models.Movie;
import com.codepath.flickster.models.Video;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

import static com.codepath.flickster.BuildConfig.API_KEY;
import static com.codepath.flickster.MovieActivity.API_BASE_URL;
import static com.codepath.flickster.MovieActivity.API_KEY_PARAM;

/**
 * Created by dphan on 9/10/17.
 */

public class MovieDetailsActivity extends YouTubeBaseActivity {

    private final String TAG = "MovieDetailsActivity";
    public static final String MOVIE_ID_KEY = "MOVIE_ID";
    private final static String YOUTUBE_API_KEY = BuildConfig.YOUTUBE_API_KEY;

    private Movie movie;
    private String id;
    private AsyncHttpClient client;

    @BindView(R.id.tvTitle) TextView tvTitle;
    @BindView(R.id.tvOverview) TextView tvOverview;
    @BindView(R.id.tvReleaseDate) TextView tvReleaseDate;
    @BindView(R.id.rbAvgVote) RatingBar rbAvgVote;
    @BindView(R.id.player) YouTubePlayerView youTubePlayerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        ButterKnife.bind(this);
        client = new AsyncHttpClient();

        movie = Parcels.unwrap(getIntent().getParcelableExtra(Movie.class.getSimpleName()));
        if(movie.getReleaseDate() != null) {
            tvReleaseDate.setText(movie.getReleaseDate());
        }

        tvTitle.setText(movie.getOriginalTitle());
        tvOverview.setText(movie.getOverview());

        float avgVote = movie.getAvgVote().floatValue();
        if(avgVote > 0) {
            avgVote = avgVote / 2.0f;
        }

        rbAvgVote.setRating(avgVote);
        fetchVideos();
    }

    private void fetchVideos() {
        String url = API_BASE_URL + "/movie/" + movie.getId() + "/videos";

        RequestParams requestParams = new RequestParams();
        requestParams.put(API_KEY_PARAM, API_KEY);
        requestParams.put(MOVIE_ID_KEY, movie.getId());

        client.get(url, requestParams, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                JSONArray jsonArray;

                try {
                    jsonArray = response.getJSONArray("results");

                    for(int i = 0; i < jsonArray.length(); i++) {
                        Video video = new Video(jsonArray.getJSONObject(i));
                        if(video.getSite().equals("YouTube")) {
                            id = video.getKey();
                            showVideo();
                            break;
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String response, Throwable throwable) {
                super.onFailure(statusCode, headers, response, throwable);
            }
        });

    }

    private void showVideo() {
        youTubePlayerView.initialize(YOUTUBE_API_KEY, new YouTubePlayer.OnInitializedListener() {

            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.cueVideo(id);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Log.e(TAG, "Error initializing YouTube player.");
            }
        });
    }
}
