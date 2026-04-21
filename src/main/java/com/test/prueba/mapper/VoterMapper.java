package com.test.prueba.mapper;

import com.test.prueba.model.Voter;
import com.test.prueba.model.dto.VoterResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VoterMapper {

    VoterResponse toResponse(Voter voter);
}
