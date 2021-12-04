// Defines the MovieKey type representing the YouTube key for a movie trailer url.

package com.arnold.mas.theaterorchill.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MovieKey implements Serializable {
    @SerializedName(value="videoKey", alternate = "key")
    private String videoKey;

    public MovieKey() {
    }

    public MovieKey(String videoKey) {
        this.videoKey = videoKey;
    }

    public String getVideoKey() {
        return videoKey;
    }

    public void setVideoKey(String videoKey) {
        this.videoKey = videoKey;
    }

    @Override
    public String toString() {
        return "MovieKey{" +
                "videoKey='" + videoKey + '\'' +
                '}';
    }
}
