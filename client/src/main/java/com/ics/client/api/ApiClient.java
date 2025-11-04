package com.ics.client.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ics.common.dto.*;
import java.net.URI;
import java.net.http.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class ApiClient {
  private final HttpClient http = HttpClient.newHttpClient();
  private final String baseUrl;
  private final ObjectMapper objectMapper = new ObjectMapper();

  public ApiClient(String baseUrl) {
    this.baseUrl = baseUrl;
  }

  public List<ScheduleResult> generate(GenerateRequest request) throws Exception {
    String jsonPayload = objectMapper.writeValueAsString(request);
    
    HttpRequest req = HttpRequest.newBuilder()
        .uri(URI.create(baseUrl + "/api/schedules/generate"))
        .header("Content-Type", "application/json")
        .POST(HttpRequest.BodyPublishers.ofString(jsonPayload, StandardCharsets.UTF_8))
        .build();
    
    HttpResponse<String> resp = http.send(req, HttpResponse.BodyHandlers.ofString());
    
    if (resp.statusCode() != 200) {
      throw new RuntimeException("API call failed with status: " + resp.statusCode() + ", body: " + resp.body());
    }
    
    return objectMapper.readValue(
        resp.body(),
        objectMapper.getTypeFactory().constructCollectionType(List.class, ScheduleResult.class)
    );
  }
}





