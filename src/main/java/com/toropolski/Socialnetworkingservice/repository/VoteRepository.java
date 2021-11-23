package com.toropolski.Socialnetworkingservice.repository;

import com.toropolski.Socialnetworkingservice.model.Post;
import com.toropolski.Socialnetworkingservice.model.User;
import com.toropolski.Socialnetworkingservice.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {
    Optional<Vote> findTopByPostAndUserOrderByVoteIdDesc(Post post, User currentUser);
}
