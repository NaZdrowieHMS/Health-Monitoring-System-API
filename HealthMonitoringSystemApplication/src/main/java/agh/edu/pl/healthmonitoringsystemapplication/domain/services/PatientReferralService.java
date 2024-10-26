package agh.edu.pl.healthmonitoringsystemapplication.domain.services;

import agh.edu.pl.healthmonitoringsystemapplication.persistence.ReferralRepository;
import agh.edu.pl.healthmonitoringsystemapplication.persistence.model.projection.PatientReferralWithCommentProjection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientReferralService {

    private final ReferralRepository referralRepository;

    @Autowired
    public PatientReferralService(ReferralRepository referralRepository) {
        this.referralRepository = referralRepository;
    }

    public List<PatientReferralWithCommentProjection> getPatientReferralsByPatientId(Long patientId, Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<PatientReferralWithCommentProjection> referralPage = referralRepository.getPatientReferralsByPatientId(patientId, pageRequest);
        return referralPage.getContent();
    }
}