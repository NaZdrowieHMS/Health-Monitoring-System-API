package agh.edu.pl.healthmonitoringsystem.domain.model.response;

import agh.edu.pl.healthmonitoringsystem.response.ResultDataContent;
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
        ResultDataContent resultDataContent = new ResultDataContent(data, type);

        // Then
        assertEquals(data, resultDataContent.data());
        assertEquals(type, resultDataContent.type());
    }
}

