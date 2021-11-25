package com.toropolski.Socialnetworkingservice.service;

import com.toropolski.Socialnetworkingservice.dto.SubredditDto;
import com.toropolski.Socialnetworkingservice.exception.SpringRedditException;
import com.toropolski.Socialnetworkingservice.mapper.SubredditMapper;
import com.toropolski.Socialnetworkingservice.model.Subreddit;
import com.toropolski.Socialnetworkingservice.repository.SubredditRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class SubredditService {

    private final SubredditRepository subredditRepository;
    private final SubredditMapper subredditMapper;

    public SubredditDto save(SubredditDto subredditDto){
        Subreddit save = subredditRepository.save(subredditMapper.mapDtoToSubreddit(subredditDto));
        subredditDto.setId(save.getId());
        return subredditDto;
    }

    public List<SubredditDto> getAll() {
        return subredditRepository.findAll()
                .stream()
                .map(subredditMapper::mapSubredditToDto)
                .collect(Collectors.toList());
    }

    public SubredditDto getSubreddit(Long id) {
        Subreddit subreddit = subredditRepository.findById(id)
                .orElseThrow(() -> new SpringRedditException("No subreddit found with id: " + id));
        return subredditMapper.mapSubredditToDto(subreddit);
    }
}
