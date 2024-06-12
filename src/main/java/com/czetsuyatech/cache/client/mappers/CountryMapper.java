package com.czetsuyatech.cache.client.mappers;

import com.czetsuyatech.cache.client.persistence.entities.CountryEntity;
import com.czetsuyatech.cache.client.services.dtos.CountryDTO;
import org.mapstruct.Mapper;

@Mapper
public interface CountryMapper {

  CountryDTO asDto(CountryEntity entity);

  CountryEntity asEntity(CountryDTO dto);
}
