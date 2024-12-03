package agh.edu.pl.healthmonitoringsystem.domain.service;

import agh.edu.pl.healthmonitoringsystem.domain.component.ModelMapper;
import agh.edu.pl.healthmonitoringsystem.domain.model.request.DoctorPatientRelationRequest;
import agh.edu.pl.healthmonitoringsystem.domain.model.response.Relation;
import agh.edu.pl.healthmonitoringsystem.domain.validator.RequestValidator;
import agh.edu.pl.healthmonitoringsystem.persistence.DoctorPatientRepository;
import agh.edu.pl.healthmonitoringsystem.persistence.model.entity.DoctorPatientEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DoctorPatientServiceTest {

    private DoctorPatientRepository doctorPatientRepository;
    private RequestValidator validator;
    private ModelMapper modelMapper;
    private DoctorPatientService doctorPatientService;

    @BeforeEach
    void setUp() {
        doctorPatientRepository = mock(DoctorPatientRepository.class);
        validator = mock(RequestValidator.class);
        modelMapper = mock(ModelMapper.class);
        doctorPatientService = new DoctorPatientService(doctorPatientRepository, validator, modelMapper);
    }

    @Test
    void createRelation_ShouldCreateAndReturnNewRelation_WhenNotExists() {
        // Given
        DoctorPatientRelationRequest request = new DoctorPatientRelationRequest(1L, 2L);
        when(doctorPatientRepository.findByPatientIdAndDoctorId(2L, 1L)).thenReturn(Optional.empty());

        DoctorPatientEntity savedEntity = DoctorPatientEntity.builder()
                .doctorId(1L)
                .patientId(2L)
                .createdDate(LocalDateTime.now())
                .build();
        when(doctorPatientRepository.save(any(DoctorPatientEntity.class))).thenReturn(savedEntity);

        Relation expectedRelation = new Relation(1L, 2L);
        when(modelMapper.mapRelationEntityToRelation(savedEntity)).thenReturn(expectedRelation);

        // When
        Relation relation = doctorPatientService.createRelation(request);

        // Then
        verify(validator).validate(1L, 2L);
        verify(doctorPatientRepository).save(any(DoctorPatientEntity.class));
        assertEquals(expectedRelation, relation);
    }

    @Test
    void createRelation_ShouldReturnExistingRelation_WhenAlreadyExists() {
        // Given
        DoctorPatientRelationRequest request = new DoctorPatientRelationRequest(1L, 2L);
        DoctorPatientEntity existingEntity = DoctorPatientEntity.builder()
                .doctorId(1L)
                .patientId(2L)
                .createdDate(LocalDateTime.now())
                .build();
        when(doctorPatientRepository.findByPatientIdAndDoctorId(2L, 1L)).thenReturn(Optional.of(existingEntity));

        Relation expectedRelation = new Relation(1L, 2L);
        when(modelMapper.mapRelationEntityToRelation(existingEntity)).thenReturn(expectedRelation);

        // When
        Relation relation = doctorPatientService.createRelation(request);

        // Then
        verify(validator).validate(1L, 2L);
        verify(doctorPatientRepository, never()).save(any(DoctorPatientEntity.class));
        assertEquals(expectedRelation, relation);
    }

    @Test
    void deleteRelation_ShouldDoNothing_WhenNotExists() {
        // Given
        Long doctorId = 1L;
        Long patientId = 2L;
        when(doctorPatientRepository.findByPatientIdAndDoctorId(patientId, doctorId)).thenReturn(Optional.empty());

        // When
        doctorPatientService.deleteRelation(doctorId, patientId);

        // Then
        verify(validator).validate(doctorId, patientId);
        verify(doctorPatientRepository, never()).delete(any(DoctorPatientEntity.class));
    }
}
