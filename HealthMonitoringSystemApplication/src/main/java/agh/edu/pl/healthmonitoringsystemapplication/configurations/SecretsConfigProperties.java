package agh.edu.pl.healthmonitoringsystemapplication.configurations;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "secrets")
public record SecretsConfigProperties(String dbHost, String dbPort, String dbName, String dbUser, String dbPassword, String sslMode) {
}

