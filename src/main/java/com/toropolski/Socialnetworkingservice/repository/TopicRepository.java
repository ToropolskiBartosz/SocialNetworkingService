package com.toropolski.Socialnetworkingservice.repository;

import com.toropolski.Socialnetworkingservice.model.Topic;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {

    Optional<Topic> findByName(String subredditName);

    @Query("Select p from Topic p")
    List<Topic> findAllTopic(Pageable page);
}
