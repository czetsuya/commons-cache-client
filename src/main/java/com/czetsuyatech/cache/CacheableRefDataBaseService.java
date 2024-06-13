package com.czetsuyatech.cache;

import com.czetsuyatech.cache.client.services.utils.QuadFunction;
import com.czetsuyatech.cache.client.services.utils.TriFunction;
import com.czetsuyatech.persistence.dtos.RefDataDTO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

public abstract class CacheableRefDataBaseService<ID extends Serializable, DTO extends RefDataDTO<ID>> implements
    CacheableRefDataService<ID, DTO> {

  @Autowired
  private CacheManager cacheManager;

  protected abstract CacheableRefDataService<ID, DTO> getProxy();

  @Override
  public List<DTO> getAll() {

    return getAll(PageRequest.of(0, Integer.MAX_VALUE, Sort.by(new Sort.Order(Sort.Direction.ASC, "id"))))
        .getContent();
  }

  @Override
  public List<DTO> getAllEnabled() {

    return getProxy().getAll().stream()
        .filter(DTO::isEnabled)
        .sorted(
            Comparator.comparing(
                    DTO::getSortOrder,
                    Comparator.nullsFirst(Integer::compareTo)
                )
                .thenComparing(DTO::getCode)
        )
        .collect(Collectors.toList());
  }

  @Override
  public Map<ID, DTO> getByIdMap() {

    return getAll().stream()
        .collect(Collectors.toMap(DTO::getId, Function.identity(), (a, b) -> b));
  }

  @Override
  public Map<String, DTO> getByCodeMap() {

    return getAllEnabled().stream()
        .collect(Collectors.toMap(DTO::getCode, Function.identity(), (a, b) -> b));
  }

  @Override
  public DTO getById(ID id) {

    return getByIdMap().get(id);
  }

  @Override
  public DTO getByCode(String code) {

    return getByCodeMap().get(code);
  }

  public Page<DTO> getAll(Pageable pageable) {

    return new PageImpl<>(Collections.emptyList(), pageable, 0);
  }

  protected Page<DTO> getAll(Pageable pageable,
      TriFunction<Integer, Integer, List<String>, ResponseEntity<List<DTO>>> getAllSupplier) {

    final List<String> sortArr = new ArrayList<>();

    if (pageable == null) {
      pageable = PageRequest.of(0, Integer.MAX_VALUE, Sort.Direction.ASC, "id");
    }

    pageable.getSort().forEach(
        order -> sortArr.add(order.getProperty() + "," + (order.getDirection().isAscending() ? "asc" : "desc")));

    ResponseEntity<List<DTO>> response = getAllSupplier.apply(pageable.getPageNumber(), pageable.getPageSize(),
        sortArr);

    return new PageImpl<>(response.getBody(), pageable, extractTotalCountFromHeaders(response));
  }

  protected Page<DTO> getAll(Pageable pageable, String code,
      QuadFunction<Integer, Integer, List<String>, String, ResponseEntity<List<DTO>>> getAllSupplier) {

    final List<String> sortArr = new ArrayList<>();

    if (pageable == null) {
      pageable = PageRequest.of(0, Integer.MAX_VALUE, Sort.Direction.ASC, "id");
    }

    pageable.getSort().forEach(
        order -> sortArr.add(order.getProperty() + "," + (order.getDirection().isAscending() ? "asc" : "desc")));

    ResponseEntity<List<DTO>> response = getAllSupplier.apply(pageable.getPageNumber(), pageable.getPageSize(), sortArr,
        code);

    return new PageImpl<>(response.getBody(), pageable, extractTotalCountFromHeaders(response));
  }

  public DTO save(DTO dto) {

    // invalidate cache
    return null;
  }

  public DTO update(ID id, DTO dto) {

    // invalidate cache
    return dto;
  }

  public void delete(ID id) {

    // invalidate cache
  }

  protected void invalidateCache(String cacheName) {

    final Cache cache = cacheManager.getCache(cacheName);

    if (cache != null) {
      cache.clear();
    }
  }

  public static <T> long extractTotalCountFromHeaders(ResponseEntity<T> responseEntity) {

    long totalCount = 0;
    String totalCountValue;
    HttpHeaders headers = responseEntity.getHeaders();

    totalCountValue = headers.getFirst("X-Total-Count");
    if (totalCountValue != null) {
      return Long.parseLong(totalCountValue);
    }

    return totalCount;
  }
}
