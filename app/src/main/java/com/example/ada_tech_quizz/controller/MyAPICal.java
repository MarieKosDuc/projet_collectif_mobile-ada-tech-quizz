package com.example.ada_tech_quizz.controller;

import com.example.ada_tech_quizz.model.DataModel;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MyAPICal {
    //  http://localhost:8085/        questions/1
    //
    @GET("questions/1")
    Call<DataModel> getData();


}
