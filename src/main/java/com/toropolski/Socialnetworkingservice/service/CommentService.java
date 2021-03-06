package com.toropolski.Socialnetworkingservice.service;

import com.toropolski.Socialnetworkingservice.dto.CommentsDto;
import com.toropolski.Socialnetworkingservice.dto.CommentsRequest;
import com.toropolski.Socialnetworkingservice.exception.PostNotFoundException;
import com.toropolski.Socialnetworkingservice.exception.SpringRedditException;
import com.toropolski.Socialnetworkingservice.mapper.CommentMapper;
import com.toropolski.Socialnetworkingservice.model.Comment;
import com.toropolski.Socialnetworkingservice.model.NotificationEmail;
import com.toropolski.Socialnetworkingservice.model.Post;
import com.toropolski.Socialnetworkingservice.model.User;
import com.toropolski.Socialnetworkingservice.repository.CommentRepository;
import com.toropolski.Socialnetworkingservice.repository.PostRepository;
import com.toropolski.Socialnetworkingservice.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CommentService {

    private static final String POST_URL = "";
    public static final int PAGE_SIZE = 2;

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final AuthService authService;
    private final CommentMapper commentMapper;
    private final MailContentBuilder mailContentBuilder;
    private final MailService mailService;

    public CommentsDto save(CommentsRequest commentsRequest){

        Post post = postRepository.findById(commentsRequest.getPostId())
                .orElseThrow(() -> new PostNotFoundException(commentsRequest.getPostId().toString()));
        Comment comment = commentMapper.map(commentsRequest, post, authService.getCurrentUser());
        commentRepository.save(comment);

        String message = mailContentBuilder.build(post.getUser().getUsername() + " posted a comment on your post." + POST_URL);
        sendCommentNotification(message, post.getUser());

        return commentMapper.mapToDto(comment);
    }

    private void sendCommentNotification(String message, User user) {
        mailService.sendMail(new NotificationEmail(user.getUsername() + " Commented on your post", user.getEmail(), message));
    }

    @Transactional(readOnly = true)
    public List<CommentsDto> getAll(int nrPage, Sort.Direction sort) {
        return commentRepository.findAllComments(PageRequest.of(nrPage, PAGE_SIZE, Sort.by(sort,"createdDate")))
                .stream()
                .map(commentMapper::mapToDto)
                .collect(Collectors.toList());
    }

    public List<CommentsDto> getCommentByPost(Long postId, int nrPage) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("Not found post by id" + postId.toString()));
        return commentRepository.findByPostPage(post, PageRequest.of(nrPage, PAGE_SIZE))
                .stream()
                .map(commentMapper::mapToDto)
                .collect(Collectors.toList());
    }

    public List<CommentsDto> getCommentsByUser(String userName,int nrPage) {
        User user = userRepository.findByUsername(userName)
                .orElseThrow(() -> new UsernameNotFoundException("Not found user " + userName));
        return commentRepository.findAllByUserPage(user,PageRequest.of(nrPage, PAGE_SIZE))
                .stream()
                .map(commentMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @javax.transaction.Transactional
    public void editComment(Map<String, String> fields, long commentId){
        Comment commentById = commentRepository.findById(commentId)
                .orElseThrow();
        fields.forEach((key,value) ->{
            // use reflection to get field k on manager and set it to value v
            Field field = ReflectionUtils.findField(Comment.class, key);
            field.setAccessible(true);
            ReflectionUtils.setField(field, commentById, value);
        });
        commentRepository.save(commentById);
    }

    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }
    
}
