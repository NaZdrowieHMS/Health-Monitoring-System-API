package agh.edu.pl.healthmonitoringsystem.domain.validator;

import agh.edu.pl.healthmonitoringsystem.domain.exception.EntityNotFoundException;
import agh.edu.pl.healthmonitoringsystem.persistence.DoctorRepository;
import agh.edu.pl.healthmonitoringsystem.persistence.PatientRepository;
import agh.edu.pl.healthmonitoringsystem.persistence.ReferralRepository;
import agh.edu.pl.healthmonitoringsystem.persistence.ResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EntityValidator {
    private final ResultRepository resultRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final ReferralRepository referralRepository;

    @Autowired
    public EntityValidator(ResultRepository resultRepository, PatientRepository patientRepository,
                           DoctorRepository doctorRepository, ReferralRepository referralRepository) {
        this.resultRepository = resultRepository;
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
        this.referralRepository = referralRepository;
    }

    public void validateDoctor(Long doctorId) {
        doctorRepository.findById(doctorId)
                .orElseThrow(() -> new EntityNotFoundException("Doctor with id " + doctorId + " does not exist"));
    }

    public void validatePatient(Long patientId) {
        patientRepository.findById(patientId)
                .orElseThrow(() -> new EntityNotFoundException("Patient with id " + patientId + " does not exist"));
    }

    public void validateResult(Long resultId) {
        resultRepository.findById(resultId)
                .orElseThrow(() -> new EntityNotFoundException("Result with id " + resultId + " does not exist"));
    }

    public void validateReferral(Long referralId) {
        referralRepository.findById(referralId)
                .orElseThrow(() -> new EntityNotFoundException("Referral with id " + referralId + " does not exist"));
    }
}
