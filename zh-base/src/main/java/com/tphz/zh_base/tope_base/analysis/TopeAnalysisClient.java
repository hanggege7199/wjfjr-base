package com.tphz.zh_base.tope_base.analysis;


import com.tphz.zh_base.common.Constants;
import com.tphz.zh_base.http_lib.HttpClient;

public class TopeAnalysisClient extends HttpClient {
    private static TopeAnalysisClient topeAnalysisClient;
    private static String BASE_URL = "";

    public static void setBaseUrl(String baseUrl) {
        BASE_URL = baseUrl;
    }

    public TopeAnalysisClient() {
        super(Constants.getContext(), BASE_URL);
    }

    public static synchronized TopeAnalysisClient getInstance() {
        if (topeAnalysisClient == null) {
            topeAnalysisClient = new TopeAnalysisClient();
        }
        return topeAnalysisClient;
    }

    public TopeAnalysisApi getApi() {
        return retrofit.create(TopeAnalysisApi.class);
    }

}
