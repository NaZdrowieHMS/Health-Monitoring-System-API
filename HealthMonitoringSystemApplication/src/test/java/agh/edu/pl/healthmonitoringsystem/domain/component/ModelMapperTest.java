package agh.edu.pl.healthmonitoringsystem.domain.component;

import agh.edu.pl.healthmonitoringsystem.ModelEntityTestUtil;
import agh.edu.pl.healthmonitoringsystem.domain.model.response.Doctor;
import agh.edu.pl.healthmonitoringsystem.domain.model.response.HealthComment;
import agh.edu.pl.healthmonitoringsystem.domain.model.response.Patient;
import agh.edu.pl.healthmonitoringsystem.domain.model.response.Referral;
import agh.edu.pl.healthmonitoringsystem.response.Result;
import agh.edu.pl.healthmonitoringsystem.persistence.model.entity.DoctorEntity;
import agh.edu.pl.healthmonitoringsystem.persistence.model.entity.PatientEntity;
import agh.edu.pl.healthmonitoringsystem.persistence.model.entity.ResultEntity;
import agh.edu.pl.healthmonitoringsystem.persistence.model.projection.HealthCommentWithAuthorProjection;
import agh.edu.pl.healthmonitoringsystem.persistence.model.projection.PatientReferralWithCommentProjection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

class ModelMapperTest {

    @Mock
    private JsonFieldExtractor jsonFieldExtractor;

    @InjectMocks
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void mapPatientEntityToPatient_shouldReturnMappedPatient() {
        // Given
        PatientEntity patientEntity = ModelEntityTestUtil.patientBuilder().build();

        // When
        Patient patient = modelMapper.mapPatientEntityToPatient(patientEntity);

        // Then
        assertEquals(patientEntity.getId(), patient.getId());
        assertEquals(patientEntity.getName(), patient.getName());
        assertEquals(patientEntity.getSurname(), patient.getSurname());
        assertEquals(patientEntity.getEmail(), patient.getEmail());
        assertEquals(patientEntity.getPesel(), patient.getPesel());
    }

    @Test
    void mapPatientEntityToPatient_shouldReturnNull_whenPatientEntityIsNull() {
        // Given
        PatientEntity patientEntity = null;

        // When
        Patient patient = modelMapper.mapPatientEntityToPatient(patientEntity);

        // Then
        assertNull(patient);
    }

    @Test
    void mapDoctorEntityToDoctor_shouldReturnMappedDoctor() {
        // Given
        DoctorEntity doctorEntity = ModelEntityTestUtil.doctorBuilder().build();

        // When
        Doctor doctor = modelMapper.mapDoctorEntityToDoctor(doctorEntity);

        // Then
        assertEquals(doctorEntity.getId(), doctor.getId());
        assertEquals(doctorEntity.getName(), doctor.getName());
        assertEquals(doctorEntity.getSurname(), doctor.getSurname());
        assertEquals(doctorEntity.getEmail(), doctor.getEmail());
        assertEquals(doctorEntity.getPesel(), doctor.getPesel());
        assertEquals(doctorEntity.getPwz(), doctor.getPwz());
    }

    @Test
    void mapDoctorEntityToDoctor_shouldReturnNull_whenDoctorEntityIsNull() {
        // Given
        DoctorEntity doctorEntity = null;

        // When
        Doctor doctor = modelMapper.mapDoctorEntityToDoctor(doctorEntity);

        // Then
        assertNull(doctor);
    }

    @Test
    void mapResultEntityToResult_shouldReturnMappedResult() {
        // Given
        ResultEntity resultEntity = ModelEntityTestUtil.resultBuilder().build();
        String jsonContent = "{\"data\": \"Test data\", \"type\": \"Blood\"}";
        when(jsonFieldExtractor.extract(resultEntity.getContent(), "data")).thenReturn("Test data");
        when(jsonFieldExtractor.extract(resultEntity.getContent(), "type")).thenReturn("Blood");

        // When
        Result result = modelMapper.mapResultEntityToResult(resultEntity);

        // Then
        assertEquals(resultEntity.getId(), result.id());
        assertEquals(resultEntity.getPatientId(), result.patientId());
        assertEquals(resultEntity.getTestType(), result.testType());
        assertEquals(resultEntity.getCreatedDate(), result.createdDate());
        assertEquals("Test data", result.content().data());
        assertEquals("Blood", result.content().type());
    }

    @Test
    void mapResultEntityToResult_shouldReturnNull_whenResultEntityIsNull() {
        // Given
        ResultEntity resultEntity = null;

        // When
        Result result = modelMapper.mapResultEntityToResult(resultEntity);

        // Then
        assertNull(result);
    }

    @Test
    void mapProjectionToReferral_shouldReturnNull_whenReferralProjectionIsNull() {
        // Given
        PatientReferralWithCommentProjection referralProjection = null;

        // When
        Referral referral = modelMapper.mapProjectionToReferral(referralProjection);

        // Then
        assertNull(referral);
    }

    @Test
    void mapProjectionToHealth_shouldReturnNull_whenHealthCommentProjectionIsNull() {
        // Given
        HealthCommentWithAuthorProjection healthCommentProjection = null;

        // When
        HealthComment healthComment = modelMapper.mapProjectionToHealth(healthCommentProjection);

        // Then
        assertNull(healthComment);
    }
}
