package com.toropolski.Socialnetworkingservice.controller;

import com.toropolski.Socialnetworkingservice.dto.VoteDto;
import com.toropolski.Socialnetworkingservice.service.VoteService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/votes")
@AllArgsConstructor
public class VoteController {

    private final VoteService voteService;

    @PostMapping
    public ResponseEntity<VoteDto> vote(@RequestBody VoteDto voteDto){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(voteService.vote(voteDto));
    }
}
