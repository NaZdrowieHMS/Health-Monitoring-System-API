package agh.edu.pl.healthmonitoringsystemapplication.resources.patients;

import agh.edu.pl.healthmonitoringsystemapplication.databaseModels.Patient;
import agh.edu.pl.healthmonitoringsystemapplication.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patients")
public class PatientController {

    private final PatientRepository patientRepository;

    @Autowired
    public PatientController(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Patient> getPatient(@PathVariable Long id) {
        return patientRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Patient> createPatient(@RequestBody Patient patient) {
        patientRepository.save(patient);
        return ResponseEntity.status(201).body(patient);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable Long id) {
        patientRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
