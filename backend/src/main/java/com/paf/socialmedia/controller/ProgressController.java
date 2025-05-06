package com.paf.socialmedia.controller;

import com.paf.socialmedia.document.ProgressPost;
import com.paf.socialmedia.service.ProgressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/progress")
public class ProgressController {

    @Autowired
    private ProgressService progressService;

    @PostMapping
    public ResponseEntity<?> saveProgress(@RequestBody ProgressPost progressPost){
        return progressService.saveProgress(progressPost);
    }

    @GetMapping
    public ResponseEntity<?> getAllProgressPosts(){
        return progressService.getAllProgressPosts();
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<?> getProgressPostsByUserId(@PathVariable String id){
        return progressService.getProgressPostsByUserId(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProgressPostById(@PathVariable String id){
        return progressService.getProgressPostById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProgressPostById(@PathVariable String id, @RequestBody ProgressPost progressPost){
        return progressService.updateProgressPostById(id, progressPost);
    }

    @PutMapping("/like/{id}")
    public ResponseEntity<?> likeProgressPostById(@PathVariable String id, @RequestBody ProgressPost progressPost){
        return progressService.likeProgressPostById(id, progressPost);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProgressPostById(@PathVariable String id){
        return progressService.deleteProgressPostById(id);
    }
}
