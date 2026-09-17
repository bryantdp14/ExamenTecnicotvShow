package com.examen.tvShow.Utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Component
public class TvMazeClient {

    private final RestClient restClient;

    public TvMazeClient() {
        this.restClient = RestClient.create("https://api.tvmaze.com");
    }

    // Busca shows en TvMaze por query
    public List<Map<String, Object>> searchShows(String query) {
        try {
            List<Map<String, Object>> result = restClient.get()
                    .uri("/search/shows?q={query}", query)
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {});

            return result != null ? result : Collections.emptyList();
        } catch (Exception ex) {
            System.err.println("Error al consumir searchShows en TV Maze: " + ex.getMessage());
            return Collections.emptyList();
        }
    }

    // Obtiene un show por ID en TvMaze
    public Map<String, Object> getShowById(Long showId) {
        try {
            return restClient.get()
                    .uri("/shows/{show_id}", showId)
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {});
        } catch (HttpClientErrorException.NotFound ex) {
            return null;
        } catch (Exception ex) {
            System.err.println("Error al consumir getShowById en TV Maze: " + ex.getMessage());
            return null;
        }
    }
}