package agh.edu.pl.healthmonitoringsystem.domain.component;

import agh.edu.pl.healthmonitoringsystem.model.ResultAiAnalysis;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Converter
@Component
public class ResultAiAnalysisConverter implements AttributeConverter<ResultAiAnalysis, String> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(ResultAiAnalysis resultAiAnalysis) {
        if(resultAiAnalysis == null) return null;
        try {
            return objectMapper.writeValueAsString(resultAiAnalysis);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting list to JSON", e);
        }
    }

    @Override
    public ResultAiAnalysis convertToEntityAttribute(String dbData) {
        if(dbData == null) return null;
        try {
            return objectMapper.readValue(dbData, ResultAiAnalysis.class);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting JSON to list", e);
        }
    }
}
