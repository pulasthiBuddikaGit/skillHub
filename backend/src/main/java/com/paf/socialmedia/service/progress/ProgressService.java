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
    private ProgressRepository workoutRepository;

    @Autowired
    private ProgressCommentRepository workoutCommentRepository;

    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<?> getWorkoutById(String id){
        Optional<Progress> workout =  workoutRepository.findById(id);
        if(workout.isPresent()){
            return new ResponseEntity<>(workout.get(), HttpStatus.OK);
        }else{
            return new ResponseEntity<>("No Workout Found",HttpStatus.NOT_FOUND);
        }
    }
    public ResponseEntity<?> getWorkouts(){
        List<Progress> workouts = workoutRepository.findAll();

        List<ProgressDTO> workoutDTOList = new ArrayList<>();

        for (Progress workout:workouts) {
            ProgressDTO workoutDTO = new ProgressDTO();
            workoutDTO.setId(workout.getId());
            workoutDTO.setCaption(workout.getCaption());
            workoutDTO.setImgLink(workout.getImgLink());
            workoutDTO.setUpdatedAt(workout.getUpdatedAt());
            workoutDTO.setCreatedAt(workout.getCreatedAt());
            workoutDTO.setLikedby(workout.getLikedby());
            workoutDTO.setUserId(workout.getUserId());

            Optional<User> user =  userRepository.findById(workout.getUserId());
            if(user.isPresent()) {
                workoutDTO.setUsername(user.get().getUsername());
                workoutDTO.setProfileImage(user.get().getProfileImage());
            }

            List<ProgressComment> workoutComments = workoutCommentRepository.findByWorkoutId(workout.getId());
            if(workoutComments.size() > 0){
                List<ProgressCommentDTO> workoutCommentDTOList = new ArrayList<>();

                for(ProgressComment workoutComment: workoutComments){
                    ProgressCommentDTO workoutCommentDTO = new ProgressCommentDTO();
                    workoutCommentDTO.setId(workoutComment.getId());
                    workoutCommentDTO.setText(workoutComment.getText());
                    workoutCommentDTO.setWorkoutId(workoutComment.getWorkoutId());
                    workoutCommentDTO.setCreatedAt(workoutComment.getCreatedAt());
                    workoutCommentDTO.setUpdatedAt(workoutComment.getUpdatedAt());
                    workoutCommentDTO.setUserId(workoutComment.getUserId());
                    Optional<User> commentedUser =  userRepository.findById(workoutComment.getUserId());
                    if(commentedUser.isPresent()) {
                        workoutCommentDTO.setUsername(commentedUser.get().getUsername());
                        workoutCommentDTO.setProfileImage(commentedUser.get().getProfileImage());
                    }
                    if(commentedUser.isPresent()) {
                        workoutCommentDTOList.add(workoutCommentDTO);
                    }

                }

                workoutDTO.setWorkoutComments(workoutCommentDTOList);
            }
            if(user.isPresent()) {
                workoutDTOList.add(workoutDTO);
            }

        }

        return new ResponseEntity<List<ProgressDTO>>(workoutDTOList, HttpStatus.OK);
    }

    public ResponseEntity<?> getWorkoutsByUserId(String userId) {
        List<Progress> workouts = workoutRepository.findByUserId(userId);
        List<ProgressDTO> workoutDTOList = new ArrayList<>();

        for (Progress workout:workouts) {
            ProgressDTO workoutDTO = new ProgressDTO();
            workoutDTO.setId(workout.getId());
            workoutDTO.setCaption(workout.getCaption());
            workoutDTO.setImgLink(workout.getImgLink());
            workoutDTO.setUpdatedAt(workout.getUpdatedAt());
            workoutDTO.setCreatedAt(workout.getCreatedAt());
            workoutDTO.setLikedby(workout.getLikedby());
            workoutDTO.setUserId(workout.getUserId());

            Optional<User> user =  userRepository.findById(workout.getUserId());
            if(user.isPresent()) {
                workoutDTO.setUsername(user.get().getUsername());
                workoutDTO.setProfileImage(user.get().getProfileImage());
            }

            List<ProgressComment> workoutComments = workoutCommentRepository.findByWorkoutId(workout.getId());
            if(workoutComments.size() > 0){
                List<ProgressCommentDTO> workoutCommentDTOList = new ArrayList<>();

                for(ProgressComment workoutComment: workoutComments){
                    ProgressCommentDTO workoutCommentDTO = new ProgressCommentDTO();
                    workoutCommentDTO.setId(workoutComment.getId());
                    workoutCommentDTO.setText(workoutComment.getText());
                    workoutCommentDTO.setWorkoutId(workoutComment.getWorkoutId());
                    workoutCommentDTO.setCreatedAt(workoutComment.getCreatedAt());
                    workoutCommentDTO.setUpdatedAt(workoutComment.getUpdatedAt());
                    workoutCommentDTO.setUserId(workoutComment.getUserId());
                    Optional<User> commentedUser =  userRepository.findById(workoutComment.getUserId());
                    if(commentedUser.isPresent()) {
                        workoutCommentDTO.setUsername(commentedUser.get().getUsername());
                        workoutCommentDTO.setProfileImage(commentedUser.get().getProfileImage());
                    }
                    if(commentedUser.isPresent()) {
                        workoutCommentDTOList.add(workoutCommentDTO);
                    }

                }

                workoutDTO.setWorkoutComments(workoutCommentDTOList);
            }
            if(user.isPresent()) {
                workoutDTOList.add(workoutDTO);
            }

        }

        return new ResponseEntity<List<ProgressDTO>>(workoutDTOList, HttpStatus.OK);
    }
    public ResponseEntity<?> saveWorkout(Progress workoutShare){
        try{
            workoutShare.setCreatedAt(new Date(System.currentTimeMillis()));
            workoutShare.setUpdatedAt(new Date(System.currentTimeMillis()));
            workoutRepository.save(workoutShare);
            return new ResponseEntity<Progress>(workoutShare, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> updateWorkoutById(String id,Progress workout){
        Optional<Progress> existingWorkout =  workoutRepository.findById(id);
        if(existingWorkout.isPresent()){
            Progress updateWorkout = existingWorkout.get();
            if(workout.getCaption() != null) {
                updateWorkout.setCaption(workout.getCaption());
            }
            if(workout.getImgLink() != null) {
                updateWorkout.setImgLink(workout.getImgLink());
            }
            updateWorkout.setUpdatedAt(new Date(System.currentTimeMillis()));
            return new ResponseEntity<>(workoutRepository.save(updateWorkout), HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Workout Update Error",HttpStatus.NOT_FOUND);
        }
    }
    public ResponseEntity<?> likeWorkoutById(String id,Progress workout){
        Optional<Progress> existingWorkout =  workoutRepository.findById(id);
        if(existingWorkout.isPresent()){
            Progress updateWorkout = existingWorkout.get();
            if(workout.getLikedby() != null) {
                updateWorkout.setLikedby(workout.getLikedby());
            }
            return new ResponseEntity<>(workoutRepository.save(updateWorkout), HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Workout Update Error",HttpStatus.NOT_FOUND);
        }
    }
    public ResponseEntity<?> deleteWorkoutById(String id){
        try{
            workoutRepository.deleteById(id);
            return new ResponseEntity<>("Success deleted with " + id,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }
}
