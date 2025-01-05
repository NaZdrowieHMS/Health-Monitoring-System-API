package agh.edu.pl.healthmonitoringsystem.client;

import agh.edu.pl.healthmonitoringsystem.request.BatchPredictionUploadRequest;
import agh.edu.pl.healthmonitoringsystem.request.PredictionSummaryRequest;
import agh.edu.pl.healthmonitoringsystem.request.PredictionSummaryUpdateRequest;
import agh.edu.pl.healthmonitoringsystem.request.PredictionUploadRequest;
import agh.edu.pl.healthmonitoringsystem.response.Prediction;
import agh.edu.pl.healthmonitoringsystem.response.PredictionSummary;
import agh.edu.pl.healthmonitoringsystem.response.ResultDataContent;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface PredictionApi {
    @POST("/api/predictions/request")
    Call<PredictionSummary> createPredictionRequest(@Body PredictionSummaryRequest predictionSummaryRequest, @Header("Authorization") String authentication);

    @PUT("/api/predictions/request")
    Call<Void> updatePredictionRequest(@Body PredictionSummaryUpdateRequest predictionSummaryRequest, @Header("Authorization") String authentication);

    @GET("/api/predictions/request/{requestId}")
    Call<PredictionSummary> getPredictionSummaryRequestById(@Path("requestId") Long requestId, @Header("Authorization") String authentication);

    @POST("/api/predictions")
    Call<Prediction> uploadPrediction(@Body PredictionUploadRequest predictionRequest, @Header("Authorization") String authentication);

    @POST("/api/predictions/batch")
    Call<List<Prediction>> batchUploadPredictions(@Body BatchPredictionUploadRequest predictionRequest, @Header("Authorization") String authentication);

    @GET("/api/results/{resultId}/prediction")
    Call<Prediction> getPredictionForResult(@Path("resultId") Long resultId, @Header("Authorization") String authentication);

    @GET("/api/results/{resultId}/data")
    Call<ResultDataContent> getPredictionDataFromResult(@Path("resultId") Long resultId, @Header("Authorization") String authentication);

}
