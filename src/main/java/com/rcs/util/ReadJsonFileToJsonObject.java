package com.rcs.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Paths;

@Slf4j
@Component
public class ReadJsonFileToJsonObject {

  public JsonNode read() {
    JsonNode node = null;
    try {
      String file = "src/main/resources/openapi/vehicleRes.json";
      String content = new String(Files.readAllBytes(Paths.get(file)));
      ObjectMapper mapper1 = new ObjectMapper();
      node = mapper1.readTree(content);
    } catch (Exception ex) {
      log.error("exception : {}", ex.getMessage());
    }
    return node;
  }
}
