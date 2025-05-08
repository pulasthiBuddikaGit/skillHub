package com.paf.socialmedia.repository.progress;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.paf.socialmedia.document.progress.ProgressShare;

import java.util.List;

public interface WorkoutShareRepository extends MongoRepository<ProgressShare, String> {
    List<ProgressShare> findByUserId(String userId);
}
