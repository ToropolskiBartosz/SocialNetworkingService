package com.toropolski.Socialnetworkingservice.controller;

import com.toropolski.Socialnetworkingservice.dto.TopicDto;
import com.toropolski.Socialnetworkingservice.model.Topic;
import com.toropolski.Socialnetworkingservice.repository.TopicRepository;
import com.toropolski.Socialnetworkingservice.service.TopicService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/topics")
@AllArgsConstructor
@Slf4j
public class TopicController {

    private final TopicService topicService;
    private final TopicRepository topicRepository;

    @PostMapping
    public ResponseEntity<TopicDto> createSubreddit(@RequestBody TopicDto topicDto){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(topicService.save(topicDto));
    }

    @GetMapping
    public ResponseEntity<List<TopicDto>> getAllSubreddits(@RequestParam int nrPage){
        int correctNrPage = nrPage > 0 ? nrPage : 1;
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(topicService.getAll(correctNrPage-1));

    }

    @GetMapping("/{id}")
    public ResponseEntity<TopicDto> getSubreddits(@PathVariable Long id){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(topicService.getSubreddit(id));

    }

    @PatchMapping("/editTopic/{topicId}")
    public void editComment(@PathVariable("topicId") Long topicId,
                            @RequestBody Map<String,String> fields){

        topicService.editTopic(fields,topicId);
    }

    @GetMapping("/withPost")
    public void getAllTopicWithPost(){
        throw new IllegalArgumentException("This method is empty");
    }
}
