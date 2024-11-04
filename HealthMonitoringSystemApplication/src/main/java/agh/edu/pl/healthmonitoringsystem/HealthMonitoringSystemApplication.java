package agh.edu.pl.healthmonitoringsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@EnableAspectJAutoProxy
@EnableRetry
public class HealthMonitoringSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(HealthMonitoringSystemApplication.class, args);
	}
}
