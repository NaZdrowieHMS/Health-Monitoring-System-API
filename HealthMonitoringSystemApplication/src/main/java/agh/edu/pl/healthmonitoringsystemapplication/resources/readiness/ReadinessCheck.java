package agh.edu.pl.healthmonitoringsystemapplication.resources.readiness;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class ReadinessCheck  {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public boolean checkIfReady() {
        try {
            jdbcTemplate.queryForObject("SELECT 1", Integer.class);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}

