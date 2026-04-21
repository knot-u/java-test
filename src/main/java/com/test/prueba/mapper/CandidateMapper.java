package com.test.prueba.mapper;

import com.test.prueba.model.Candidate;
import com.test.prueba.model.dto.CandidateResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CandidateMapper {

    CandidateResponse toResponse(Candidate candidate);
}
