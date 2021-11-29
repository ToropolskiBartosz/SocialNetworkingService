package com.toropolski.Socialnetworkingservice.service;

import com.toropolski.Socialnetworkingservice.dto.PostRequest;
import com.toropolski.Socialnetworkingservice.dto.PostResponse;
import com.toropolski.Socialnetworkingservice.exception.PostNotFoundException;
import com.toropolski.Socialnetworkingservice.exception.SubredditNotFoundException;
import com.toropolski.Socialnetworkingservice.mapper.PostMapper;
import com.toropolski.Socialnetworkingservice.model.Comment;
import com.toropolski.Socialnetworkingservice.model.Post;
import com.toropolski.Socialnetworkingservice.model.Topic;
import com.toropolski.Socialnetworkingservice.model.User;
import com.toropolski.Socialnetworkingservice.repository.PostRepository;
import com.toropolski.Socialnetworkingservice.repository.TopicRepository;
import com.toropolski.Socialnetworkingservice.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import static java.util.stream.Collectors.toList;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class PostService {

    public static final int PAGE_SIZE = 2;
    private final TopicRepository topicRepository;
    private final AuthService authService;
    private final PostMapper postMapper;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostRequest save(PostRequest postRequest) {
        Topic topic = topicRepository.findByName(postRequest.getTopicName())
                .orElseThrow(() -> new SubredditNotFoundException(postRequest.getTopicName()));
        User currentUser = authService.getCurrentUser();
        postRepository.save(postMapper.map(postRequest, topic, currentUser));

        return postRequest;
    }

    @Transactional(readOnly = true)
    public PostResponse getPost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(id.toString()));
        return postMapper.mapToDto(post);
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getAllPosts(int nrPage, Sort.Direction sort) {
        return postRepository.findAllPosts(PageRequest.of(nrPage, PAGE_SIZE, Sort.by(sort, "createdDate")))
                .stream()
                .map(postMapper::mapToDto)
                .collect(toList());
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getPostsBySubreddit(Long subredditId, int nrPage) {
        Topic topic = topicRepository.findById(subredditId)
                .orElseThrow(() -> new SubredditNotFoundException(subredditId.toString()));
        List<Post> posts = postRepository.findAllByTopicPage(topic, PageRequest.of(nrPage, PAGE_SIZE));
        return posts.stream().map(postMapper::mapToDto).collect(toList());
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getPostsByUsername(String username, int nrPage) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        return postRepository.findByUserPage(user, PageRequest.of(nrPage,PAGE_SIZE))
                .stream()
                .map(postMapper::mapToDto)
                .collect(toList());
    }

    @javax.transaction.Transactional
    public void editPost(Map<String, String> fields, long postId){
        Post postById = postRepository.findById(postId)
                .orElseThrow();
        fields.forEach((key,value) ->{
            // use reflection to get field k on manager and set it to value v
            Field field = ReflectionUtils.findField(Post.class, key);
            field.setAccessible(true);
            ReflectionUtils.setField(field, postById, value);
        });
        postRepository.save(postById);
    }

    public void deletePost(Long postId) {
        postRepository.deleteById(postId);
    }
}
