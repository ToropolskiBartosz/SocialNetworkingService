package com.toropolski.Socialnetworkingservice.dto;

import com.toropolski.Socialnetworkingservice.model.enumerate.VoteType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoteDto {
    private VoteType voteType;
    private Long postId;

}
