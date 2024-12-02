package agh.edu.pl.healthmonitoringsystem.persistence;

import agh.edu.pl.healthmonitoringsystem.domain.model.Role;
import agh.edu.pl.healthmonitoringsystem.persistence.model.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    default Page<UserEntity> findAllDoctors(Pageable pageable) {
        return findAllByRole(Role.DOCTOR, pageable);
    }

    default Page<UserEntity> findAllPatients(Pageable pageable) {
        return findAllByRole(Role.PATIENT, pageable);
    }

    default Optional<UserEntity> findPatientById(Long id) {
        return findUserEntityByIdAndRole(id, Role.PATIENT);
    }

    default Optional<UserEntity> findDoctorById(Long id) {
        return findUserEntityByIdAndRole(id, Role.DOCTOR);
    }

    Page<UserEntity> findAllByRole(Role role, Pageable pageable);

    Optional<UserEntity> findUserEntityByIdAndRole(Long id, Role role);

    @Query(value = """
            SELECT p FROM UserEntity p
            WHERE p.id IN (SELECT dp.patientId FROM DoctorPatientEntity dp WHERE dp.doctorId = :doctorId)
            AND p.role = :role
            ORDER BY p.createdDate DESC
            """)
    Page<UserEntity> findPatientsByDoctorId(@Param("doctorId") Long doctorId, @Param("role") Role role, Pageable pageable);

    @Query(value = """
            SELECT p FROM UserEntity p
            WHERE p.id NOT IN (SELECT dp.patientId FROM DoctorPatientEntity dp WHERE dp.doctorId = :doctorId)
            AND p.role = :role
            ORDER BY p.createdDate DESC
            """)
    Page<UserEntity> findUnassignedPatientsByDoctorId(@Param("doctorId") Long doctorId, @Param("role") Role role, Pageable pageable);
}
