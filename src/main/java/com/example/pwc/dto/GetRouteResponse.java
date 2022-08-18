package com.example.pwc.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetRouteResponse {
  @JsonProperty("route")
  private List<String> routeList;
}
