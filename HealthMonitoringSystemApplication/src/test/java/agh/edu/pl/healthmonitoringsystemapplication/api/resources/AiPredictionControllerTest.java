package agh.edu.pl.healthmonitoringsystemapplication.api.resources;

import agh.edu.pl.healthmonitoringsystemapplication.ModelRequestTestUtil;
import agh.edu.pl.healthmonitoringsystemapplication.ModelResponseTestUtil;
import agh.edu.pl.healthmonitoringsystemapplication.domain.models.request.PredictionRequest;
import agh.edu.pl.healthmonitoringsystemapplication.domain.models.response.PredictionResponse;
import agh.edu.pl.healthmonitoringsystemapplication.domain.services.PredictionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

class AiPredictionControllerTest {

    @InjectMocks
    private PredictionController predictionController;

    @Mock
    private PredictionService predictionService;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(predictionController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testPredictionSuccess() throws Exception {
        // Given
        PredictionRequest request = ModelRequestTestUtil.predictionRequestBuilder().build();
        PredictionResponse expectedPrediction = ModelResponseTestUtil.predictionResponseBuilder().build();
        when(predictionService.predict(any(PredictionRequest.class))).thenReturn(expectedPrediction);

        // When
        mockMvc.perform(post("/api/predictions/test")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                // Then
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(expectedPrediction)));

        verify(predictionService, times(1)).predict(any(PredictionRequest.class));
    }

    @Test
    void testPredictionInvalidRequest() throws Exception {
        // Given
        PredictionRequest invalidRequest = ModelRequestTestUtil.predictionRequestBuilder()
                .imageBase64(null)
                .build();

        // When
        mockMvc.perform(post("/api/predictions/test")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                // Then
                .andExpect(status().isBadRequest());

        verify(predictionService, never()).predict(any(PredictionRequest.class));
    }
}
