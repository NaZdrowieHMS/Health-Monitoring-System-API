package agh.edu.pl.healthmonitoringsystem.client;

import agh.edu.pl.healthmonitoringsystem.request.AiFormAnalysisRequest;
import agh.edu.pl.healthmonitoringsystem.model.FormAiAnalysis;
import agh.edu.pl.healthmonitoringsystem.response.Form;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;


public interface FormApi {
    @GET("/api/forms/{formId}")
    Call<Form> getFormById(@Path("formId") Long formId);

    @POST("/api/forms/analysis")
    Call<FormAiAnalysis> saveFormAiAnalysis(@Body AiFormAnalysisRequest aiFormAnalysisRequest);
}
