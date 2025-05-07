package com.paf.socialmedia.service;

import com.paf.socialmedia.document.ProgressShare;
import com.paf.socialmedia.document.Post;
import com.paf.socialmedia.document.User;
import com.paf.socialmedia.dto.ProgressDTO;
import com.paf.socialmedia.dto.SharedProgressDTO;
import com.paf.socialmedia.repository.ProgressShareRepository;
import com.paf.socialmedia.repository.UserRepository;
import com.paf.socialmedia.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ProgressShareService {

    @Autowired
    private ProgressShareRepository progressShareRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    public ResponseEntity<?> getProgressById(String id) {
        Optional<ProgressShare> progress = progressShareRepository.findById(id);
        if (progress.isPresent()) {
            return new ResponseEntity<>(progress.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No Progress Found", HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<?> getProgresses() {
        List<ProgressShare> progresses = progressShareRepository.findAll();
        return new ResponseEntity<>(progresses, HttpStatus.OK);
    }

    public ResponseEntity<?> getSharedProgressByUserId(String userId) {
        List<ProgressShare> sharedProgresses = progressShareRepository.findByUserId(userId);
        List<SharedProgressDTO> sharedProgressDTOList = new ArrayList<>();

        for (ProgressShare progressShare : sharedProgresses) {
            SharedProgressDTO sharedProgressDTO = new SharedProgressDTO();
            sharedProgressDTO.setId(progressShare.getId());
            sharedProgressDTO.setCaption(progressShare.getCaption());
            sharedProgressDTO.setUpdatedAt(progressShare.getUpdatedAt());
            sharedProgressDTO.setCreatedAt(progressShare.getCreatedAt());
            sharedProgressDTO.setUserId(progressShare.getUserId());

            Optional<User> user = userRepository.findById(progressShare.getUserId());
            if (user.isPresent()) {
                sharedProgressDTO.setUsername(user.get().getUsername());
                sharedProgressDTO.setProfileImage(user.get().getProfileImage());
            }

            ProgressDTO progressDTO = new ProgressDTO();
            Optional<Post> post = postRepository.findById(progressShare.getProgressPost().getId());
            System.out.println("progressShare.getProgressPost().getId(): " + progressShare.getProgressPost().getId());
            if (post.isPresent()) {
                System.out.println("post.isPresent(): " + post.get().getId());
                progressDTO.setId(post.get().getId());
                progressDTO.setCaption(post.get().getCaption());
                progressDTO.setImgLink(post.get().getImgLink());
                progressDTO.setUpdatedAt(post.get().getUpdatedAt());
                progressDTO.setCreatedAt(post.get().getCreatedAt());
                progressDTO.setUserId(post.get().getUserId());

                Optional<User> postUser = userRepository.findById(post.get().getUserId());
                if (postUser.isPresent()) {
                    progressDTO.setUsername(postUser.get().getUsername());
                    progressDTO.setProfileImage(postUser.get().getProfileImage());
                } else {
                    progressDTO.setUsername("Unavailable");
                }

                sharedProgressDTO.setProgress(progressDTO);
            }

            sharedProgressDTOList.add(sharedProgressDTO);
        }

        return new ResponseEntity<>(sharedProgressDTOList, HttpStatus.OK);
    }

    public ResponseEntity<?> saveProgress(ProgressShare progressShare) {
        try {
            progressShare.setCreatedAt(new Date(System.currentTimeMillis()));
            progressShare.setUpdatedAt(new Date(System.currentTimeMillis()));
            progressShareRepository.save(progressShare);
            return new ResponseEntity<>(progressShare, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> updateProgressById(String id, ProgressShare progressShare) {
        Optional<ProgressShare> existingProgress = progressShareRepository.findById(id);
        if (existingProgress.isPresent()) {
            ProgressShare updatedProgress = existingProgress.get();
            if (progressShare.getCaption() != null) {
                updatedProgress.setCaption(progressShare.getCaption());
            }
            updatedProgress.setUpdatedAt(new Date(System.currentTimeMillis()));
            return new ResponseEntity<>(progressShareRepository.save(updatedProgress), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Shared Progress Update Error", HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<?> deleteProgressById(String id) {
        try {
            progressShareRepository.deleteById(id);
            return new ResponseEntity<>("Successfully deleted with ID: " + id, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
