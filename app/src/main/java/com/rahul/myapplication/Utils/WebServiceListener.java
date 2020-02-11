package com.rahul.myapplication.Utils;

import com.rahul.myapplication.Model.Response;

public interface WebServiceListener {
    void onSuccess(Response response);

    void onError(String message);
}
