package com.test.prueba.mapper;

import com.test.prueba.model.Vote;
import com.test.prueba.model.dto.VoteResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface VoteMapper {

    @Mapping(target = "voterId", source = "voter.id")
    @Mapping(target = "candidateId", source = "candidate.id")
    VoteResponse toResponse(Vote vote);
}
