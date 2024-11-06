package agh.edu.pl.healthmonitoringsystem.domain.service;

import agh.edu.pl.healthmonitoringsystem.domain.component.ModelMapper;
import agh.edu.pl.healthmonitoringsystem.domain.model.request.DoctorPatientRelationRequest;
import agh.edu.pl.healthmonitoringsystem.domain.model.response.Relation;
import agh.edu.pl.healthmonitoringsystem.domain.validator.RequestValidator;
import agh.edu.pl.healthmonitoringsystem.persistence.DoctorPatientRepository;
import agh.edu.pl.healthmonitoringsystem.persistence.model.entity.DoctorPatientEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class DoctorPatientService {

    private final DoctorPatientRepository doctorPatientRepository;
    private final RequestValidator validator;
    private final ModelMapper modelMapper;

    @Autowired
    public DoctorPatientService(DoctorPatientRepository doctorPatientRepository,
                                RequestValidator validator, ModelMapper modelMapper) {
        this.doctorPatientRepository = doctorPatientRepository;
        this.validator = validator;
        this.modelMapper = modelMapper;
    }

    public Relation createRelation(DoctorPatientRelationRequest request) {
        validator.validate(request.getDoctorId(), request.getPatientId());

        Optional<DoctorPatientEntity> entity = doctorPatientRepository.findByPatientIdAndDoctorId(
                request.getPatientId(), request.getDoctorId());

        if (entity.isEmpty()) {
            DoctorPatientEntity resultDoctorPatientEntity = DoctorPatientEntity.builder()
                    .patientId(request.getPatientId())
                    .doctorId(request.getDoctorId())
                    .createdDate(LocalDateTime.now())
                    .build();
            DoctorPatientEntity savedEntity = doctorPatientRepository.save(resultDoctorPatientEntity);
            return modelMapper.mapRelationEntityToRelation(savedEntity);
        }
        return modelMapper.mapRelationEntityToRelation(entity.get());
    }

    public void deleteRelation(Long doctorId, Long patientId) {
        validator.validate(doctorId, patientId);

        Optional<DoctorPatientEntity> resultDoctorPatientEntity = doctorPatientRepository.findByPatientIdAndDoctorId(
                patientId, doctorId);
        resultDoctorPatientEntity.ifPresent(doctorPatientRepository::delete);
    }
}
