package com.czetsuyatech.cache;

import com.czetsuyatech.cache.dto.RefDataDTO;
import java.io.Serializable;
import java.util.Collections;
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

  public Page<DTO> getAll(Pageable pageable) {

    return new PageImpl<>(Collections.emptyList(), pageable, 0);
  }

  @Override
  public List<DTO> getAllEnabled() {

//    return getProxy().getAll().stream()
//        .filter( DTO::isEnabled )
//        .sorted(
//            Comparator.comparing(
//                    D::getSortOrder,
//                    Comparator.nullsFirst( Integer::compareTo )
//                )
//                .thenComparing( D::getCode )
//        )
//        .collect( Collectors.toList() );
    return null;
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

  public DTO save(DTO dto) {

    // invalidate cache
    return null;
  }

  public DTO patch(ID id, DTO dto) {

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
}
