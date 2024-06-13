package com.czetsuyatech.cache.client.services.dtos;

import com.czetsuyatech.persistence.dtos.BaseRefDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CountryDTO extends BaseRefDTO<Long> {

  private String isoCode3;

  public CountryDTO(BaseRefDTO<Long> other) {
    super(other);
  }
}
