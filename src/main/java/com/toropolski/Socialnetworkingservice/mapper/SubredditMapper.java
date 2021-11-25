package com.toropolski.Socialnetworkingservice.mapper;

import com.toropolski.Socialnetworkingservice.dto.SubredditDto;
import com.toropolski.Socialnetworkingservice.model.Post;
import com.toropolski.Socialnetworkingservice.model.Subreddit;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


import java.util.List;

@Mapper(componentModel = "spring")
public interface SubredditMapper {

    @Mapping(target = "numberOfPosts", expression = "java(mapPosts(subreddit.getPosts()))")
    SubredditDto mapSubredditToDto(Subreddit subreddit);

    default Integer mapPosts(List<Post> numberOfPosts){return numberOfPosts.size();}

    @InheritInverseConfiguration
    @Mapping(target = "posts", ignore = true)
    Subreddit mapDtoToSubreddit(SubredditDto subreddit);
}
