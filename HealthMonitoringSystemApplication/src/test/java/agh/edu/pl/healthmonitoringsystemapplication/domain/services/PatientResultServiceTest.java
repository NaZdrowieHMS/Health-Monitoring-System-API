package agh.edu.pl.healthmonitoringsystemapplication.domain.services;

import agh.edu.pl.healthmonitoringsystemapplication.ModelEntityTestUtil;
import agh.edu.pl.healthmonitoringsystemapplication.ModelTestUtil;
import agh.edu.pl.healthmonitoringsystemapplication.domain.components.JsonFieldExtractor;
import agh.edu.pl.healthmonitoringsystemapplication.domain.components.ModelMapper;
import agh.edu.pl.healthmonitoringsystemapplication.domain.models.response.Result;
import agh.edu.pl.healthmonitoringsystemapplication.persistence.ResultRepository;
import agh.edu.pl.healthmonitoringsystemapplication.persistence.model.entity.ResultEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class PatientResultServiceTest {

    @InjectMocks
    private PatientResultService patientResultService;

    @Mock
    private ResultRepository resultRepository;

    @Mock
    private JsonFieldExtractor jsonFieldExtractor;

    @Mock
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetPatientResultsByPatientIdReturnsResultList() {
        // Given
        Long patientId = 1L;
        Integer page = 0;
        Integer size = 2;

        ResultEntity resultEntity1 = ModelEntityTestUtil.resultBuilder()
                .id(1L)
                .patientId(patientId)
                .testType("Blood Test")
                .createdDate(LocalDateTime.now())
                .content("{\"data\":\"value1\", \"type\":\"testType1\"}")
                .build();

        ResultEntity resultEntity2 = ModelEntityTestUtil.resultBuilder()
                .id(2L)
                .patientId(patientId)
                .testType("X-Ray")
                .createdDate(LocalDateTime.now())
                .content("{\"data\":\"value2\", \"type\":\"testType2\"}")
                .build();

        List<ResultEntity> resultEntities = Arrays.asList(resultEntity1, resultEntity2);
        Page<ResultEntity> resultPage = new PageImpl<>(resultEntities, PageRequest.of(page, size), resultEntities.size());

        when(resultRepository.getPatientResultsByPatientId(patientId, PageRequest.of(page, size))).thenReturn(resultPage);
        when(modelMapper.mapResultEntityToResult(resultEntity1)).thenReturn(ModelTestUtil.resultBuilder()
                .id(1L)
                .testType("Blood Test").build());
        when(modelMapper.mapResultEntityToResult(resultEntity2)).thenReturn(ModelTestUtil.resultBuilder()
                .id(2L)
                .testType("X-Ray").build());

        // When
        List<Result> results = patientResultService.getPatientResultsByPatientId(patientId, page, size);

        // Then
        assertThat(results).hasSize(2);
        assertThat(results.get(0).getId()).isEqualTo(1L);
        assertThat(results.get(0).getTestType()).isEqualTo("Blood Test");
        assertThat(results.get(0).getContent().getType()).isEqualTo("Blood");

        assertThat(results.get(1).getId()).isEqualTo(2L);
        assertThat(results.get(1).getTestType()).isEqualTo("X-Ray");

        verify(resultRepository, times(1)).getPatientResultsByPatientId(patientId, PageRequest.of(page, size));
    }

    @Test
    void testGetPatientResultsByPatientIdReturnsEmptyListWhenNoResultsFound() {
        // Given
        Long patientId = 1L;
        Integer page = 0;
        Integer size = 2;

        Page<ResultEntity> emptyResultPage = new PageImpl<>(Collections.emptyList(), PageRequest.of(page, size), 0);
        when(resultRepository.getPatientResultsByPatientId(patientId, PageRequest.of(page, size))).thenReturn(emptyResultPage);

        // When
        List<Result> results = patientResultService.getPatientResultsByPatientId(patientId, page, size);

        // Then
        assertThat(results).isEmpty();
        verify(resultRepository, times(1)).getPatientResultsByPatientId(patientId, PageRequest.of(page, size));
        verify(jsonFieldExtractor, never()).extract(anyString(), anyString());
    }

    @Test
    void testGetPatientResultsByPatientIdHandlesException() {
        // Given
        Long patientId = 1L;
        int page = 0;
        int size = 2;

        when(resultRepository.getPatientResultsByPatientId(patientId, PageRequest.of(page, size))).thenThrow(new RuntimeException("Repository error"));

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            patientResultService.getPatientResultsByPatientId(patientId, page, size);
        });

        assertThat(exception.getMessage()).isEqualTo("Repository error");
        verify(resultRepository, times(1)).getPatientResultsByPatientId(patientId, PageRequest.of(page, size));
    }

}
