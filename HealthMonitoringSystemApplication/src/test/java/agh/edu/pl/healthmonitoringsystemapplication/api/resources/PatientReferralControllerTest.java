package agh.edu.pl.healthmonitoringsystemapplication.api.resources;

import agh.edu.pl.healthmonitoringsystemapplication.ModelTestUtil;
import agh.edu.pl.healthmonitoringsystemapplication.domain.models.response.Referral;
import agh.edu.pl.healthmonitoringsystemapplication.domain.services.PatientReferralService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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

        // Create mock referrals
        Referral referral1 = ModelTestUtil.referralBuilder().referralId(1L).commentContent("Follow-up needed").build();
        Referral referral2 = ModelTestUtil.referralBuilder().build();

        List<Referral> referrals = Arrays.asList(referral1, referral2);
        when(patientReferralService.getPatientReferralsByPatientId(patientId, startIndex, pageSize)).thenReturn(referrals);

        // When
        ResponseEntity<List<Referral>> response = patientReferralController.getPatientHealthComments(startIndex, pageSize, patientId);

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
        ResponseEntity<List<Referral>> response = patientReferralController.getPatientHealthComments(startIndex, pageSize, patientId);

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

        when(patientReferralService.getPatientReferralsByPatientId(patientId, startIndex, pageSize))
                .thenThrow(new RuntimeException("Service error"));

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            patientReferralController.getPatientHealthComments(startIndex, pageSize, patientId);
        });

        assertThat(exception.getMessage()).isEqualTo("Service error");
        verify(patientReferralService, times(1)).getPatientReferralsByPatientId(patientId, startIndex, pageSize);
    }
}
