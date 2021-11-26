package com.toropolski.Socialnetworkingservice.model.enumerate;

import com.toropolski.Socialnetworkingservice.exception.SpringRedditException;
import lombok.Data;
import lombok.Getter;

import java.util.Arrays;

public enum VoteType {
    UPVOTE(1), DOWNVOTE(-1);
    private Integer direction;
    VoteType(Integer direction) {
    }

    public static VoteType lookup(Integer direction) {
        return Arrays.stream(VoteType.values())
                .filter(value -> value.getDirection().equals(direction))
                .findAny()
                .orElseThrow(() -> new SpringRedditException("Vote not found"));
    }

    public Integer getDirection() {
        return direction;
    }

}
