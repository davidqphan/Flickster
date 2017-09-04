package com.codepath.flickster.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by dphan on 9/3/17.
 */

public class Config {

    String baseUrl;
    String posterSize;
    String backdropSize;

    public Config(JSONObject jsonObject) throws JSONException {
        JSONObject images = jsonObject.getJSONObject("images");

        baseUrl = images.getString("secure_base_url");
        JSONArray posterSizes = images.getJSONArray("poster_sizes");
        posterSize = posterSizes.optString(3, "w342");

        JSONArray backdropSizes = images.getJSONArray("backdrop_sizes");
        backdropSize = backdropSizes.optString(1, "w780");
    }

    public String createImageUrl(String path, String size) {
        return String.format("%s%s%s", baseUrl, size, path);
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public String getPosterSize() {
        return posterSize;
    }

    public String getBackdropSize() {
        return backdropSize;
    }
}
