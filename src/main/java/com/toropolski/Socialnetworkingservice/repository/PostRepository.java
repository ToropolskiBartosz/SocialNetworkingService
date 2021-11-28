package com.toropolski.Socialnetworkingservice.repository;

import com.toropolski.Socialnetworkingservice.model.Post;
import com.toropolski.Socialnetworkingservice.model.Topic;
import com.toropolski.Socialnetworkingservice.model.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByTopic(Topic topic);

    @Query("Select p from Post p where topic = :topic")
    List<Post> findAllByTopicPage(@Param("topic") Topic topic, Pageable page);

    List<Post> findByUser(User user);

    @Query("Select p from Post p where user = :user")
    List<Post> findByUserPage(@Param("user") User user, Pageable page);

    @Query("Select p from Post p")
    List<Post> findAllPosts(Pageable page);
}
