package com.examen.tvShow.Repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.examen.tvShow.Models.CommentsModel;

@Repository
public interface CommentsRepository extends MongoRepository<CommentsModel, String> {
	List<CommentsModel> findByShowId(Long showId);
    List<CommentsModel> findByShowIdIn(List<Long> showIds);
}
