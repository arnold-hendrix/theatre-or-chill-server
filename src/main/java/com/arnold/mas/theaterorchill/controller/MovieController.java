package com.arnold.mas.theaterorchill.controller;

import com.arnold.mas.theaterorchill.config.AppProperties;
import com.arnold.mas.theaterorchill.model.Movie;
import com.arnold.mas.theaterorchill.model.MovieKey;
import com.arnold.mas.theaterorchill.model.MovieKeyResults;
import com.arnold.mas.theaterorchill.model.MovieResults;
import com.google.gson.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("movies")
public class MovieController {
    private final String language = "en-US";
    private final RestTemplate restTemplate;
    private final HttpHeaders headers  = new HttpHeaders();
    private final HttpEntity<String> entity = new HttpEntity<>(headers);
    private final Gson gson;
    private final AppProperties appProperties;

    private static final Logger logger = LoggerFactory.getLogger(MovieController.class);

    @Autowired
    public MovieController(RestTemplate restTemplate, Gson gson, AppProperties appProperties) {
        this.restTemplate = restTemplate;
        this.gson = gson;
        this.headers.set("User-Agent", "theatre-or-chill");
        this.appProperties = appProperties;

    }

    @GetMapping("/nowPlaying")
    public ResponseEntity<List<Movie>> getNowPlayingMovies() {
        String nowPlayingUrl = "https://api.themoviedb.org/3/movie/now_playing";
        return getListResponseEntity(nowPlayingUrl);
    }

    @GetMapping("/upcoming")
    public ResponseEntity<List<Movie>> getUpcomingMovies(){
        String upcomingUrl = "https://api.themoviedb.org/3/movie/upcoming";
        return getListResponseEntity(upcomingUrl);
    }

    private ResponseEntity<List<Movie>> getListResponseEntity(String url) {
        UriComponents builder = getUriBuilder(url);
        ResponseEntity<String> responseEntity = restTemplate.exchange(builder.toUriString(),
                HttpMethod.GET, entity, String.class);
        MovieResults movieResults = gson.fromJson(responseEntity.getBody(), MovieResults.class);
        List<Movie> movies = movieResults.getResults();
        getMovieKey(movies);
        return new ResponseEntity<>(movies, HttpStatus.OK);
    }

    void getMovieKey(List<Movie> movies){
        for(Movie movie: movies) {
            String movieUrl = "https://api.themoviedb.org/3/movie/";
            UriComponents builder = UriComponentsBuilder.fromHttpUrl(movieUrl)
                    .pathSegment("{movie_id}")
                    .pathSegment("videos")
                    .queryParam("api_key", appProperties.getApiKey("apiKey"))
                    .queryParam("language", language).build();
            Map<String, Integer> uriVariables = new HashMap<>();
            uriVariables.put("movie_id", movie.getId());
            ResponseEntity<String> responseEntity = restTemplate.exchange(builder.toUriString(),
                    HttpMethod.GET, entity, String.class, uriVariables);

            MovieKeyResults movieKeyResults = gson.fromJson(responseEntity.getBody(), MovieKeyResults.class);

            List<MovieKey> keys = movieKeyResults.getResults();
            try{
                movie.setKey(keys.get(0).getVideoKey());
            } catch(IndexOutOfBoundsException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    UriComponents getUriBuilder(String url) {
        String region = "CA";
        return UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("api_key", appProperties.getApiKey("apiKey"))
                .queryParam("language", language)
                .queryParam("region", region).build();
    }
}
