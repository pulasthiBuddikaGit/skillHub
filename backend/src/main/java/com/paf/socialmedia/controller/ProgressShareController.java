package com.paf.socialmedia.controller;

import com.paf.socialmedia.document.ProgressShare;
import com.paf.socialmedia.service.ProgressShareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/progressshare")
public class ProgressShareController {

    @Autowired
    private ProgressShareService progressShareService;

    @PostMapping
    public ResponseEntity<?> saveProgressShare(@RequestBody ProgressShare progressShare) {
        return progressShareService.saveProgress(progressShare); // ✅ aligned method
    }

    @GetMapping
    public ResponseEntity<?> getProgressShares() {
        return progressShareService.getProgresses(); // ✅ aligned method
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<?> getSharedProgressByUserId(@PathVariable String id) {
        return progressShareService.getSharedProgressByUserId(id); // ✅ aligned method
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProgressShareById(@PathVariable String id) {
        return progressShareService.getProgressById(id); // ✅ aligned method
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProgressShareById(@PathVariable String id, @RequestBody ProgressShare progressShare) {
        return progressShareService.updateProgressById(id, progressShare); // ✅ aligned method
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProgressShareById(@PathVariable String id) {
        return progressShareService.deleteProgressById(id); // ✅ aligned method
    }
}
