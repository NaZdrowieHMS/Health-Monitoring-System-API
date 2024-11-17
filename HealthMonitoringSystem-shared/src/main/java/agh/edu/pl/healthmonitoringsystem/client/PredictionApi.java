package agh.edu.pl.healthmonitoringsystem.client;

import agh.edu.pl.healthmonitoringsystem.request.BatchPredictionUploadRequest;
import agh.edu.pl.healthmonitoringsystem.request.PredictionSummaryRequest;
import agh.edu.pl.healthmonitoringsystem.request.PredictionSummaryUpdateRequest;
import agh.edu.pl.healthmonitoringsystem.request.PredictionUploadRequest;
import agh.edu.pl.healthmonitoringsystem.response.Prediction;
import agh.edu.pl.healthmonitoringsystem.response.PredictionSummary;
import agh.edu.pl.healthmonitoringsystem.response.ResultDataContent;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

import java.util.List;

public interface PredictionApi {

    @POST("/api/predictions/request")
    Call<PredictionSummary> createPredictionRequest(@Body PredictionSummaryRequest predictionSummaryRequest);

    @PUT("/api/predictions/request")
    Call<Void> updatePredictionRequest(@Body PredictionSummaryUpdateRequest predictionSummaryRequest);

    @GET("/api/predictions/request/{requestId}")
    Call<PredictionSummary> getPredictionSummaryRequestById(@Path("requestId") Long requestId);

    @POST("/api/predictions")
    Call<Prediction> uploadPrediction(@Body PredictionUploadRequest predictionRequest);

    @POST("/api/predictions/batch")
    Call<List<Prediction>> batchUploadPredictions(@Body BatchPredictionUploadRequest predictionRequest);

    @GET("/api/results/{resultId}/prediction")
    Call<Prediction> getPredictionForResult(@Path("resultId") Long resultId);

    @GET("/api/results/{resultId}/data")
    Call<ResultDataContent> getPredictionDataFromResult(@Path("resultId") Long resultId);

}
