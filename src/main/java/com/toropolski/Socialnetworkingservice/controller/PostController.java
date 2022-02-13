package com.toropolski.Socialnetworkingservice.controller;

import com.toropolski.Socialnetworkingservice.dto.PostRequest;
import com.toropolski.Socialnetworkingservice.dto.PostResponse;
import com.toropolski.Socialnetworkingservice.service.PostService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/posts")
@AllArgsConstructor
@Slf4j
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<PostRequest> createPost(@RequestBody PostRequest postRequest) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(postService.save(postRequest));
    }

    @GetMapping
    public ResponseEntity<List<PostResponse>> getAllPosts(@RequestParam int nrPage, Sort.Direction sort) {
        int correctNrPage = nrPage>0 ? nrPage : 1;
        return ResponseEntity
                .status(HttpStatus.OK).body(postService.getAllPosts(nrPage -1, sort));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPost(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK).body(postService.getPost(id));
    }

    @GetMapping("by-topic/{id}")
    public ResponseEntity<List<PostResponse>> getPostsBySubreddit(@RequestParam int nrPage,
                                                                  @PathVariable Long id) {
        int correctNrPage = nrPage>0 ? nrPage : 1;
        return ResponseEntity
                .status(HttpStatus.OK).body(postService.getPostsBySubreddit(id, correctNrPage - 1));
    }

    @GetMapping("by-user/{name}")
    public ResponseEntity<List<PostResponse>> getPostsByUsername(@RequestParam int nrPage,
                                                                 @PathVariable String username) {
        int correctNrPage = nrPage>0 ? nrPage:1;
        return ResponseEntity
                .status(HttpStatus.OK).body(postService.getPostsByUsername(username, correctNrPage - 1));
    }

    @PatchMapping("/editPost/{postId}")
    public void editPost(@PathVariable("postId") Long postId,
                            @RequestBody Map<String,String> fields){
        postService.editPost(fields,postId);
    }

    @DeleteMapping("/deletePost/{postId}")
    public void deletePost(@PathVariable("postId") Long postId){
        postService.deletePost(postId);
    }
}
