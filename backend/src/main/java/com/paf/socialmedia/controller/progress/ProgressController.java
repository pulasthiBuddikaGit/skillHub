package com.paf.socialmedia.controller.progress;

import com.paf.socialmedia.document.progress.Progress;
import com.paf.socialmedia.service.progress.ProgressService;

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
    public ResponseEntity<?> saveProgress(@RequestBody Progress progress){
        return progressService.saveProgress(progress);
    }

    @GetMapping
    public ResponseEntity<?> getProgresses(){
        return progressService.getProgresses();
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<?> getProgressesByUserId(@PathVariable String id){
        return progressService.getProgressesByUserId(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProgressById(@PathVariable String id){
        return progressService.getProgressById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProgressById(@PathVariable String id, @RequestBody Progress progress){
        return progressService.updateProgressById(id, progress);
    }

    @PutMapping("/like/{id}")
    public ResponseEntity<?> likeProgressById(@PathVariable String id, @RequestBody Progress progress){
        return progressService.likeProgressById(id, progress);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProgressById(@PathVariable String id){
        return progressService.deleteProgressById(id);
    }
}
