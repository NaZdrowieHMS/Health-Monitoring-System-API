package agh.edu.pl.healthmonitoringsystem.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ResultDataType {
    PNG("image/png"),
    JPG("image/jpg"),
    JPEG("image/jpeg");

    public final String value;

    ResultDataType(String value) {
        this.value = value;
    }

    @JsonValue
    @Override
    public String toString() {
        return this.value;
    }

    @JsonCreator
    public static ResultDataType fromString(String value) {
        for (ResultDataType type : ResultDataType.values()) {
            if (type.value.equals(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown result data type: " + value);
    }
}
