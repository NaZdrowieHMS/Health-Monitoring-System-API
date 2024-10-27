package agh.edu.pl.healthmonitoringsystemapplication.persistence;

import agh.edu.pl.healthmonitoringsystemapplication.persistence.model.entity.ResultEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ResultRepository extends JpaRepository<ResultEntity, Long> {

    @Override
    Optional<ResultEntity> findById(Long id);

    @Query(value = "SELECT * FROM result r WHERE r.patient_id = :patientId",
            countQuery = "SELECT COUNT(*) FROM result r WHERE r.patient_id = :patientId",
            nativeQuery = true)
    Page<ResultEntity> getPatientResultsByPatientId(@Param("patientId") Long patientId, Pageable pageable);
}
