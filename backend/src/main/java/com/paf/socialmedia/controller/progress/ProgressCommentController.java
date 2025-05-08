package com.paf.socialmedia.controller.progress;

import com.paf.socialmedia.document.progress.ProgressComment;
import com.paf.socialmedia.service.progress.ProgressCommentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/workout/comments")
public class ProgressCommentController {
    @Autowired
    private ProgressCommentService workoutCommentService;

    //Get data from id
    @GetMapping("/{id}")
    public ResponseEntity<?> getWorkoutCommentById(@PathVariable String id){
        return workoutCommentService.getWorkoutCommentById(id);
    }
    
    //Get data from id
    @GetMapping
    public ResponseEntity<?> getWorkoutComments(){
        return workoutCommentService.getWorkoutComments();
    }

    //Get data from id
    @GetMapping("/workout/{id}")
    public ResponseEntity<?> getWorkoutCommentsByWorkout(@PathVariable String id){
        return workoutCommentService.getWorkoutCommentsByWorkout(id);
    }

    //Get data from id
    @PostMapping
    public ResponseEntity<?> saveWorkoutComment(@RequestBody ProgressComment workoutComment){
        return workoutCommentService.saveWorkoutComment(workoutComment);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateWorkoutCommentById(@PathVariable String id, @RequestBody ProgressComment workoutComment){
        return  workoutCommentService.updateWorkoutCommentById(id,workoutComment);
    }

    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteWorkoutCommentById(@PathVariable String id){
        return workoutCommentService.deleteWorkoutCommentById(id);
    }
}

