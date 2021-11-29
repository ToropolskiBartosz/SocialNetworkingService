package com.toropolski.Socialnetworkingservice.controller;

import com.toropolski.Socialnetworkingservice.dto.CommentsDto;
import com.toropolski.Socialnetworkingservice.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@AllArgsConstructor
public class CommentsController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<Void> createComment(@RequestBody CommentsDto commentsDto){
        commentService.save(commentsDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<List<CommentsDto>> getAllComments(@RequestParam int nrPage, Sort.Direction sort){
        int correctNrPage = nrPage>0 ? nrPage : 1;
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(commentService.getAll(correctNrPage - 1, sort));
    }

    @GetMapping("/byPostId/{postId}")
    public ResponseEntity<List<CommentsDto>> getAllCommentsForPost(@PathVariable("postId") Long postId,
                                                                   @RequestParam int nrPage) {
        int correctNrPage = nrPage>0 ? nrPage : 1;
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(commentService.getCommentByPost(postId, correctNrPage - 1));
    }

    @GetMapping("/byUser/{userName}")
    public ResponseEntity<List<CommentsDto>> getAllCommentsByUser(@PathVariable("userName") String userName,
                                                                  @RequestParam int nrPage) {
        int correctNrPage = nrPage>0 ? nrPage : 1;
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(commentService.getCommentsByUser(userName,correctNrPage));
    }
}
