package com.example.pwc.controller;

import com.example.pwc.dto.GetRouteResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RoutingControllerIntegrationTest {

  @LocalServerPort private int port;

  @Autowired private TestRestTemplate restTemplate;

  @Test
  void testSummaryStatistics_successful() {
    ResponseEntity<GetRouteResponse> response =
        this.restTemplate.getForEntity(
            "http://localhost:" + port + "/routing/CZE/ITA", GetRouteResponse.class);

    Assertions.assertTrue(response.getStatusCode().is2xxSuccessful());
  }

  @Test
  void testSummaryStatisticsForDepartment_routeNotFound() {
    ResponseEntity<GetRouteResponse> response =
        this.restTemplate.getForEntity(
            "http://localhost:" + port + "/routing/CZE/JPN", GetRouteResponse.class);

    Assertions.assertTrue(response.getStatusCode().is4xxClientError());
  }

  @Test
  void testSummaryStatisticsForDepartment_countryCodeNotFound() {
    ResponseEntity<GetRouteResponse> response =
        this.restTemplate.getForEntity(
            "http://localhost:" + port + "/routing/XXX/YYY", GetRouteResponse.class);

    Assertions.assertTrue(response.getStatusCode().is4xxClientError());
  }
}
