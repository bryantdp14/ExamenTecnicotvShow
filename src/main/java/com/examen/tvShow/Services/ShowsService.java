package com.examen.tvShow.Services;

import com.examen.tvShow.Utils.TvMazeClient;
import com.examen.tvShow.Models.CachedShowModel;
import com.examen.tvShow.Models.CommentsModel;
import com.examen.tvShow.Repository.CachedShowRepository;
import com.examen.tvShow.Services.Interface.IShowsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ShowsService implements IShowsService {

	@Autowired
    private TvMazeClient tvMazeClient;
	@Autowired
    private CachedShowRepository cachedShowRepository;
	@Autowired
    private CommentsService commentsService;

 
    // Realiza la busqueda de shows y sus comentarios
    public List<Map<String, Object>> searchShows(String query) {
        try {
            List<Map<String, Object>> externalResults = tvMazeClient.searchShows(query);

            if (externalResults == null || externalResults.isEmpty()) {
                return Collections.emptyList();
            }

            List<Long> showIds = externalResults.stream()
                    .map(item -> (Map<String, Object>) item.get("show"))
                    .filter(Objects::nonNull)
                    .map(show -> ((Number) show.get("id")).longValue())
                    .toList();

            List<CommentsModel> commentsList = commentsService.getCommentsByShowIds(showIds);
            Map<Long, List<Map<String, Object>>> commentsByShowId = commentsList.stream()
                    .collect(Collectors.groupingBy(
                            CommentsModel::getShowId,
                            Collectors.mapping(c -> {
                                Map<String, Object> commentMap = new LinkedHashMap<>();
                                commentMap.put("comment", c.getComment());
                                commentMap.put("rating", c.getRating());
                                return commentMap;
                            }, Collectors.toList())
                    ));

            List<Map<String, Object>> formattedShows = new ArrayList<>();
            for (Map<String, Object> item : externalResults) {
                Map<String, Object> show = (Map<String, Object>) item.get("show");
                if (show == null) continue;

                Long id = ((Number) show.get("id")).longValue();
                String name = (String) show.get("name");
                String summary = (String) show.get("summary");
                List<String> genres = (List<String>) show.get("genres");

                String channel = null;
                if (show.get("network") instanceof Map<?, ?> network) {
                    channel = (String) network.get("name");
                } else if (show.get("webChannel") instanceof Map<?, ?> webChannel) {
                    channel = (String) webChannel.get("name");
                }

                List<Map<String, Object>> showComments = commentsByShowId.getOrDefault(id, Collections.emptyList());

                Map<String, Object> responseItem = new LinkedHashMap<>();
                responseItem.put("id", id);
                responseItem.put("name", name);
                responseItem.put("channel", channel);
                responseItem.put("summary", summary);
                responseItem.put("genres", genres);
                responseItem.put("comments", showComments);

                formattedShows.add(responseItem);
            }

            return formattedShows;

        } catch (Exception ex) {
            throw new RuntimeException("Error en searchShows: " + ex.getMessage(), ex);
        }
    }

    // Obtiene el show por ID, usando cache en mongo
    public Map<String, Object> getShowById(Long showId) {
        try {
            Map<String, Object> showData = null;

            try {
                Optional<CachedShowModel> cached = cachedShowRepository.findById(showId);
                if (cached.isPresent()) {
                    showData = cached.get().getData();
                }
            } catch (Exception mongoEx) {
                System.err.println("Advertencia al leer cache de Mongo: " + mongoEx.getMessage());
            }

            if (showData == null) {
                showData = tvMazeClient.getShowById(showId);

                if (showData != null && !showData.isEmpty()) {
                    try {
                        cachedShowRepository.save(new CachedShowModel(showId, showData));
                    } catch (Exception mongoSaveEx) {
                        System.err.println("Advertencia al guardar cache en Mongo: " + mongoSaveEx.getMessage());
                    }
                }
            }

            if (showData == null || showData.isEmpty()) {
                return Collections.emptyMap();
            }

            Map<String, Object> response = new LinkedHashMap<>(showData);
            List<CommentsModel> comments = commentsService.getCommentsByShowId(showId);

            List<Map<String, Object>> formattedComments = comments.stream().map(c -> {
                Map<String, Object> map = new LinkedHashMap<>();
                map.put("comment", c.getComment());
                map.put("rating", c.getRating());
                return map;
            }).toList();

            response.put("comments", formattedComments);

            return response;

        } catch (Exception ex) {
            throw new RuntimeException("Error en getShowById: " + ex.getMessage(), ex);
        }
    }
}