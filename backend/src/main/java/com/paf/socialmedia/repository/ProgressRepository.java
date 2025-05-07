package com.paf.socialmedia.repository;

import com.paf.socialmedia.document.ProgressPost;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProgressRepository extends MongoRepository<ProgressPost, String> {
    List<ProgressPost> findByUserId(String userId);
}
