package com.toropolski.Socialnetworkingservice.mapper;

import com.toropolski.Socialnetworkingservice.dto.VoteDto;
import com.toropolski.Socialnetworkingservice.model.Post;
import com.toropolski.Socialnetworkingservice.model.User;
import com.toropolski.Socialnetworkingservice.model.Vote;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface VoteMapper {

    @Mapping(target = "voteId", ignore = true)
    @Mapping(target = "post", source = "post")
    @Mapping(target = "user", source = "user")
    Vote mapDtoToVote(VoteDto voteDto, Post post, User user);
}
