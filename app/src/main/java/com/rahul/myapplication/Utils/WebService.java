package com.rahul.myapplication.Utils;

import android.text.TextUtils;

import com.rahul.myapplication.Model.Response;

import retrofit2.Call;
import retrofit2.Callback;

public class WebService {

    public void getUserList(final WebServiceListener webServiceListener) {
        RetroService client;
        String baseUrl = GeneralUtils.getBaseUrl();
        if (TextUtils.isEmpty(baseUrl)) {
            webServiceListener.onError("Empty base url");
            return;
        }

        client = Client.getClient(baseUrl).create(RetroService.class);
        Call<Response> call = client.getListOfUser();
        call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                webServiceListener.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                webServiceListener.onError(t.getMessage());
            }
        });
    }
}
