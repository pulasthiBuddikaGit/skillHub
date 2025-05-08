package com.paf.socialmedia.controller.progress;

import com.paf.socialmedia.document.progress.ProgressNotification;
import com.paf.socialmedia.service.progress.ProgressNotificationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/workout/notifications")
public class ProgressNotificationController {
    @Autowired
    private ProgressNotificationService workoutNotificationService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getWorkoutNotificationById(@PathVariable String id){
        return workoutNotificationService.getWorkoutNotificationById(id);
    }
    @GetMapping
    public ResponseEntity<?> getNotifications(){
        return workoutNotificationService.getWorkoutNotifications();
    }
    @GetMapping("/user/{id}")
    public ResponseEntity<?> getUnreadNotificationsByUserId(@PathVariable String id){
        return workoutNotificationService.getUnreadWorkoutNotificationsByUserId(id);
    }
    @PostMapping
    public ResponseEntity<?> saveNotification(@RequestBody ProgressNotification workoutNotification){
        return workoutNotificationService.saveWorkoutNotification(workoutNotification);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateNotificationById(@PathVariable String id, @RequestBody ProgressNotification workoutNotification){
        return  workoutNotificationService.updateWorkoutNotificationById(id,workoutNotification);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteNotificationById(@PathVariable String id){
        return workoutNotificationService.deleteWorkoutNotificationById(id);
    }
}

