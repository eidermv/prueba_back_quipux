package com.example.back.lista.infrastructure.rest.mapper;

import com.example.back.lista.infrastructure.db.entity.CancionEnt;
import com.example.back.lista.infrastructure.rest.dto.request.CancionDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface CancionEntMapper {
    CancionEnt toEntity(CancionDto cancionDto);

    CancionDto toDto(CancionEnt cancionEnt);

}
