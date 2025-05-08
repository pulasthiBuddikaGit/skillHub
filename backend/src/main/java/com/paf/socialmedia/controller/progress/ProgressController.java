package com.paf.socialmedia.controller.progress;

import com.paf.socialmedia.document.progress.Progress;
import com.paf.socialmedia.service.progress.ProgressService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/workouts")
public class ProgressController {

    @Autowired
    private ProgressService workoutService;

    @PostMapping
    public ResponseEntity<?> saveWorkout(@RequestBody Progress workout){
        return workoutService.saveWorkout(workout);
    }
    @GetMapping
    public ResponseEntity<?> getWorkouts(){
        return workoutService.getWorkouts();
    }
    @GetMapping("/user/{id}")
    public ResponseEntity<?> getWorkoutsByUserId(@PathVariable String id){
        return workoutService.getWorkoutsByUserId(id);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getWorkoutById(@PathVariable String id){
        return workoutService.getWorkoutById(id);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateWorkoutById(@PathVariable String id, @RequestBody Progress workout){
        return  workoutService.updateWorkoutById(id,workout);
    }
    @PutMapping("/like/{id}")
    public ResponseEntity<?> likeWorkoutById(@PathVariable String id, @RequestBody Progress workout){
        return  workoutService.likeWorkoutById(id,workout);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteWorkoutById(@PathVariable String id){
        return workoutService.deleteWorkoutById(id);
    }
}

