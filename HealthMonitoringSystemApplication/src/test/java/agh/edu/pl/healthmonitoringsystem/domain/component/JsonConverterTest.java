package agh.edu.pl.healthmonitoringsystem.domain.component;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonConverterTest {

    private JsonConverter jsonConverter;

    @BeforeEach
    void setUp() {
        jsonConverter = new JsonConverter();
    }

    @Test
    void convertToDatabaseColumn_ShouldConvertListToJson() {
        // Given
        List<String> input = List.of("apple", "banana", "cherry");

        // When
        String json = jsonConverter.convertToDatabaseColumn(input);

        // Then
        assertEquals("[\"apple\",\"banana\",\"cherry\"]", json);
    }

    @Test
    void convertToEntityAttribute_ShouldConvertJsonToList() {
        // Given
        String json = "[\"apple\",\"banana\",\"cherry\"]";

        // When
        List<String> result = jsonConverter.convertToEntityAttribute(json);

        // Then
        assertNotNull(result);
        assertEquals(3, result.size());
        assertTrue(result.contains("apple"));
        assertTrue(result.contains("banana"));
        assertTrue(result.contains("cherry"));
    }

    @Test
    void convertToEntityAttribute_ShouldThrowException_WhenJsonIsMalformed() {
        // Given
        String malformedJson = "{\"key\":\"value\""; // Invalid JSON format

        // When & Then
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> jsonConverter.convertToEntityAttribute(malformedJson));
        assertTrue(exception.getMessage().contains("Error converting JSON to list"));
    }
}
