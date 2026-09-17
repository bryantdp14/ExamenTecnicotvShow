package com.examen.tvShow.Services.Interface;

import java.util.List;
import java.util.Map;

import com.examen.tvShow.Dtos.SearchShowResponseDto;

public interface IShowsService {
	public List<Map<String, Object>> searchShows(String query);
	public Map<String, Object> getShowById(Long showId);
}
