package com.czetsuyatech.cache.client.web.controllers;

import com.czetsuyatech.cache.client.services.CountryService;
import com.czetsuyatech.cache.client.services.dtos.CountryDTO;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/countries")
public class CountryController {

  private final CountryService countryService;

  @PostMapping
  public ResponseEntity<CountryDTO> create(@RequestBody CountryDTO countryDTO) {

    log.debug("Request to create country={}", countryDTO);

    return ResponseEntity.ok(countryService.save(countryDTO));
  }

  @PutMapping("/{countryId}")
  public ResponseEntity<CountryDTO> update(@PathVariable Long id, @RequestBody CountryDTO countryDTO) {

    log.debug("Request to update country with id={}, dto={}", id, countryDTO);

    return ResponseEntity.ok(countryService.update(id, countryDTO));
  }

  @GetMapping("/{countryCode}")
  public ResponseEntity<CountryDTO> getByCode(@PathVariable String countryCode) {

    log.debug("Request to get country with code={}", countryCode);

    return ResponseEntity.ok(countryService.getByCode(countryCode));
  }

  @GetMapping
  public ResponseEntity<List<CountryDTO>> list() {

    log.debug("Request to list countries");

    return ResponseEntity.ok(countryService.getAll());
  }
}
