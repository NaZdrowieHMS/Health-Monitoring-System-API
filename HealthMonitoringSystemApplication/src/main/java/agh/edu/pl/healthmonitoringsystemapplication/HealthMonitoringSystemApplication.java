package agh.edu.pl.healthmonitoringsystemapplication;

import agh.edu.pl.healthmonitoringsystemapplication.configurations.SecretsConfigProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(SecretsConfigProperties.class)
public class HealthMonitoringSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(HealthMonitoringSystemApplication.class, args);
	}
}
