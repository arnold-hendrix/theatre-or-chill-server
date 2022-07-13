package com.arnold.mas.theaterorchill.model;

import java.util.List;

public class MovieNewsResults {
    private String status;
    private int totalResults;
    private List<MovieNews> articles;

    public MovieNewsResults() {

    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public List<MovieNews> getArticles() {
        return articles;
    }

    public void setArticles(List<MovieNews> articles) {
        this.articles = articles;
    }

    @Override
    public String toString() {
        return "MovieNewsResults{" +
                "status='" + status + '\'' +
                ", totalResults=" + totalResults +
                ", articles=" + articles +
                '}';
    }
}
