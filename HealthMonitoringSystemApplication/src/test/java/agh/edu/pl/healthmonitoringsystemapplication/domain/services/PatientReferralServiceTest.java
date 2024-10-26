package agh.edu.pl.healthmonitoringsystemapplication.domain.services;

import agh.edu.pl.healthmonitoringsystemapplication.persistence.ReferralRepository;
import agh.edu.pl.healthmonitoringsystemapplication.persistence.model.projection.PatientReferralWithCommentProjection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class PatientReferralServiceTest {

    @InjectMocks
    private PatientReferralService patientReferralService;

    @Mock
    private ReferralRepository referralRepository;

    @Mock
    private Page<PatientReferralWithCommentProjection> referralPage;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetPatientReferralsByPatientIdReturnsReferralList() {
        // Given
        Long patientId = 1L;
        int page = 0;
        int size = 2;

        PatientReferralWithCommentProjection referral1 = mock(PatientReferralWithCommentProjection.class);
        when(referral1.getReferralId()).thenReturn(1L);
        when(referral1.getCommentId()).thenReturn(1L);

        PatientReferralWithCommentProjection referral2 = mock(PatientReferralWithCommentProjection.class);
        when(referral2.getReferralId()).thenReturn(2L);
        when(referral2.getCommentId()).thenReturn(2L);

        List<PatientReferralWithCommentProjection> referrals = Arrays.asList(referral1, referral2);
        when(referralRepository.getPatientReferralsByPatientId(patientId, PageRequest.of(page, size))).thenReturn(referralPage);
        when(referralPage.getContent()).thenReturn(referrals);

        // When
        List<PatientReferralWithCommentProjection> result = patientReferralService.getPatientReferralsByPatientId(patientId, page, size);

        // Then
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getReferralId()).isEqualTo(1L);
        assertThat(result.get(1).getReferralId()).isEqualTo(2L);
        verify(referralRepository, times(1)).getPatientReferralsByPatientId(patientId, PageRequest.of(page, size));
    }

    @Test
    void testGetPatientReferralsByPatientIdReturnsEmptyListWhenNoReferralsFound() {
        // Given
        Long patientId = 1L;
        int page = 0;
        int size = 2;

        when(referralRepository.getPatientReferralsByPatientId(patientId, PageRequest.of(page, size))).thenReturn(referralPage);
        when(referralPage.getContent()).thenReturn(Collections.emptyList());

        // When
        List<PatientReferralWithCommentProjection> result = patientReferralService.getPatientReferralsByPatientId(patientId, page, size);

        // Then
        assertThat(result).isEmpty();
        verify(referralRepository, times(1)).getPatientReferralsByPatientId(patientId, PageRequest.of(page, size));
    }

    @Test
    void testGetPatientReferralsByPatientIdHandlesRepositoryException() {
        // Given
        Long patientId = 1L;
        int page = 0;
        int size = 2;

        when(referralRepository.getPatientReferralsByPatientId(patientId, PageRequest.of(page, size))).thenThrow(new RuntimeException("Repository error"));

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            patientReferralService.getPatientReferralsByPatientId(patientId, page, size);
        });

        assertThat(exception.getMessage()).isEqualTo("Repository error");
        verify(referralRepository, times(1)).getPatientReferralsByPatientId(patientId, PageRequest.of(page, size));
    }
}
