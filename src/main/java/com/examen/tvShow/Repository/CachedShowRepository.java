package com.examen.tvShow.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.examen.tvShow.Models.CachedShowModel;

@Repository
public interface CachedShowRepository extends MongoRepository<CachedShowModel, Long> {
}