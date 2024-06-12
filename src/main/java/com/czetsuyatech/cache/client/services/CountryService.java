package com.czetsuyatech.cache.client.services;

import com.czetsuyatech.cache.CacheableRefDataBaseService;
import com.czetsuyatech.cache.CacheableRefDataService;
import com.czetsuyatech.cache.client.mappers.CountryMapper;
import com.czetsuyatech.cache.client.persistence.repositories.CountryRepository;
import com.czetsuyatech.cache.client.services.dtos.CountryDTO;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CountryService extends CacheableRefDataBaseService<Long, CountryDTO> {

  public static final String COUNTRY_CACHE_NAME = "country";
  private final CountryRepository countryRepository;
  private final CountryMapper countryMapper;

  @Cacheable(COUNTRY_CACHE_NAME)
  @Override
  public List<CountryDTO> getAll() {

    return super.getAll();
  }

  @Override
  public CountryDTO save(CountryDTO countryDTO) {

    log.debug("Saving country from DTO={}", countryDTO);
    var newEntity = countryRepository.save(countryMapper.asEntity(countryDTO));

    invalidateCache(COUNTRY_CACHE_NAME);

    return countryMapper.asDto(newEntity);
  }

  @Override
  protected CacheableRefDataService<Long, CountryDTO> getProxy() {
    return this;
  }
}
