package com.toropolski.Socialnetworkingservice.service;

import com.toropolski.Socialnetworkingservice.dto.TopicDto;
import com.toropolski.Socialnetworkingservice.exception.SpringRedditException;
import com.toropolski.Socialnetworkingservice.mapper.TopicMapper;
import com.toropolski.Socialnetworkingservice.model.Comment;
import com.toropolski.Socialnetworkingservice.model.Topic;
import com.toropolski.Socialnetworkingservice.repository.TopicRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
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

    @javax.transaction.Transactional
    public void editTopic(Map<String, String> fields, Long topicId) {
        Topic topicById = topicRepository.findById(topicId)
                .orElseThrow();
        fields.forEach((key,value) ->{
            // use reflection to get field k on manager and set it to value v
            Field field = ReflectionUtils.findField(Topic.class, key);
            field.setAccessible(true);
            ReflectionUtils.setField(field, topicById, value);
        });
        topicRepository.save(topicById);
    }
}
