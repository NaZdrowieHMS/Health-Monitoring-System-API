package agh.edu.pl.healthmonitoringsystemapplication;

import agh.edu.pl.healthmonitoringsystemapplication.domain.models.request.DoctorRequest;
import agh.edu.pl.healthmonitoringsystemapplication.domain.models.request.PatientRequest;
import agh.edu.pl.healthmonitoringsystemapplication.domain.models.request.PredictionRequest;

public class ModelRequestTestUtil {
    public static PredictionRequest.PredictionRequestBuilder predictionRequestBuilder() {
        return PredictionRequest.builder()
                .imageBase64("data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAUA...");
    }

    public static DoctorRequest.DoctorRequestBuilder doctorRequestBuilder() {
        return DoctorRequest.builder()
                .name("John")
                .surname("Doe")
                .email("john.doe@example.com")
                .pesel("12345678901")
                .pwz("5425740");
    }

    public static PatientRequest.PatientRequestBuilder patientRequestBuilder() {
        return PatientRequest.builder()
                .name("John")
                .surname("Doe")
                .email("john.doe@example.com")
                .pesel("12345678901");
    }
}
