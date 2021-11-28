package com.toropolski.Socialnetworkingservice.service;

import com.toropolski.Socialnetworkingservice.dto.TopicDto;
import com.toropolski.Socialnetworkingservice.exception.SpringRedditException;
import com.toropolski.Socialnetworkingservice.mapper.TopicMapper;
import com.toropolski.Socialnetworkingservice.model.Topic;
import com.toropolski.Socialnetworkingservice.repository.TopicRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class TopicService {

    public static final int PAGE_SIZE = 2;
    private final TopicRepository topicRepository;
    private final TopicMapper topicMapper;
    private final AuthService authService;

    public TopicDto save(TopicDto topicDto){
        Topic save = topicRepository.save(topicMapper.mapDtoToSubreddit(topicDto,authService.getCurrentUser()));
        topicDto.setId(save.getId());
        return topicDto;
    }

    @Transactional(readOnly = true)
    public List<TopicDto> getAll(int nrPage) {
        return topicRepository.findAllTopic(PageRequest.of(nrPage, PAGE_SIZE))
                .stream()
                .map(topicMapper::mapSubredditToDto)
                .collect(Collectors.toList());
    }

    public TopicDto getSubreddit(Long id) {
        Topic topic = topicRepository.findById(id)
                .orElseThrow(() -> new SpringRedditException("No topic found with id: " + id));
        return topicMapper.mapSubredditToDto(topic);
    }
}
