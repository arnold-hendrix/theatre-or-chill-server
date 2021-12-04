// Movie controller class provides functions to retrieve now playing and upcoming movies from moviedb api via rest.

package com.arnold.mas.theaterorchill.controller;

import com.arnold.mas.theaterorchill.model.Movie;
import com.arnold.mas.theaterorchill.model.MovieKey;
import com.arnold.mas.theaterorchill.model.MovieKeyResults;
import com.arnold.mas.theaterorchill.model.MovieSearchResults;
import com.google.gson.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
    private final static String apiKey="dfbbb74a5de37d0033bd0ea63eb42865";
//    private String apiKey;
    private final static String language = "en-US";
    private final static String region = "CA";
    private final static String movieUrl="https://api.themoviedb.org/3/movie/";
    private final static String nowPlayingUrl="https://api.themoviedb.org/3/movie/now_playing";
    private final static String upcomingUrl="https://api.themoviedb.org/3/movie/upcoming";


    private final RestTemplate restTemplate;
    private final HttpHeaders headers  = new HttpHeaders();
    private final HttpEntity<String> entity = new HttpEntity<>(headers);
    private final Gson gson;

    private static final Logger logger = LoggerFactory.getLogger(MovieController.class);

    @Autowired
    public MovieController(RestTemplate restTemplate, Gson gson) {
        this.restTemplate = restTemplate;
        this.gson = gson;
        this.headers.set("User-Agent", "theatre-or-chill");

    } // constructor autowiring for RestTemplate and Gson dependencies.

    @GetMapping("/nowPlaying")  // returns a response entity containing a list of movies currently showing in theatres.
    public ResponseEntity<List<Movie>> getNowPlayingMovies() {
        return getListResponseEntity(nowPlayingUrl);
    }

    @GetMapping("/upcoming")  // returns a response entity containing a list of upcoming movies in theatres.
    public ResponseEntity<List<Movie>> getUpcomingMovies(){
        return getListResponseEntity(upcomingUrl);
    }

    private ResponseEntity<List<Movie>> getListResponseEntity(String movieUrl) {  // returns a response entity of type
        // list of movies.
        UriComponents builder = getUriBuilder(movieUrl);
        // string url and accompanying query params constructed using UriComponents builder.
        ResponseEntity<String> responseEntity = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, String.class);
        // restTamplate.exchange is used to call moviedb api and return a response entity of type String.
        MovieSearchResults movieSearchResults = gson.fromJson(responseEntity.getBody(), MovieSearchResults.class);
        // gson is used to deserialize and map the returned json into the MovieSearchResults POJO class.
        List<Movie> movies = movieSearchResults.getResults();
        // List of movies retrieved from movieSearchResults object and saved to local variable.
        getMovieKey(movies);
        // gets the videos (trailers) for each movie in the list of movies.
        return new ResponseEntity<>(movies, HttpStatus.OK);
    }

    void getMovieKey(List<Movie> movies){  // retrieves the video trailer for each movie using the movie id property as
        // a path variable.
        for(Movie movie: movies) {
            UriComponents builder = UriComponentsBuilder.fromHttpUrl(movieUrl)
                    .pathSegment("{movie_id}")
                    .pathSegment("videos")
                    .queryParam("api_key", apiKey)
                    .queryParam("language", language).build();
            Map<String, Integer> uriVariables = new HashMap<>();
            uriVariables.put("movie_id", movie.getId());
            ResponseEntity<String> responseEntity = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, String.class, uriVariables);

            MovieKeyResults movieKeyResults = gson.fromJson(responseEntity.getBody(), MovieKeyResults.class);

            List<MovieKey> keys = movieKeyResults.getResults();
            try{
                movie.setKey(keys.get(0).getVideoKey());
            } catch(IndexOutOfBoundsException e) {
                logger.error(e.getMessage(), e);
            }  // catch exception that may occur if a movie trailer is unavailable for a particular movie.
        }
    }

    UriComponents getUriBuilder(String url) {
        return UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("api_key", apiKey)
                .queryParam("language", language)
                .queryParam("region", region).build();
    }

}
