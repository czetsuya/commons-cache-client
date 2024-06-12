package com.czetsuyatech.cache.dto;

import java.io.Serializable;

public class BaseRefDataDTO<ID extends Serializable> implements RefDataDTO<ID> {

  private ID id;
  private String code;
  private String description;

  public BaseRefDataDTO(BaseRefDataDTO<ID> other) {

    this.id = other.id;
    this.code = other.code;
    this.description = other.description;
  }

  @Override
  public RefDataDTO<ID> deepClone() {
    return new BaseRefDataDTO<>(this);
  }

  @Override
  public ID getId() {
    return id;
  }

  @Override
  public void setId(ID id) {
    this.id = id;
  }

  @Override
  public String getCode() {
    return code;
  }

  @Override
  public void setCode(String code) {
    this.code = code;
  }
}
