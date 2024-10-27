package agh.edu.pl.healthmonitoringsystemapplication.domain.models.response;

import agh.edu.pl.healthmonitoringsystemapplication.ModelTestUtil;
import agh.edu.pl.healthmonitoringsystemapplication.domain.exceptions.RequestValidationException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ResultDataContentTest {

    @Test
    public void shouldInitializeFieldsCorrectly() {
        // Given
        String data = "Some result data";
        String type = "Blood Test";

        // When
        ResultDataContent resultDataContent = ModelTestUtil.resultDataContentBuilder()
                .data(data)
                .type(type)
                .build();

        // Then
        assertEquals(data, resultDataContent.getData());
        assertEquals(type, resultDataContent.getType());
    }

    @Test
    public void builderShouldThrowExceptionWhenDataIsNull() {
        // Given
        String type = "Blood Test";

        // When & Then
        RequestValidationException exception = assertThrows(RequestValidationException.class, () -> {
            ResultDataContent.builder()
                    .data(null)
                    .type(type)
                    .build();
        });
        assertEquals("Data cannot be null", exception.getMessage());
    }

    @Test
    public void builderShouldThrowExceptionWhenTypeIsNull() {
        // Given
        String data = "Some result data";

        // When & Then
        RequestValidationException exception = assertThrows(RequestValidationException.class, () -> {
            ResultDataContent.builder()
                    .data(data)
                    .type(null)
                    .build();
        });
        assertEquals("Data type id cannot be null", exception.getMessage());
    }
}

