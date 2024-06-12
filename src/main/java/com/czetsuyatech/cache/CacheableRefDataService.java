package com.czetsuyatech.cache;

import com.czetsuyatech.cache.dto.RefDataDTO;
import java.util.List;
import java.util.Map;

public interface CacheableRefDataService<ID, DTO extends RefDataDTO> {

  List<DTO> getAll();

  List<DTO> getAllEnabled();

  Map<ID, DTO> getByIdMap();

  Map<String, DTO> getByCodeMap();

  DTO getById(ID id);

  DTO getByCode(String code);
}
