package agh.edu.pl.healthmonitoringsystem.client;

import retrofit2.Call;
import retrofit2.http.GET;
import agh.edu.pl.healthmonitoringsystem.response.Result;
import retrofit2.http.Path;
import java.util.List;

public interface PatientApi {
    @GET("/api/patients/{patientId}/results")
    Call<List<Result>> getPatientResults(@Path("patientId") Long patientId);
}