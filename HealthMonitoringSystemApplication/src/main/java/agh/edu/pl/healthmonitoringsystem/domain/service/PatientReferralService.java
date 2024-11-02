package agh.edu.pl.healthmonitoringsystem.domain.service;

import agh.edu.pl.healthmonitoringsystem.domain.component.ModelMapper;
import agh.edu.pl.healthmonitoringsystem.domain.exception.EntityNotFoundException;
import agh.edu.pl.healthmonitoringsystem.domain.model.response.Referral;
import agh.edu.pl.healthmonitoringsystem.persistence.PatientRepository;
import agh.edu.pl.healthmonitoringsystem.persistence.ReferralRepository;
import agh.edu.pl.healthmonitoringsystem.persistence.model.projection.PatientReferralWithCommentProjection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PatientReferralService {

    private final ReferralRepository referralRepository;
    private final PatientRepository patientRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public PatientReferralService(ReferralRepository referralRepository, PatientRepository patientRepository, ModelMapper modelMapper) {
        this.referralRepository = referralRepository;
        this.patientRepository = patientRepository;
        this.modelMapper = modelMapper;
    }

    public List<Referral> getPatientReferralsByPatientId(Long patientId, Integer page, Integer size) {
        patientRepository.findById(patientId)
                .orElseThrow(() -> new EntityNotFoundException("Patient with id " + patientId + " not found"));

        PageRequest pageRequest = PageRequest.of(page, size);
        List<PatientReferralWithCommentProjection> referrals = referralRepository.getPatientReferralsByPatientId(patientId, pageRequest).getContent();

        return referrals.stream()
                .map(modelMapper::mapProjectionToReferral)
                .collect(Collectors.toList());
    }
}