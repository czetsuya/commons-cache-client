package com.czetsuyatech.cache.client.mappers;

import com.czetsuyatech.cache.client.persistence.entities.CountryEntity;
import com.czetsuyatech.cache.client.services.dtos.CountryDTO;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants.ComponentModel;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = ComponentModel.SPRING,
    unmappedSourcePolicy = ReportingPolicy.IGNORE,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    collectionMappingStrategy = CollectionMappingStrategy.TARGET_IMMUTABLE
)
public interface CountryMapper {

  CountryDTO asDto(CountryEntity entity);

  CountryEntity asEntity(CountryDTO dto);

  void asEntity(CountryDTO countryDTO, @MappingTarget CountryEntity oldEntity);
}
