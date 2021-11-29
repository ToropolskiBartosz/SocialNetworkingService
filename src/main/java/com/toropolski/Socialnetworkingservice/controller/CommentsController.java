package com.toropolski.Socialnetworkingservice.controller;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.toropolski.Socialnetworkingservice.dto.CommentsDto;
import com.toropolski.Socialnetworkingservice.dto.CommentsRequest;
import com.toropolski.Socialnetworkingservice.model.Comment;
import com.toropolski.Socialnetworkingservice.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/comments")
@AllArgsConstructor
public class CommentsController {

    private final CommentService commentService;
    private final ObjectMapper objectMapper;

    @PostMapping
    public ResponseEntity<CommentsDto> createComment(@RequestBody CommentsRequest commentsRequest){

        return  ResponseEntity
                .status(HttpStatus.CREATED)
                .body(commentService.save(commentsRequest));
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

    @PatchMapping("/editComment/{commentId}")
    public void editComment(@PathVariable("commentId") Long commentId,
                            @RequestBody Map<String,String> fields){

        commentService.editComment(fields,commentId);
    }
    @DeleteMapping("/deleteComment/{commentId}")
    public void deleteComment(@PathVariable("commentId") Long commentId){
        commentService.deleteComment(commentId);
    }

}
