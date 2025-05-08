package com.paf.socialmedia.service.progress;

import com.paf.socialmedia.document.progress.ProgressComment;
import com.paf.socialmedia.repository.progress.WorkoutCommentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ProgressCommentService {
    @Autowired
    private WorkoutCommentRepository workoutCommentRepository;

    public ResponseEntity<?> getWorkoutCommentById(String id){
        Optional<ProgressComment> workoutComment =  workoutCommentRepository.findById(id);
        if(workoutComment.isPresent()){
            return new ResponseEntity<>(workoutComment.get(), HttpStatus.OK);
        }else{
            return new ResponseEntity<>("No Comment Found",HttpStatus.NOT_FOUND);
        }
    }
    public ResponseEntity<?> getWorkoutComments(){
        List<ProgressComment> workoutComment = workoutCommentRepository.findAll();
        if(workoutComment.size() > 0){
            return new ResponseEntity<List<ProgressComment>>(workoutComment, HttpStatus.OK);
        }else {
            return new ResponseEntity<List<ProgressComment>>(new ArrayList<>(),HttpStatus.OK);
        }
    }

    public ResponseEntity<?> getWorkoutCommentsByWorkout(String postId){
        List<ProgressComment> workoutComment = workoutCommentRepository.findByWorkoutId(postId);
        if(workoutComment.size() > 0){
            return new ResponseEntity<List<ProgressComment>>(workoutComment, HttpStatus.OK);
        }else {
            return new ResponseEntity<List<ProgressComment>>(new ArrayList<>(),HttpStatus.OK);
        }
    }

    public ResponseEntity<?> saveWorkoutComment(ProgressComment workoutComment){
        try{
            workoutComment.setCreatedAt(new Date(System.currentTimeMillis()));
            workoutComment.setUpdatedAt(new Date(System.currentTimeMillis()));
            workoutCommentRepository.save(workoutComment);
            return new ResponseEntity<ProgressComment>(workoutComment, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> updateWorkoutCommentById(String id,ProgressComment workoutComment){
        Optional<ProgressComment> existingComment =  workoutCommentRepository.findById(id);
        if(existingComment.isPresent()){
            ProgressComment updateComment = existingComment.get();
            updateComment.setText(workoutComment.getText());
            updateComment.setUpdatedAt(new Date(System.currentTimeMillis()));
            return new ResponseEntity<>(workoutCommentRepository.save(updateComment), HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Comment Update Error",HttpStatus.NOT_FOUND);
        }
    }
    public ResponseEntity<?> deleteWorkoutCommentById(String id){
        try{
            workoutCommentRepository.deleteById(id);
            return new ResponseEntity<>("Success deleted with " + id,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }
}

