package agh.edu.pl.healthmonitoringsystem.domain.service;

import agh.edu.pl.healthmonitoringsystem.domain.component.ModelMapper;
import agh.edu.pl.healthmonitoringsystem.domain.model.request.DoctorPatientRelationRequest;
import agh.edu.pl.healthmonitoringsystem.domain.model.response.Patient;
import agh.edu.pl.healthmonitoringsystem.domain.model.response.ResultForDoctorView;
import agh.edu.pl.healthmonitoringsystem.domain.model.response.ResultWithPatientData;
import agh.edu.pl.healthmonitoringsystem.domain.validator.DoctorPatientRelationRequestValidator;
import agh.edu.pl.healthmonitoringsystem.persistence.DoctorPatientRepository;
import agh.edu.pl.healthmonitoringsystem.persistence.PatientRepository;
import agh.edu.pl.healthmonitoringsystem.persistence.ResultRepository;
import agh.edu.pl.healthmonitoringsystem.persistence.model.entity.DoctorPatientEntity;
import agh.edu.pl.healthmonitoringsystem.persistence.model.entity.PatientEntity;
import agh.edu.pl.healthmonitoringsystem.persistence.model.projection.ResultWithAiSelectedAndViewedProjection;
import agh.edu.pl.healthmonitoringsystem.persistence.model.projection.ResultWithPatientDataProjection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DoctorPatientService {

    private final PatientRepository patientRepository;
    private final ResultRepository resultRepository;
    private final DoctorPatientRepository doctorPatientRepository;
    private final DoctorPatientRelationRequestValidator validator;
    private final ModelMapper modelMapper;

    @Autowired
    public DoctorPatientService(PatientRepository patientRepository,  ResultRepository resultRepository, DoctorPatientRepository doctorPatientRepository,
                                DoctorPatientRelationRequestValidator validator, ModelMapper modelMapper) {
        this.patientRepository = patientRepository;
        this.doctorPatientRepository = doctorPatientRepository;
        this.resultRepository = resultRepository;
        this.validator = validator;
        this.modelMapper = modelMapper;
    }

    public List<Patient> getPatientsByDoctorId(Long doctorId, Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        List<PatientEntity> patients = patientRepository.findPatientsByDoctorId(doctorId, pageRequest).getContent();
        return patients.stream()
                .map(modelMapper::mapPatientEntityToPatient)
                .collect(Collectors.toList());
    }

    public List<ResultForDoctorView> getDoctorPatientResultWithAiSelectedAndViewed(Long doctorId, Long patientId) {
        List<ResultWithAiSelectedAndViewedProjection> results = resultRepository.getDoctorPatientResultWithAiSelectedAndViewed(doctorId, patientId);
        return results.stream()
                .map(modelMapper::mapProjectionToResultForDoctorView)
                .collect(Collectors.toList());
    }

    public List<ResultWithPatientData> getDoctorUnviewedResults(Long doctorId) {
        List<ResultWithPatientDataProjection> results = resultRepository.getDoctorUnviewedResults(doctorId);
        return results.stream()
                .map(modelMapper::mapProjectionToResultWithPatientData)
                .collect(Collectors.toList());
    }

    public void createRelation(DoctorPatientRelationRequest request) {
        validator.validate(request);

        Optional<DoctorPatientEntity> entity = doctorPatientRepository.findByPatientIdAndDoctorId(
                request.getPatientId(), request.getDoctorId());

        if (entity.isEmpty()) {
            DoctorPatientEntity resultDoctorPatientEntity = DoctorPatientEntity.builder()
                    .patientId(request.getPatientId())
                    .doctorId(request.getDoctorId())
                    .createdDate(LocalDateTime.now())
                    .build();
            doctorPatientRepository.save(resultDoctorPatientEntity);
        }
    }

    public void deleteRelation(Long doctorId, Long patientId) {
        validator.validate(doctorId, patientId);

        Optional<DoctorPatientEntity> resultDoctorPatientEntity = doctorPatientRepository.findByPatientIdAndDoctorId(
                patientId, doctorId);
        resultDoctorPatientEntity.ifPresent(doctorPatientRepository::delete);
    }
}
