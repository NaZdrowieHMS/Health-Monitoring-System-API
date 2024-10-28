package agh.edu.pl.healthmonitoringsystem.persistence;

import agh.edu.pl.healthmonitoringsystem.persistence.model.entity.DoctorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<DoctorEntity, Long> {

    @Override
    Optional<DoctorEntity> findById(Long id);
}
