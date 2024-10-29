package agh.edu.pl.healthmonitoringsystem.persistence;

import agh.edu.pl.healthmonitoringsystem.persistence.model.entity.DoctorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PredictionRepository extends JpaRepository<DoctorEntity, Long> {
    // to define after database connection ;)
}
