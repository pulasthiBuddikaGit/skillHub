package com.paf.socialmedia.repository;

import com.paf.socialmedia.document.ProgressShare;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ProgressShareRepository extends MongoRepository<ProgressShare, String> {
    List<ProgressShare> findByUserId(String userId);
}
