package com.arnold.mas.theaterorchill.controller;

import com.arnold.mas.theaterorchill.model.MovieNews;
import com.arnold.mas.theaterorchill.config.AppProperties;
import com.arnold.mas.theaterorchill.model.MovieNewsResults;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("movie-news")
public class MovieNewsController {
    private final RestTemplate restTemplate;
    private final HttpHeaders headers  = new HttpHeaders();
    private final HttpEntity<String> entity = new HttpEntity<>(headers);
    private final Gson gson;
    private final AppProperties appProperties;

    @Autowired
    public MovieNewsController(RestTemplate restTemplate, Gson gson, AppProperties appProperties) {
        this.restTemplate = restTemplate;
        this.gson = gson;
        this.headers.set("User-Agent", "theatre-or-chill");
        this.appProperties = appProperties;
    }

    @GetMapping("/latest")
    public ResponseEntity<List<MovieNews>> getMovieNewsArticles() {
        String newsUrl = "https://newsapi.org/v2/top-headlines";
        return getListResponseEntity(newsUrl);
    }

    private ResponseEntity<List<MovieNews>> getListResponseEntity(String url) {
        UriComponents builder = getUriBuilder(url);
        ResponseEntity<String> responseEntity = restTemplate.exchange(builder.toUriString(),
                HttpMethod.GET, entity, String.class);
        MovieNewsResults newsResults = gson.fromJson(responseEntity.getBody(), MovieNewsResults.class);
        List<MovieNews> articles = newsResults.getArticles();
        return new ResponseEntity<>(articles, HttpStatus.OK);
    }

    UriComponents getUriBuilder(String url) {
        String country = "us";
        String category = "entertainment";
        return UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("country", country)
                .queryParam("category", category)
                .queryParam("apiKey", appProperties.getApiKey("newsApiKey")).build();
    }
}
