// Defines the POJO that maps to the json returned from the MovieKey api call.

package com.arnold.mas.theatreorchill.model;

import java.util.List;

public class MovieKeyResults {
    private int id;
    private List<MovieKey> results;

    public MovieKeyResults() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<MovieKey> getResults() {
        return results;
    }

    public void setResults(List<MovieKey> results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "MovieKeyResults{" +
                "id=" + id +
                ", results=" + results +
                '}';
    }
}
