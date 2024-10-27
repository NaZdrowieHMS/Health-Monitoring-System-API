package agh.edu.pl.healthmonitoringsystemapplication.domain.components;

import agh.edu.pl.healthmonitoringsystemapplication.domain.exceptions.InvalidJsonFieldException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class JsonFieldExtractor {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public String extract(String jsonContent, String fieldName) {
        try {
            JsonNode node = objectMapper.readTree(jsonContent);
            if (!node.has(fieldName)) {
                throw new InvalidJsonFieldException("Field '" + fieldName + "' not found in JSON content.");
            }
            return node.get(fieldName).asText();
        } catch (Exception e) {
            throw new InvalidJsonFieldException("Error parsing JSON content: " + e.getMessage());
        }
    }
}
