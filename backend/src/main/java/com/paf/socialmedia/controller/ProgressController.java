// ProgressController.java
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
        return progressService.savePost(progressPost);
    }

    @GetMapping
    public ResponseEntity<?> getAllProgressPosts(){
        return progressService.getPosts();
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<?> getProgressPostsByUserId(@PathVariable String id){
        return progressService.getPostsByUserId(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProgressPostById(@PathVariable String id){
        return progressService.getPostById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProgressPostById(@PathVariable String id, @RequestBody ProgressPost progressPost){
        return progressService.updatePostById(id, progressPost);
    }

    @PutMapping("/like/{id}")
    public ResponseEntity<?> likeProgressPostById(@PathVariable String id, @RequestBody ProgressPost progressPost){
        return progressService.likePostById(id, progressPost);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProgressPostById(@PathVariable String id){
        return progressService.deletePostById(id);
    }
}