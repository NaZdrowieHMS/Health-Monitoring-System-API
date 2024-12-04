package agh.edu.pl.healthmonitoringsystem;

import lombok.Getter;

@Getter
public enum TestEnv {
    LOCAL("http://localhost:8080/");

    private final String baseUrl;

    TestEnv(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public static String getBaseUrl() {
        return LOCAL.baseUrl;
    }
}
