package agh.edu.pl.healthmonitoringsystemapplication.domain.components;

import agh.edu.pl.healthmonitoringsystemapplication.domain.exceptions.InvalidJsonFieldException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class JsonFieldExtractorTest {

    private JsonFieldExtractor jsonFieldExtractor;

    @BeforeEach
    void setUp() {
        jsonFieldExtractor = new JsonFieldExtractor();
    }

    @Test
    void testExtractReturnsCorrectValue() {
        // Given
        String jsonContent = "{\"name\": \"John\", \"age\": \"30\"}";
        String fieldName = "name";

        // When
        String result = jsonFieldExtractor.extract(jsonContent, fieldName);

        // Then
        assertThat(result).isEqualTo("John");
    }

    @Test
    void testExtractThrowsExceptionWhenFieldNotFound() {
        // Given
        String jsonContent = "{\"name\": \"John\", \"age\": \"30\"}";
        String fieldName = "address";

        // When & Then
        InvalidJsonFieldException exception = assertThrows(InvalidJsonFieldException.class, () -> {
            jsonFieldExtractor.extract(jsonContent, fieldName);
        });
        assertThat(exception.getMessage()).isEqualTo("Error parsing JSON content: Field 'address' not found in JSON content.");
    }

    @Test
    void testExtractThrowsExceptionOnInvalidJson() {
        // Given
        String invalidJsonContent = "{name: John, age: 30}";
        String fieldName = "name";

        // When & Then
        InvalidJsonFieldException exception = assertThrows(InvalidJsonFieldException.class, () -> {
            jsonFieldExtractor.extract(invalidJsonContent, fieldName);
        });
        assertThat(exception.getMessage()).contains("Error parsing JSON content");
    }
}
