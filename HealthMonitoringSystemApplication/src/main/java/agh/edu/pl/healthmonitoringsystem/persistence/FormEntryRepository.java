package agh.edu.pl.healthmonitoringsystem.persistence;

import agh.edu.pl.healthmonitoringsystem.persistence.model.entity.FormEntryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FormEntryRepository extends JpaRepository<FormEntryEntity, Long> {

    List<FormEntryEntity> findByFormId(Long id);
}