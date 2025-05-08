package com.paf.socialmedia.controller.progress;

import com.paf.socialmedia.document.PostShare;
import com.paf.socialmedia.document.progress.ProgressShare;
import com.paf.socialmedia.service.PostShareService;
import com.paf.socialmedia.service.progress.ProgressShareService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/workoutshare")
public class ProgressShareController {
    @Autowired
    private ProgressShareService workoutShareService;

    @PostMapping
    public ResponseEntity<?> saveWorkout(@RequestBody ProgressShare workoutShare){
        return workoutShareService.saveWorkout(workoutShare);
    }
    @GetMapping
    public ResponseEntity<?> getWorkouts(){
        return workoutShareService.getWorkouts();
    }
    @GetMapping("/user/{id}")
    public ResponseEntity<?> getsharedWorkoutsByUserId(@PathVariable String id){
        return workoutShareService.getsharedWorkoutsByUserId(id);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getWorkoutById(@PathVariable String id){
        return workoutShareService.getWorkoutById(id);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateWorkoutById(@PathVariable String id, @RequestBody ProgressShare workoutShare){
        return  workoutShareService.updateWorkoutById(id,workoutShare);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteWorkoutById(@PathVariable String id){
        return workoutShareService.deleteWorkoutById(id);
    }
}

