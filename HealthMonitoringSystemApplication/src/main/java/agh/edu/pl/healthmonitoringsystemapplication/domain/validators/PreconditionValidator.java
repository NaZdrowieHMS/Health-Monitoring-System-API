package agh.edu.pl.healthmonitoringsystemapplication.domain.validators;

import java.util.function.Supplier;

public class PreconditionValidator {

    public static void checkNotNull(Object reference, Supplier<RuntimeException> runtimeExceptionSupplier){
        if (reference == null){
            throw runtimeExceptionSupplier.get();
        }
    }

    public static void checkLength(String string, Integer length, Supplier<RuntimeException> runtimeExceptionSupplier){
        if (string.length() != length){
            throw runtimeExceptionSupplier.get();
        }
    }
}
