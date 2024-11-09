package agh.edu.pl.healthmonitoringsystem.client;

import agh.edu.pl.healthmonitoringsystem.request.AiFormAnalysisRequest;
import agh.edu.pl.healthmonitoringsystem.response.AiFormAnalysis;
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
    Call<AiFormAnalysis> saveFormAiAnalysis(@Body AiFormAnalysisRequest aiFormAnalysisRequest);
}
