package com.czetsuyatech.cache.client.persistence.repositories;

import com.czetsuyatech.cache.client.persistence.entities.CountryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends JpaRepository<CountryEntity, Long> {

}
