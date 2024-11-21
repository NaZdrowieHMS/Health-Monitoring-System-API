package agh.edu.pl.healthmonitoringsystem.domain.validator;

import agh.edu.pl.healthmonitoringsystem.domain.exception.EntityNotFoundException;
import agh.edu.pl.healthmonitoringsystem.domain.exception.RequestValidationException;
import agh.edu.pl.healthmonitoringsystem.persistence.FormRepository;
import agh.edu.pl.healthmonitoringsystem.persistence.PredictionRepository;
import agh.edu.pl.healthmonitoringsystem.persistence.PredictionSummaryRepository;
import agh.edu.pl.healthmonitoringsystem.persistence.ReferralRepository;
import agh.edu.pl.healthmonitoringsystem.persistence.ResultRepository;
import agh.edu.pl.healthmonitoringsystem.persistence.UserRepository;
import agh.edu.pl.healthmonitoringsystem.persistence.model.entity.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EntityValidator {
    private final ResultRepository resultRepository;
    private final UserRepository userRepository;
    private final ReferralRepository referralRepository;
    private final FormRepository formRepository;
    private final PredictionRepository predictionRepository;
    private final PredictionSummaryRepository predictionSummaryRepository;

    @Autowired
    public EntityValidator(ResultRepository resultRepository, UserRepository userRepository,
                           ReferralRepository referralRepository, FormRepository formRepository, PredictionRepository predictionRepository,
                           PredictionSummaryRepository predictionSummaryRepository) {
        this.resultRepository = resultRepository;
        this.userRepository = userRepository;
        this.referralRepository = referralRepository;
        this.formRepository = formRepository;
        this.predictionRepository = predictionRepository;
        this.predictionSummaryRepository = predictionSummaryRepository;
    }

    public void validateDoctor(Long doctorId) {
        userRepository.findDoctorById(doctorId)
                .orElseThrow(() -> new EntityNotFoundException("Doctor with id " + doctorId + " does not exist"));
    }

    public void validatePatient(Long patientId) {
        userRepository.findPatientById(patientId)
                .orElseThrow(() -> new EntityNotFoundException("Patient with id " + patientId + " does not exist"));
    }

    public void validateResult(Long resultId) {
        resultRepository.findById(resultId)
                .orElseThrow(() -> new EntityNotFoundException("Result with id " + resultId + " does not exist"));
    }


    public void validateResultForPatient(Long resultId, Long patientId) {
        ResultEntity resultEntity = resultRepository.findById(resultId)
                .orElseThrow(() -> new EntityNotFoundException("Result with id " + resultId + " does not exist"));
        if (!resultEntity.getPatientId().equals(patientId)) {
            throw new RequestValidationException("Result with id " + resultId + " does not belong to patient with id " + patientId);
        }
    }

    public void validatePrediction(Long predictionId) {
        predictionRepository.findById(predictionId)
                .orElseThrow(() -> new EntityNotFoundException("Prediction with id " + predictionId + " does not exist"));
    }

    public void validatePredictionSummary(Long predictionId) {
        predictionSummaryRepository.findById(predictionId)
                .orElseThrow(() -> new EntityNotFoundException("Prediction summary with id " + predictionId + " does not exist"));
    }

    public void validateForm(Long formId) {
        formRepository.findById(formId)
                .orElseThrow(() -> new EntityNotFoundException("Form with id " + formId + " does not exist"));
    }

    public void validateReferral(Long referralId) {
        referralRepository.findById(referralId)
                .orElseThrow(() -> new EntityNotFoundException("Referral with id " + referralId + " does not exist"));
    }
}
