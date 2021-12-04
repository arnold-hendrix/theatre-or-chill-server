// Defines the POJO that maps to the json returned from the api calls for now playing and upcoming movies.

package com.arnold.mas.theaterorchill.model;

import java.util.List;
import java.util.Map;

public class MovieSearchResults {
    private Map<String, String> dates;
    private int page;
    private List<Movie> results;
    private int total_pages;
    private int total_results;

    public MovieSearchResults() {
    }

    public Map<String, String> getDates() {
        return dates;
    }

    public void setDates(Map<String, String> dates) {
        this.dates = dates;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<Movie> getResults() {
        return results;
    }

    public void setResults(List<Movie> results) {
        this.results = results;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    public int getTotal_results() {
        return total_results;
    }

    public void setTotal_results(int total_results) {
        this.total_results = total_results;
    }

    @Override
    public String toString() {
        return "MovieSearchResults{" +
                "dates=" + dates +
                ", page=" + page +
                ", results=" + results +
                ", total_pages=" + total_pages +
                ", total_results=" + total_results +
                '}';
    }
}
