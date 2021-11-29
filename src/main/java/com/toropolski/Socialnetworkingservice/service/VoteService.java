package com.toropolski.Socialnetworkingservice.service;

import com.toropolski.Socialnetworkingservice.dto.VoteDto;
import com.toropolski.Socialnetworkingservice.exception.PostNotFoundException;
import com.toropolski.Socialnetworkingservice.exception.SpringRedditException;
import com.toropolski.Socialnetworkingservice.mapper.VoteMapper;
import com.toropolski.Socialnetworkingservice.model.Post;
import com.toropolski.Socialnetworkingservice.model.Vote;
import com.toropolski.Socialnetworkingservice.model.enumerate.VoteType;
import com.toropolski.Socialnetworkingservice.repository.PostRepository;
import com.toropolski.Socialnetworkingservice.repository.VoteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class VoteService {
    private final VoteRepository voteRepository;
    private final PostRepository postRepository;
    private final AuthService authService;
    private final VoteMapper voteMapper;

    public VoteDto vote(VoteDto voteDto){
        Post post = postRepository.findById(voteDto.getPostId())
                .orElseThrow(() -> new PostNotFoundException("Not found post by id: "
                        + voteDto.getPostId().toString()));
        Optional<Vote> voteByPostAndUser = voteRepository
                .findTopByPostAndUserOrderByVoteIdDesc(post, authService.getCurrentUser());

        if(voteByPostAndUser.isPresent() && voteByPostAndUser.get().getVoteType()
                        .equals(voteDto.getVoteType())){
            throw new SpringRedditException("You have already "
                    + voteDto.getVoteType()
                    + "'d for this post");
        }

        int countVote = VoteType.UPVOTE.equals(voteDto.getVoteType()) ? 1 : -1;
        post.setVoteCount(post.getVoteCount() + countVote);

        Vote vote = voteMapper.mapDtoToVote(voteDto, post, authService.getCurrentUser());

        postRepository.save(post);
        voteRepository.save(vote);

        return voteDto;
    }

}
