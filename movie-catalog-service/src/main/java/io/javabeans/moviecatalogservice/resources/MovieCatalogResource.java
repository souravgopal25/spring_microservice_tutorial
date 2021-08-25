package io.javabeans.moviecatalogservice.resources;

import io.javabeans.moviecatalogservice.models.CatalogItem;
import io.javabeans.moviecatalogservice.models.Movie;
import io.javabeans.moviecatalogservice.models.Rating;
import io.javabeans.moviecatalogservice.models.UserRating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {
    @Autowired
    private RestTemplate restTemplate;

    @Qualifier("getWebClient")
    @Autowired
    private WebClient.Builder webClient;

    @GetMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable("userId") String userId) {
        //get all rated movie ID's

        UserRating userrating = restTemplate.getForObject("http://rating-data-service/ratingsdata/users/" + userId, UserRating.class);
        List<Rating> ratings = userrating.getUserRating();
        return ratings.stream().map(rating -> {
            //For each movie Id, call movie info service and get details

            Movie movie = restTemplate.getForObject("http://movie-info-service/movie/" + rating.getMovieId(), Movie.class);

          /*  Movie movie = webClient.build()
                    .get()
                    .uri("http://localhost:8082/movie/" + rating.getMovieId())
                    .retrieve()
                    .bodyToMono(Movie.class).block();*/

            //put them all together
            return new CatalogItem(movie.getName(), "Test", rating.getRating());
        }).collect(Collectors.toList());

        //  return Collections.singletonList(new CatalogItem("Transformers", "test", 4));

    }
}
