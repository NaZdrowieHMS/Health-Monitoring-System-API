package agh.edu.pl.healthmonitoringsystem.domain.models.response;

import agh.edu.pl.healthmonitoringsystem.ModelTestUtil;
import agh.edu.pl.healthmonitoringsystem.domain.exceptions.RequestValidationException;
import agh.edu.pl.healthmonitoringsystem.response.Result;
import agh.edu.pl.healthmonitoringsystem.response.ResultDataContent;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ResultTest {

    @Test
    public void shouldInitializeFieldsCorrectly() {
        // Given
        Long id = 1L;
        Long patientId = 2L;
        String testType = "Blood Test";
        ResultDataContent content = ModelTestUtil.resultDataContent();
        LocalDateTime createdDate = LocalDateTime.now();

        // When
        Result result = new Result(
                id,
                patientId,
                testType,
                content,
                createdDate
        );

        // Then
        assertEquals(id, result.id());
        assertEquals(patientId, result.patientId());
        assertEquals(testType, result.testType());
        assertEquals(content, result.content());
        assertEquals(createdDate, result.createdDate());
    }
}

