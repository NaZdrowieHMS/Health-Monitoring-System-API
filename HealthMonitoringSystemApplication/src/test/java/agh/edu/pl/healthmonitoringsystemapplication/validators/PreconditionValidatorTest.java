package agh.edu.pl.healthmonitoringsystemapplication.validators;

import org.junit.jupiter.api.Test;

import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PreconditionValidatorTest {

    @Test
    void testShouldThrowExceptionWhenReferenceIsNull() {
        // Given
        Object reference = null;
        Supplier<RuntimeException> exceptionSupplier = () -> new RuntimeException("Reference is null");

        // When & Then
        RuntimeException thrown = assertThrows(RuntimeException.class, () ->
                PreconditionValidator.checkNotNull(reference, exceptionSupplier)
        );
        assertEquals("Reference is null", thrown.getMessage());
    }

    @Test
    void testShouldNotThrowExceptionWhenReferenceIsNotNull() {
        // Given
        Object reference = new Object();
        Supplier<RuntimeException> exceptionSupplier = () -> new RuntimeException("Reference is null");

        // When & Then
        PreconditionValidator.checkNotNull(reference, exceptionSupplier);
    }
}
