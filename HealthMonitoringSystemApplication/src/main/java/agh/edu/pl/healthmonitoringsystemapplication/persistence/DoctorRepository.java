package agh.edu.pl.healthmonitoringsystemapplication.persistence;

import agh.edu.pl.healthmonitoringsystemapplication.persistence.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    @Override
    Optional<Doctor> findById(Long id);
}
