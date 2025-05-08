package com.paf.socialmedia.service.progress;

import com.paf.socialmedia.document.User;
import com.paf.socialmedia.document.progress.Progress;
import com.paf.socialmedia.document.progress.ProgressShare;
import com.paf.socialmedia.dto.progress.ProgressDTO;
import com.paf.socialmedia.dto.progress.ProgressShareDTO;
import com.paf.socialmedia.repository.UserRepository;
import com.paf.socialmedia.repository.progress.WorkoutRepository;
import com.paf.socialmedia.repository.progress.WorkoutShareRepository;

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
    private WorkoutShareRepository workoutShareRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private WorkoutRepository workoutRepository;
    public ResponseEntity<?> getWorkoutById(String id){
        Optional<ProgressShare> workout =  workoutShareRepository.findById(id);
        if(workout.isPresent()){
            return new ResponseEntity<>(workout.get(), HttpStatus.OK);
        }else{
            return new ResponseEntity<>("No Workout Found", HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<?> getWorkouts(){
        List<ProgressShare> workoutNotifications = workoutShareRepository.findAll();
        return new ResponseEntity<List<ProgressShare>>(workoutNotifications, HttpStatus.OK);
    }

    public ResponseEntity<?> getsharedWorkoutsByUserId(String userId) {
        List<ProgressShare> sharedWorkouts = workoutShareRepository.findByUserId(userId);
        List<ProgressShareDTO> sharedWorkoutDTOList = new ArrayList<>();

        for (ProgressShare workoutShare:sharedWorkouts) {
            ProgressShareDTO workoutShareDTO = new ProgressShareDTO();
            workoutShareDTO.setId(workoutShare.getId());
            workoutShareDTO.setCaption(workoutShare.getCaption());
            workoutShareDTO.setUpdatedAt(workoutShare.getUpdatedAt());
            workoutShareDTO.setCreatedAt(workoutShare.getCreatedAt());
            workoutShareDTO.setUserId(workoutShare.getUserId());

            Optional<User> user =  userRepository.findById(workoutShare.getUserId());
            if(user.isPresent()) {
                workoutShareDTO.setUsername(user.get().getUsername());
                workoutShareDTO.setProfileImage(user.get().getProfileImage());
            }

            ProgressDTO workoutDTO = new ProgressDTO();
            Optional<Progress> workout =  workoutRepository.findById(workoutShare.getWorkout().getId());
            System.out.println("postshare.getPost().getId()" + workoutShare.getWorkout().getId());
            if(workout.isPresent()) {
                System.out.println("post.isPresent()" + workout.get().getId());
                workoutDTO.setId(workout.get().getId());
                workoutDTO.setCaption(workout.get().getCaption());
                workoutDTO.setImgLink(workout.get().getImgLink());
                workoutDTO.setUpdatedAt(workout.get().getUpdatedAt());
                workoutDTO.setCreatedAt(workout.get().getCreatedAt());
                workoutDTO.setUserId(workout.get().getUserId());

                Optional<User> workoutUser =  userRepository.findById(workout.get().getUserId());
                if(workoutUser.isPresent()) {
                    workoutDTO.setUsername(workoutUser.get().getUsername());
                    workoutDTO.setProfileImage(workoutUser.get().getProfileImage());
                }else{
                    workoutDTO.setUsername("Unavailable");
                }
                workoutShareDTO.setWorkout(workoutDTO);
            }


            sharedWorkoutDTOList.add(workoutShareDTO);
        }

        return new ResponseEntity<>(sharedWorkoutDTOList, HttpStatus.OK);
    }

    public ResponseEntity<?> saveWorkout(ProgressShare workoutShare){
        try{
            workoutShare.setCreatedAt(new Date(System.currentTimeMillis()));
            workoutShare.setUpdatedAt(new Date(System.currentTimeMillis()));
            workoutShareRepository.save(workoutShare);
            return new ResponseEntity<ProgressShare>(workoutShare, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> updateWorkoutById(String id,ProgressShare workoutShare){
        Optional<ProgressShare> existingWorkout =  workoutShareRepository.findById(id);
        if(existingWorkout.isPresent()){
            ProgressShare updateWorkout = existingWorkout.get();
            if(workoutShare.getCaption() != null) {
                updateWorkout.setCaption(workoutShare.getCaption());
            }
            updateWorkout.setUpdatedAt(new Date(System.currentTimeMillis()));
            return new ResponseEntity<>(workoutShareRepository.save(updateWorkout), HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Shared Workout Update Error",HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<?> deleteWorkoutById(String id){
        try{
            workoutShareRepository.deleteById(id);
            return new ResponseEntity<>("Success deleted with " + id,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }
}
