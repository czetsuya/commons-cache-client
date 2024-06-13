package com.czetsuyatech.cache.client.services;

import com.czetsuyatech.cache.CacheableRefDataBaseService;
import com.czetsuyatech.cache.CacheableRefDataService;
import com.czetsuyatech.cache.client.mappers.CountryMapper;
import com.czetsuyatech.cache.client.persistence.repositories.CountryRepository;
import com.czetsuyatech.cache.client.services.dtos.CountryDTO;
import jakarta.annotation.Resource;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CountryService extends CacheableRefDataBaseService<Long, CountryDTO> {

  public static final String COUNTRY_CACHE_NAME = "CACHE_COUNTRY";
  private final CountryRepository countryRepository;
  private final CountryMapper countryMapper;
  @Lazy
  @Resource(name = "countryService")
  private CountryService proxy;

  @Cacheable(COUNTRY_CACHE_NAME)
  @Override
  public List<CountryDTO> getAll() {

    log.debug("List all countries");

    return countryRepository.findAll().stream()
        .map(countryMapper::asDto)
        .toList();
  }

  @Override
  public CountryDTO save(CountryDTO countryDTO) {

    log.debug("Create country from DTO={}", countryDTO);

    var newEntity = countryRepository.save(countryMapper.asEntity(countryDTO));

    invalidateCache(COUNTRY_CACHE_NAME);

    return countryMapper.asDto(newEntity);
  }

  @Override
  public CountryDTO update(Long id, CountryDTO countryDTO) {

    log.debug("Update country with id={}, dto={}", id, countryDTO);

    var oldEntity = countryRepository.findById(id)
        .map(e -> {
          countryMapper.asEntity(countryDTO, e);
          return e;
        })
        .orElseThrow(() -> new RuntimeException("NOT_FOUND"));

    countryRepository.save(oldEntity);

    invalidateCache(COUNTRY_CACHE_NAME);

    return countryMapper.asDto(oldEntity);
  }

  @Override
  public void delete(Long id) {

    log.debug("Delete country with id={}", id);

    countryRepository.deleteById(id);

    invalidateCache(COUNTRY_CACHE_NAME);
  }

  @Override
  protected CacheableRefDataService<Long, CountryDTO> getProxy() {
    return proxy;
  }
}
