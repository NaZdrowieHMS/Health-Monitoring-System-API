package agh.edu.pl.healthmonitoringsystemapplication.repositories;

import agh.edu.pl.healthmonitoringsystemapplication.models.Patient;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;


import java.util.Optional;

@Repository
public interface PatientRepository extends CrudRepository<Patient, Long>, PagingAndSortingRepository<Patient, Long> {

    @Override
    Optional<Patient> findById(Long id);
}
