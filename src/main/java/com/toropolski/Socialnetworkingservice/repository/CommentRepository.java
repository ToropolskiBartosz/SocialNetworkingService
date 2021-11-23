package com.toropolski.Socialnetworkingservice.repository;

import com.toropolski.Socialnetworkingservice.model.Comment;
import com.toropolski.Socialnetworkingservice.model.Post;
import com.toropolski.Socialnetworkingservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPost(Post post);

    List<Comment> findAllByUser(User user);
}
