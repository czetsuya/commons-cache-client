package com.czetsuyatech.cache.client.persistence.entities;

import com.czetsuyatech.persistence.entities.BusinessEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@Table
@Entity(name = "country")
@SuperBuilder
public class CountryEntity extends BusinessEntity {

  @Column(name = "iso_code_3")
  private String isoCode3;
}
