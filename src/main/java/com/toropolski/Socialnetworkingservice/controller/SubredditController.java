package com.toropolski.Socialnetworkingservice.controller;

import com.toropolski.Socialnetworkingservice.dto.SubredditDto;
import com.toropolski.Socialnetworkingservice.service.SubredditService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subreddit")
@AllArgsConstructor
@Slf4j
public class SubredditController {

    SubredditService subredditService;

    @PostMapping
    public ResponseEntity<SubredditDto> createSubreddit(@RequestBody SubredditDto subredditDto){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(subredditService.save(subredditDto));
    }

    @GetMapping
    public ResponseEntity<List<SubredditDto>> getAllSubreddits(){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(subredditService.getAll());

    }

    @GetMapping("/{id}")
    public ResponseEntity<SubredditDto> getSubreddits(@PathVariable Long id){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(subredditService.getSubreddit(id));

    }
}
