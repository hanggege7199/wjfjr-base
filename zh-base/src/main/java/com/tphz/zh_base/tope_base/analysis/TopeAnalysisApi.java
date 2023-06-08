package com.tphz.zh_base.tope_base.analysis;

import com.tope.http_lib.DataList;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface TopeAnalysisApi {

    @POST("buried/saveBuried")
    Call<DataList<String>> saveBuried(@Body RequestBody body);
}
