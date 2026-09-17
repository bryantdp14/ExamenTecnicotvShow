package com.examen.tvShow.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.examen.tvShow.Models.ShowModel;

@Repository
public interface ShowsRepository extends MongoRepository<ShowModel, Long> {

}
