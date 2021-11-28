package com.toropolski.Socialnetworkingservice.repository;

import com.toropolski.Socialnetworkingservice.model.Comment;
import com.toropolski.Socialnetworkingservice.model.Post;
import com.toropolski.Socialnetworkingservice.model.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.net.CookieHandler;
import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPost(Post post);

    @Query("Select c from Comment c where post = :post")
    List<Comment> findByPostPage(@Param("post") Post post, Pageable page);

    List<Comment> findAllByUser(User user);

    @Query("Select c from Comment c where user = :user")
    List<Comment> findAllByUserPage(@Param("user") User user, Pageable page);

    @Query("Select c from Comment c")
    List<Comment> findAllComments(Pageable page);
}
