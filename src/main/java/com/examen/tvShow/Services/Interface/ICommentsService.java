package com.examen.tvShow.Services.Interface;

import java.util.List;

import com.examen.tvShow.Models.CommentsModel;

public interface ICommentsService {
	List<CommentsModel> findByShowId(Long showId);
    List<CommentsModel> findByShowIdIn(List<Long> showIds);
}
