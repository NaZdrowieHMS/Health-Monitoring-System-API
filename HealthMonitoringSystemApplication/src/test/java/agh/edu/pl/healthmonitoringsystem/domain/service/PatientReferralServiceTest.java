package agh.edu.pl.healthmonitoringsystem.domain.service;

import agh.edu.pl.healthmonitoringsystem.ModelTestUtil;
import agh.edu.pl.healthmonitoringsystem.domain.component.ModelMapper;
import agh.edu.pl.healthmonitoringsystem.domain.model.response.Referral;
import agh.edu.pl.healthmonitoringsystem.persistence.ReferralRepository;
import agh.edu.pl.healthmonitoringsystem.persistence.model.projection.PatientReferralWithCommentProjection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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
    private ModelMapper modelMapper;

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

        PatientReferralWithCommentProjection referralP1 = mock(PatientReferralWithCommentProjection.class);
        when(referralP1.getReferralId()).thenReturn(1L);
        when(referralP1.getCommentId()).thenReturn(1L);

        PatientReferralWithCommentProjection referralP2 = mock(PatientReferralWithCommentProjection.class);
        when(referralP2.getReferralId()).thenReturn(2L);
        when(referralP2.getCommentId()).thenReturn(2L);

        List<PatientReferralWithCommentProjection> referralsList = Arrays.asList(referralP1, referralP2);
        Page<PatientReferralWithCommentProjection> referralPage = new PageImpl<>(referralsList, PageRequest.of(page, size), referralsList.size());

        when(referralRepository.getPatientReferralsByPatientId(patientId, PageRequest.of(page, size))).thenReturn(referralPage);

        Referral referral1 = ModelTestUtil.referralBuilder().referralId(1L).build();
        Referral referral2 = ModelTestUtil.referralBuilder().referralId(2L).build();
        when(modelMapper.mapProjectionToReferral(referralP1)).thenReturn(referral1);
        when(modelMapper.mapProjectionToReferral(referralP2)).thenReturn(referral2);

        // When
        List<Referral> result = patientReferralService.getPatientReferralsByPatientId(patientId, page, size);

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

        Page<PatientReferralWithCommentProjection> emptyReferralPage = new PageImpl<>(Collections.emptyList(), PageRequest.of(page, size), 0);
        when(referralRepository.getPatientReferralsByPatientId(patientId, PageRequest.of(page, size))).thenReturn(emptyReferralPage);

        // When
        List<Referral> result = patientReferralService.getPatientReferralsByPatientId(patientId, page, size);

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
