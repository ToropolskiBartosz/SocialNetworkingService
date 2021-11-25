package com.toropolski.Socialnetworkingservice.service;

import com.toropolski.Socialnetworkingservice.dto.PostRequest;
import com.toropolski.Socialnetworkingservice.dto.PostResponse;
import com.toropolski.Socialnetworkingservice.exception.PostNotFoundException;
import com.toropolski.Socialnetworkingservice.exception.SubredditNotFoundException;
import com.toropolski.Socialnetworkingservice.mapper.PostMapper;
import com.toropolski.Socialnetworkingservice.model.Post;
import com.toropolski.Socialnetworkingservice.model.Subreddit;
import com.toropolski.Socialnetworkingservice.model.User;
import com.toropolski.Socialnetworkingservice.repository.PostRepository;
import com.toropolski.Socialnetworkingservice.repository.SubredditRepository;
import com.toropolski.Socialnetworkingservice.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import static java.util.stream.Collectors.toList;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class PostService {

    private final SubredditRepository subredditRepository;
    private final AuthService authService;
    private final PostMapper postMapper;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public void save(PostRequest postRequest) {
        Subreddit subreddit = subredditRepository.findByName(postRequest.getSubredditName())
                .orElseThrow(() -> new SubredditNotFoundException(postRequest.getSubredditName()));
        User currentUser = authService.getCurrentUser();
        postRepository.save(postMapper.map(postRequest, subreddit, currentUser));
    }

    @Transactional(readOnly = true)
    public PostResponse getPost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(id.toString()));
        return postMapper.mapToDto(post);
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getAllPosts() {
        return postRepository.findAll()
                .stream()
                .map(postMapper::mapToDto)
                .collect(toList());
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getPostsBySubreddit(Long subredditId) {
        Subreddit subreddit = subredditRepository.findById(subredditId)
                .orElseThrow(() -> new SubredditNotFoundException(subredditId.toString()));
        List<Post> posts = postRepository.findAllBySubreddit(subreddit);
        return posts.stream().map(postMapper::mapToDto).collect(toList());
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getPostsByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        return postRepository.findByUser(user)
                .stream()
                .map(postMapper::mapToDto)
                .collect(toList());
    }

}
