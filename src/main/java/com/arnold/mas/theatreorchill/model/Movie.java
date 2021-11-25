// Defines the Movie object type to be mapped from the json returned by moviedb api.

package com.arnold.mas.theatreorchill.model;

import com.google.gson.annotations.SerializedName;
import org.springframework.lang.Nullable;

import java.io.Serializable;

public class Movie implements Serializable {
    private int id;
    private String title;
    private String overview;
    @SerializedName(value="posterPath", alternate = "poster_path")
    private String posterPath;
    @SerializedName(value="releaseDate", alternate = "release_date")
    private String releaseDate;
    @SerializedName(value = "voteAverage", alternate = "vote_average")
    private float voteAverage;
    private String key;

    public Movie() {
    }

    public Movie(int id, String title, String overview, String posterPath, String releaseDate,
                 float voteAverage, @Nullable String key) {
        this.id = id;
        this.title = title;
        this.overview = overview;
        this.posterPath = posterPath;
        this.releaseDate = releaseDate;
        this.voteAverage = voteAverage;
        this.key = key;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public float getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(float voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String videoKey) {
        this.key = videoKey;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", overview='" + overview + '\'' +
                ", posterPath='" + posterPath + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                ", voteAverage=" + voteAverage +
                ", key='" + key + '\'' +
                '}';
    }
}
