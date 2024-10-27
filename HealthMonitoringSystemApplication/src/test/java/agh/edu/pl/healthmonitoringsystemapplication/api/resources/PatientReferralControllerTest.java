package agh.edu.pl.healthmonitoringsystemapplication.api.resources;

import agh.edu.pl.healthmonitoringsystemapplication.domain.models.response.ReferralResponse;
import agh.edu.pl.healthmonitoringsystemapplication.domain.services.PatientReferralService;
import agh.edu.pl.healthmonitoringsystemapplication.persistence.model.projection.PatientReferralWithCommentProjection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class PatientReferralControllerTest {

    @InjectMocks
    private PatientReferralController patientReferralController;

    @Mock
    private PatientReferralService patientReferralService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetPatientHealthCommentsReturnsReferralList() {
        // Given
        Long patientId = 1L;
        int startIndex = 0;
        int pageSize = 2;

        PatientReferralWithCommentProjection referral1 = mock(PatientReferralWithCommentProjection.class);
        when(referral1.getReferralId()).thenReturn(1L);
        when(referral1.getCommentId()).thenReturn(1L);
        when(referral1.getDoctorId()).thenReturn(1L);
        when(referral1.getPatientId()).thenReturn(patientId);
        when(referral1.getTestType()).thenReturn("Blood Test");
        when(referral1.getReferralNumber()).thenReturn("REF123");
        when(referral1.getCompleted()).thenReturn(true);
        when(referral1.getCommentContent()).thenReturn("Follow-up needed");
        when(referral1.getModifiedDate()).thenReturn(LocalDateTime.now());

        PatientReferralWithCommentProjection referral2 = mock(PatientReferralWithCommentProjection.class);
        when(referral2.getReferralId()).thenReturn(2L);
        when(referral2.getCommentId()).thenReturn(2L);
        when(referral2.getDoctorId()).thenReturn(2L);
        when(referral2.getPatientId()).thenReturn(patientId);
        when(referral2.getTestType()).thenReturn("X-Ray");
        when(referral2.getReferralNumber()).thenReturn("REF456");
        when(referral2.getCompleted()).thenReturn(false);
        when(referral2.getCommentContent()).thenReturn("Check results");
        when(referral2.getModifiedDate()).thenReturn(LocalDateTime.now());

        List<PatientReferralWithCommentProjection> referrals = Arrays.asList(referral1, referral2);
        when(patientReferralService.getPatientReferralsByPatientId(patientId, startIndex, pageSize)).thenReturn(referrals);

        // When
        ResponseEntity<List<ReferralResponse>> response = patientReferralController.getPatientHealthComments(startIndex, pageSize, patientId);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).hasSize(2);
        assertThat(response.getBody().get(0).getReferralId()).isEqualTo(1L);
        assertThat(response.getBody().get(0).getCommentContent()).isEqualTo("Follow-up needed");
        verify(patientReferralService, times(1)).getPatientReferralsByPatientId(patientId, startIndex, pageSize);
    }

    @Test
    void testGetPatientHealthCommentsReturnsEmptyListWhenNoReferralsFound() {
        // Given
        Long patientId = 1L;
        int startIndex = 0;
        int pageSize = 2;

        when(patientReferralService.getPatientReferralsByPatientId(patientId, startIndex, pageSize)).thenReturn(Collections.emptyList());

        // When
        ResponseEntity<List<ReferralResponse>> response = patientReferralController.getPatientHealthComments(startIndex, pageSize, patientId);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEmpty();
        verify(patientReferralService, times(1)).getPatientReferralsByPatientId(patientId, startIndex, pageSize);
    }

    @Test
    void testGetPatientHealthCommentsHandlesServiceException() {
        // Given
        Long patientId = 1L;
        int startIndex = 0;
        int pageSize = 50;

        when(patientReferralService.getPatientReferralsByPatientId(patientId, startIndex, pageSize)).thenThrow(new RuntimeException("Service error"));

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            patientReferralController.getPatientHealthComments(startIndex, pageSize, patientId);
        });

        assertThat(exception.getMessage()).isEqualTo("Service error");
        verify(patientReferralService, times(1)).getPatientReferralsByPatientId(patientId, startIndex, pageSize);
    }
}
