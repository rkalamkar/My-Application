package com.rahul.myapplication.Utils;

import com.rahul.myapplication.Model.Response;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RetroService {
    @GET("api/0.4/?randomapi")
    Call<Response> getListOfUser();

}
