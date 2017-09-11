package com.codepath.flickster.models;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

/**
 * Created by dphan on 9/10/17.
 */

@Parcel
public class Video {

    private String key;
    private String site;

    public Video() {

    }

    public Video(JSONObject jsonObject) throws JSONException {
        key = jsonObject.getString("key");
        site = jsonObject.getString("site");
    }

    public String getKey() {
        return key;
    }

    public String getSite() {
        return site;
    }
}
