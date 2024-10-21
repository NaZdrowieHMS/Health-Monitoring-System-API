package agh.edu.pl.healthmonitoringsystemapplication.repositories;

import agh.edu.pl.healthmonitoringsystemapplication.databaseModels.Patient;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.table;

@Repository
public class PatientRepository {

    private final DSLContext dsl;

    @Autowired
    public PatientRepository(DSLContext dsl) {
        this.dsl = dsl;
    }

    public Optional<Patient> findById(Long id) {
        return dsl.selectFrom("patient")
                .where("id = ?", id)
                .fetchOptional(record -> {
                    return new Patient(
                            record.get("id", Long.class),
                            record.get("name", String.class),
                            record.get("surname", String.class),
                            record.get("email", String.class),
                            record.get("pesel", String.class),
                            record.get("created_at", LocalDateTime.class),
                            record.get("last_updated", LocalDateTime.class)
                    );
                });
    }

    public void save(Patient patient) {
        dsl.insertInto(table("patient"))
                .set(field("name"), patient.getName())
                .set(field("surname"), patient.getSurname())
                .set(field("email"), patient.getEmail())
                .set(field("pesel"), patient.getPesel())
                .set(field("created_at"), patient.getCreatedAt())
                .set(field("last_updated"), patient.getLastUpdated())
                .execute();
    }

    public void deleteById(Long id) {
        dsl.deleteFrom(table("patient"))
                .where(field("id").eq(id))
                .execute();
    }
}
