package agh.edu.pl.healthmonitoringsystemapplication.validators;

import java.util.function.Supplier;

public class PreconditionValidator {

    public static void checkNotNull(Object reference, Supplier<RuntimeException> runtimeExceptionSupplier){
        if (reference == null){
            throw runtimeExceptionSupplier.get();
        }
    }
}
