package com.czetsuyatech.cache.dto;

import com.czetsuyatech.cache.Code;
import com.czetsuyatech.cache.Id;
import java.io.Serializable;

public interface RefDataDTO<T extends Serializable> extends Id<T>, Code, Serializable {

  RefDataDTO<T> deepClone();
}
