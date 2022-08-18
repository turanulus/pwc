package com.example.pwc.config;

import com.example.pwc.model.Country;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.nio.charset.Charset.defaultCharset;

@Slf4j
@Configuration
public class FileConfig {

  @Bean(name = "countryMapping")
  public Map<String, List<String>> getCountryMapping() {

    Map<String, List<String>> countryMapping = new HashMap<>();

    ObjectMapper objectMapper = new ObjectMapper();
    try {
      String countryJsonString =
          StreamUtils.copyToString(
              new ClassPathResource("countries.json").getInputStream(), defaultCharset());
      List<Country> countryList =
          objectMapper.readValue(countryJsonString, new TypeReference<List<Country>>() {});

      countryMapping =
          countryList.stream()
              .collect(Collectors.toMap(Country::getCountryCode, Country::getBorderList));

    } catch (IOException e) {
      log.error("Internal error: {}", e);
    }

    return countryMapping;
  }
}
