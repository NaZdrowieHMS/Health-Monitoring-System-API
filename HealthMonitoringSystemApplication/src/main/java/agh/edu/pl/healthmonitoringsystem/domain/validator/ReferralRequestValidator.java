package agh.edu.pl.healthmonitoringsystem.domain.validator;

import agh.edu.pl.healthmonitoringsystem.domain.exception.AccessDeniedException;
import agh.edu.pl.healthmonitoringsystem.domain.exception.EntityNotFoundException;
import agh.edu.pl.healthmonitoringsystem.domain.model.request.ReferralRequest;
import agh.edu.pl.healthmonitoringsystem.domain.model.request.ReferralUpdateRequest;
import agh.edu.pl.healthmonitoringsystem.persistence.DoctorRepository;
import agh.edu.pl.healthmonitoringsystem.persistence.PatientRepository;
import agh.edu.pl.healthmonitoringsystem.persistence.model.entity.ReferralEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReferralRequestValidator {

    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;

    @Autowired
    public ReferralRequestValidator(PatientRepository patientRepository, DoctorRepository doctorRepository) {
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
    }

    public void validate(ReferralRequest request) {
        patientRepository.findById(request.getPatientId())
                .orElseThrow(() -> new EntityNotFoundException("Patient with id " + request.getPatientId() + " does not exist"));

        doctorRepository.findById(request.getDoctorId())
                .orElseThrow(() -> new EntityNotFoundException("Doctor with id " + request.getDoctorId() + " does not exist"));
    }

    public void validateUpdateRequest(ReferralUpdateRequest request, ReferralEntity referralEntity) {
        if (!referralEntity.getDoctorId().equals(request.getDoctorId())){
            throw new AccessDeniedException(String.format("Only the author of the referral can edit it. " +
                    "Author id: %s. Current editor id: %s.", referralEntity.getDoctorId(), request.getDoctorId()));
        }
    }
}
