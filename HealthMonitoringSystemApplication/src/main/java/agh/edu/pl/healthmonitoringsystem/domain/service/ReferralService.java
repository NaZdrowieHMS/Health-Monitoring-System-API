package agh.edu.pl.healthmonitoringsystem.domain.service;

import agh.edu.pl.healthmonitoringsystem.domain.component.ModelMapper;
import agh.edu.pl.healthmonitoringsystem.domain.exception.EntityNotFoundException;
import agh.edu.pl.healthmonitoringsystem.domain.model.request.DeleteRequest;
import agh.edu.pl.healthmonitoringsystem.domain.model.request.ReferralRequest;
import agh.edu.pl.healthmonitoringsystem.domain.model.request.ReferralUpdateRequest;
import agh.edu.pl.healthmonitoringsystem.domain.model.response.Referral;
import agh.edu.pl.healthmonitoringsystem.domain.validator.RequestValidator;
import agh.edu.pl.healthmonitoringsystem.persistence.ReferralRepository;
import agh.edu.pl.healthmonitoringsystem.persistence.model.entity.ReferralEntity;
import agh.edu.pl.healthmonitoringsystem.persistence.model.projection.PatientReferralWithCommentProjection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static agh.edu.pl.healthmonitoringsystem.domain.component.UpdateUtil.updateField;

@Service
public class ReferralService {

    private final ReferralRepository referralRepository;
    private final RequestValidator validator;
    private final ModelMapper modelMapper;

    @Autowired
    public ReferralService(ReferralRepository referralRepository, RequestValidator validator, ModelMapper modelMapper) {
        this.referralRepository = referralRepository;
        this.validator = validator;
        this.modelMapper = modelMapper;
    }

    public List<Referral> getPatientReferralsByPatientId(Long patientId, Integer page, Integer size) {
        validator.validatePatient(patientId);

        PageRequest pageRequest = PageRequest.of(page, size);
        List<PatientReferralWithCommentProjection> referrals = referralRepository.getPatientReferralsByPatientId(patientId, pageRequest).getContent();

        return referrals.stream()
                .map(modelMapper::mapProjectionToReferral)
                .collect(Collectors.toList());
    }

    public Referral createReferral(ReferralRequest referralRequest) {
        validator.validate(referralRequest.getDoctorId(), referralRequest.getPatientId());

        LocalDateTime now = LocalDateTime.now();
        ReferralEntity referralEntity = ReferralEntity.builder()
                .doctorId(referralRequest.getDoctorId())
                .patientId(referralRequest.getPatientId())
                .testType(referralRequest.getTestType())
                .number(referralRequest.getReferralNumber())
                .completed(referralRequest.getCompleted())
                .comment(referralRequest.getComment())
                .createdDate(now)
                .modifiedDate(now).build();

        return saveAndMapReferral(referralEntity);
    }

    public Referral getReferralById(Long referralId) {
        ReferralEntity referralEntity = referralRepository.findById(referralId)
                .orElseThrow(() -> new EntityNotFoundException("Referral with id " + referralId + " does not exist"));
        return mapReferral(referralEntity);
    }

    public void deleteReferral(DeleteRequest referralDeleteRequest) {
        ReferralEntity entity = referralRepository.findById(referralDeleteRequest.getId())
                .orElseThrow(() -> new EntityNotFoundException("Referral with id " + referralDeleteRequest.getId() + " does not exist"));
        referralRepository.delete(entity);
    }

    public Referral updateReferral(ReferralUpdateRequest referralUpdateRequest) {
        ReferralEntity referralEntity = referralRepository.findById(referralUpdateRequest.getReferralId())
                .orElseThrow(() -> new EntityNotFoundException("Referral with id " + referralUpdateRequest.getReferralId() + " does not exist"));
        validator.validateUpdateRequest(referralUpdateRequest, referralEntity);

        updateField(Optional.ofNullable(referralUpdateRequest.getTestType()), referralEntity::setTestType);
        updateField(Optional.ofNullable(referralUpdateRequest.getCompleted()), referralEntity::setCompleted);
        updateField(Optional.ofNullable(referralUpdateRequest.getComment()), referralEntity::setComment);
        referralEntity.setModifiedDate(LocalDateTime.now());

        return saveAndMapReferral(referralEntity);
    }

    public void completeReferral(Long referralId){
        ReferralEntity referralEntity = referralRepository.findById(referralId)
                .orElseThrow(() -> new EntityNotFoundException("Referral with id " + referralId + " does not exist"));

        referralEntity.setCompleted(true);
        referralEntity.setModifiedDate(LocalDateTime.now());
        saveAndMapReferral(referralEntity);
    }

    private Referral mapReferral(ReferralEntity referralEntity) {
        PatientReferralWithCommentProjection referral = referralRepository.getPatientReferralWithAllData(referralEntity.getId()).orElse(null);
        return modelMapper.mapProjectionToReferral(referral);
    }

    private Referral saveAndMapReferral(ReferralEntity referralEntity) {
        ReferralEntity savedReferralEntity = referralRepository.save(referralEntity);
        return mapReferral(savedReferralEntity);
    }
}