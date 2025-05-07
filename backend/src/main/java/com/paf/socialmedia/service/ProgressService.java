package com.paf.socialmedia.service;

import com.paf.socialmedia.document.Comment;
import com.paf.socialmedia.document.ProgressPost;
import com.paf.socialmedia.document.User;
import com.paf.socialmedia.dto.CommentDTO;
import com.paf.socialmedia.dto.ProgressDTO;
import com.paf.socialmedia.repository.CommentRepository;
import com.paf.socialmedia.repository.ProgressRepository;
import com.paf.socialmedia.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ProgressService {
    @Autowired
    private ProgressRepository progressRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<?> getPostById(String id){
        Optional<ProgressPost> progressPost = progressRepository.findById(id);
        if(progressPost.isPresent()){
            return new ResponseEntity<>(progressPost.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No Progress Post Found", HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<?> getPosts(){
        List<ProgressPost> posts = progressRepository.findAll();
        List<ProgressDTO> progressDTOList = new ArrayList<>();

        for (ProgressPost post : posts) {
            ProgressDTO dto = buildProgressDTO(post);
            if (dto != null) {
                progressDTOList.add(dto);
            }
        }

        return new ResponseEntity<>(progressDTOList, HttpStatus.OK);
    }

    public ResponseEntity<?> getPostsByUserId(String userId) {
        List<ProgressPost> posts = progressRepository.findByUserId(userId);
        List<ProgressDTO> progressDTOList = new ArrayList<>();

        for (ProgressPost post : posts) {
            ProgressDTO dto = buildProgressDTO(post);
            if (dto != null) {
                progressDTOList.add(dto);
            }
        }

        return new ResponseEntity<>(progressDTOList, HttpStatus.OK);
    }

    public ResponseEntity<?> savePost(ProgressPost progressPost){
        try {
            progressPost.setCreatedAt(new Date(System.currentTimeMillis()));
            progressPost.setUpdatedAt(new Date(System.currentTimeMillis()));
            progressRepository.save(progressPost);
            return new ResponseEntity<>(progressPost, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> updatePostById(String id, ProgressPost progressPost){
        Optional<ProgressPost> existingPost = progressRepository.findById(id);
        if(existingPost.isPresent()){
            ProgressPost updatePost = existingPost.get();
            if(progressPost.getCaption() != null) {
                updatePost.setCaption(progressPost.getCaption());
            }
            if(progressPost.getImgLink() != null) {
                updatePost.setImgLink(progressPost.getImgLink());
            }
            updatePost.setUpdatedAt(new Date(System.currentTimeMillis()));
            return new ResponseEntity<>(progressRepository.save(updatePost), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Progress Post Update Error", HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<?> likePostById(String id, ProgressPost progressPost){
        Optional<ProgressPost> existingPost = progressRepository.findById(id);
        if(existingPost.isPresent()){
            ProgressPost updatePost = existingPost.get();
            if(progressPost.getLikedby() != null) {
                updatePost.setLikedby(progressPost.getLikedby());
            }
            return new ResponseEntity<>(progressRepository.save(updatePost), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Progress Post Like Error", HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<?> deletePostById(String id){
        try {
            progressRepository.deleteById(id);
            return new ResponseEntity<>("Successfully deleted Progress Post with ID: " + id, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    private ProgressDTO buildProgressDTO(ProgressPost post) {
        Optional<User> user = userRepository.findById(post.getUserId());
        if (user.isEmpty()) return null;

        ProgressDTO dto = new ProgressDTO();
        dto.setId(post.getId());
        dto.setCaption(post.getCaption());
        dto.setImgLink(post.getImgLink());
        dto.setUpdatedAt(post.getUpdatedAt());
        dto.setCreatedAt(post.getCreatedAt());
        dto.setLikedby(post.getLikedby());
        dto.setUserId(post.getUserId());
        dto.setUsername(user.get().getUsername());
        dto.setProfileImage(user.get().getProfileImage());

        List<Comment> comments = commentRepository.findByPostId(post.getId());
        if (!comments.isEmpty()) {
            List<CommentDTO> commentDTOList = new ArrayList<>();
            for (Comment comment : comments) {
                Optional<User> commenter = userRepository.findById(comment.getUserId());
                if (commenter.isPresent()) {
                    CommentDTO commentDTO = new CommentDTO();
                    commentDTO.setId(comment.getId());
                    commentDTO.setText(comment.getText());
                    commentDTO.setPostId(comment.getPostId());
                    commentDTO.setCreatedAt(comment.getCreatedAt());
                    commentDTO.setUpdatedAt(comment.getUpdatedAt());
                    commentDTO.setUserId(comment.getUserId());
                    commentDTO.setUsername(commenter.get().getUsername());
                    commentDTO.setProfileImage(commenter.get().getProfileImage());
                    commentDTOList.add(commentDTO);
                }
            }
            dto.setComments(commentDTOList);
        }

        return dto;
    }
}
