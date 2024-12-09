package agh.edu.pl.healthmonitoringsystem.client;

import agh.edu.pl.healthmonitoringsystem.request.AiFormAnalysisRequest;
import agh.edu.pl.healthmonitoringsystem.model.FormAiAnalysis;
import agh.edu.pl.healthmonitoringsystem.response.Form;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;


public interface FormApi {
    @GET("api/forms")
    Call<List<Form>> getAllHealthForms(
            @Query("startIndex") Integer startIndex,
            @Query("pageSize") Integer pageSize,
            @Header("userId") Long userId,
            @Query("patientId") Long patientId
    );
    @GET("/api/forms/{formId}")
    Call<Form> getFormById(@Path("formId") Long formId);
}
