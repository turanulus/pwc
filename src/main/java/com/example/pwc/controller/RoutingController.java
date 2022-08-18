package com.example.pwc.controller;

import com.example.pwc.dto.GetRouteResponse;
import com.example.pwc.service.RoutingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/routing")
public class RoutingController {

  private final RoutingService routingService;

  @GetMapping("/{origin}/{destination}")
  public ResponseEntity<GetRouteResponse> getRoute(
      @PathVariable(name = "origin") String origin,
      @PathVariable(name = "destination") String destination) {
    GetRouteResponse response = routingService.getRoute(origin, destination);
    return ResponseEntity.ok(response);
  }
}
