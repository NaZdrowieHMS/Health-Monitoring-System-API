package agh.edu.pl.healthmonitoringsystem.domain.component;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.function.Consumer;

@Component
public class UpdateUtil {
    public static <T> void updateField(Optional<T> newValue, Consumer<T> setter) {
        newValue.ifPresent(setter);
    }
}

