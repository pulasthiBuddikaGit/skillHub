package com.paf.socialmedia.service.progress;

import com.paf.socialmedia.document.User;
import com.paf.socialmedia.document.progress.Progress;
import com.paf.socialmedia.document.progress.ProgressComment;
import com.paf.socialmedia.dto.progress.ProgressCommentDTO;
import com.paf.socialmedia.dto.progress.ProgressDTO;
import com.paf.socialmedia.repository.UserRepository;
import com.paf.socialmedia.repository.progress.ProgressCommentRepository;
import com.paf.socialmedia.repository.progress.ProgressRepository;

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
    private ProgressCommentRepository progressCommentRepository;

    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<?> getProgressById(String id){
        Optional<Progress> progress =  progressRepository.findById(id);
        if(progress.isPresent()){
            return new ResponseEntity<>(progress.get(), HttpStatus.OK);
        }else{
            return new ResponseEntity<>("No Progress Found",HttpStatus.NOT_FOUND);
        }
    }
    public ResponseEntity<?> getProgresses(){
        List<Progress> progresses = progressRepository.findAll();

        List<ProgressDTO> progressDTOList = new ArrayList<>();

        for (Progress progress:progresses) {
            ProgressDTO progressDTO = new ProgressDTO();
            progressDTO.setId(progress.getId());
            progressDTO.setCaption(progress.getCaption());
            progressDTO.setImgLink(progress.getImgLink());
            progressDTO.setUpdatedAt(progress.getUpdatedAt());
            progressDTO.setCreatedAt(progress.getCreatedAt());
            progressDTO.setLikedby(progress.getLikedby());
            progressDTO.setUserId(progress.getUserId());

            Optional<User> user =  userRepository.findById(progress.getUserId());
            if(user.isPresent()) {
                progressDTO.setUsername(user.get().getUsername());
                progressDTO.setProfileImage(user.get().getProfileImage());
            }

            List<ProgressComment> progressComments = progressCommentRepository.findByProgressId(progress.getId());
            if(progressComments.size() > 0){
                List<ProgressCommentDTO> progressCommentDTOList = new ArrayList<>();

                for(ProgressComment progressComment: progressComments){
                    ProgressCommentDTO progressCommentDTO = new ProgressCommentDTO();
                    progressCommentDTO.setId(progressComment.getId());
                    progressCommentDTO.setText(progressComment.getText());
                    progressCommentDTO.setProgressId(progressComment.getProgressId());
                    progressCommentDTO.setCreatedAt(progressComment.getCreatedAt());
                    progressCommentDTO.setUpdatedAt(progressComment.getUpdatedAt());
                    progressCommentDTO.setUserId(progressComment.getUserId());
                    Optional<User> commentedUser =  userRepository.findById(progressComment.getUserId());
                    if(commentedUser.isPresent()) {
                        progressCommentDTO.setUsername(commentedUser.get().getUsername());
                        progressCommentDTO.setProfileImage(commentedUser.get().getProfileImage());
                    }
                    if(commentedUser.isPresent()) {
                        progressCommentDTOList.add(progressCommentDTO);
                    }

                }

                progressDTO.setProgressComments(progressCommentDTOList);
            }
            if(user.isPresent()) {
                progressDTOList.add(progressDTO);
            }

        }

        return new ResponseEntity<List<ProgressDTO>>(progressDTOList, HttpStatus.OK);
    }

    public ResponseEntity<?> getProgressesByUserId(String userId) {
        List<Progress> progresses = progressRepository.findByUserId(userId);
        List<ProgressDTO> progressDTOList = new ArrayList<>();

        for (Progress progress:progresses) {
            ProgressDTO progressDTO = new ProgressDTO();
            progressDTO.setId(progress.getId());
            progressDTO.setCaption(progress.getCaption());
            progressDTO.setImgLink(progress.getImgLink());
            progressDTO.setUpdatedAt(progress.getUpdatedAt());
            progressDTO.setCreatedAt(progress.getCreatedAt());
            progressDTO.setLikedby(progress.getLikedby());
            progressDTO.setUserId(progress.getUserId());

            Optional<User> user =  userRepository.findById(progress.getUserId());
            if(user.isPresent()) {
                progressDTO.setUsername(user.get().getUsername());
                progressDTO.setProfileImage(user.get().getProfileImage());
            }

            List<ProgressComment> progressComments = progressCommentRepository.findByProgressId(progress.getId());
            if(progressComments.size() > 0){
                List<ProgressCommentDTO> progressCommentDTOList = new ArrayList<>();

                for(ProgressComment progressComment: progressComments){
                    ProgressCommentDTO progressCommentDTO = new ProgressCommentDTO();
                    progressCommentDTO.setId(progressComment.getId());
                    progressCommentDTO.setText(progressComment.getText());
                    progressCommentDTO.setProgressId(progressComment.getProgressId());
                    progressCommentDTO.setCreatedAt(progressComment.getCreatedAt());
                    progressCommentDTO.setUpdatedAt(progressComment.getUpdatedAt());
                    progressCommentDTO.setUserId(progressComment.getUserId());
                    Optional<User> commentedUser =  userRepository.findById(progressComment.getUserId());
                    if(commentedUser.isPresent()) {
                        progressCommentDTO.setUsername(commentedUser.get().getUsername());
                        progressCommentDTO.setProfileImage(commentedUser.get().getProfileImage());
                    }
                    if(commentedUser.isPresent()) {
                        progressCommentDTOList.add(progressCommentDTO);
                    }

                }

                progressDTO.setProgressComments(progressCommentDTOList);
            }
            if(user.isPresent()) {
                progressDTOList.add(progressDTO);
            }

        }

        return new ResponseEntity<List<ProgressDTO>>(progressDTOList, HttpStatus.OK);
    }
    public ResponseEntity<?> saveProgress(Progress progressShare){
        try{
            progressShare.setCreatedAt(new Date(System.currentTimeMillis()));
            progressShare.setUpdatedAt(new Date(System.currentTimeMillis()));
            progressRepository.save(progressShare);
            return new ResponseEntity<Progress>(progressShare, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> updateProgressById(String id,Progress progress){
        Optional<Progress> existingProgress =  progressRepository.findById(id);
        if(existingProgress.isPresent()){
            Progress updateProgress = existingProgress.get();
            if(progress.getCaption() != null) {
                updateProgress.setCaption(progress.getCaption());
            }
            if(progress.getImgLink() != null) {
                updateProgress.setImgLink(progress.getImgLink());
            }
            updateProgress.setUpdatedAt(new Date(System.currentTimeMillis()));
            return new ResponseEntity<>(progressRepository.save(updateProgress), HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Progress Update Error",HttpStatus.NOT_FOUND);
        }
    }
    public ResponseEntity<?> likeProgressById(String id,Progress progress){
        Optional<Progress> existingProgress =  progressRepository.findById(id);
        if(existingProgress.isPresent()){
            Progress updateProgress = existingProgress.get();
            if(progress.getLikedby() != null) {
                updateProgress.setLikedby(progress.getLikedby());
            }
            return new ResponseEntity<>(progressRepository.save(updateProgress), HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Progress Update Error",HttpStatus.NOT_FOUND);
        }
    }
    public ResponseEntity<?> deleteProgressById(String id){
        try{
            progressRepository.deleteById(id);
            return new ResponseEntity<>("Success deleted with " + id,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }
}
