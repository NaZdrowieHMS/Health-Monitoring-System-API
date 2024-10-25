package agh.edu.pl.healthmonitoringsystemapplication.persistence;

import agh.edu.pl.healthmonitoringsystemapplication.persistence.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PredictionRepository extends JpaRepository<Doctor, Long> {
    // to define after database connection ;)
}
