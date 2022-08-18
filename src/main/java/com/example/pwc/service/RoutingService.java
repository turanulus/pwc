package com.example.pwc.service;

import com.example.pwc.dto.GetRouteResponse;
import com.example.pwc.exception.CountryCodeNotFoundException;
import com.example.pwc.exception.RouteNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class RoutingService {

  @Qualifier("countryMapping")
  private final Map<String, List<String>> countryMapping;

  public GetRouteResponse getRoute(String origin, String destination) {
    Map<String, String> pred = new HashMap<>();

    if (countryMapping.get(origin) == null || countryMapping.get(destination) == null) {
      throw new CountryCodeNotFoundException("Origin or destination country codes not found");
    }

    if (!bfs(countryMapping, origin, destination, countryMapping.keySet(), pred)) {
      log.error("Route not found from {} to {}", origin, destination);
      throw new RouteNotFoundException("Route not found");
    }

    List<String> path = new LinkedList<>();
    String crawl = destination;
    path.add(crawl);
    while (pred.get(crawl) != null) {
      path.add(pred.get(crawl));
      crawl = pred.get(crawl);
    }

    Collections.reverse(path);
    return GetRouteResponse.builder().routeList(path).build();
  }

  private boolean bfs(
      Map<String, List<String>> adj,
      String src,
      String dest,
      Set<String> keys,
      Map<String, String> pred) {
    LinkedList<String> queue = new LinkedList<>();
    Map<String, Boolean> visited = new HashMap<>();

    keys.forEach(key -> visited.put(key, false));

    visited.put(src, true);
    queue.add(src);

    while (!queue.isEmpty()) {
      String node = queue.remove();
      for (int i = 0; i < adj.get(node).size(); i++) {
        String adjacent = adj.get(node).get(i);
        if (!visited.get(adjacent)) {
          visited.put(adjacent, true);
          pred.put(adjacent, node);
          queue.add(adjacent);

          if (adjacent.equals(dest)) {
            return true;
          }
        }
      }
    }
    return false;
  }
}
