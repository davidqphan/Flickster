package com.codepath.flickster.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;

/**
 * Created by dphan on 8/31/17.
 */

@Parcel
public class Movie {

    private String posterPath;
    private String backdropPath;
    private String originalTitle;
    private String overview;

    private Double avgVote;
    private Integer id;
    private String releaseDate;

    public String getPosterPath() {
        return posterPath;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getOverview() {
        return overview;
    }

    public Double getAvgVote() {
        return avgVote;
    }

    public Integer getId() {
        return id;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public Movie() {

    }

    public Movie(JSONObject jsonObject) throws JSONException {
        this.posterPath = jsonObject.getString("poster_path");
        this.backdropPath = jsonObject.getString("backdrop_path");
        this.originalTitle = jsonObject.getString("original_title");
        this.overview = jsonObject.getString("overview");
        this.avgVote = jsonObject.getDouble("vote_average");
        this.id = jsonObject.getInt("id");
        this.releaseDate = jsonObject.getString("release_date");
    }
}
