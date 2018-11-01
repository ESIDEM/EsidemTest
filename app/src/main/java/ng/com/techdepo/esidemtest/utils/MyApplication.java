package ng.com.techdepo.esidemtest.utils;

import android.app.Application;


import ng.com.techdepo.esidemtest.api.ApiInterface;
import ng.com.techdepo.esidemtest.constants.Constants;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyApplication extends Application {

    private static Retrofit retrofit = null;

    @Override
    public void onCreate() {
        super.onCreate();


        initRetrofit();

    }

    private void initRetrofit() {
        retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constants.BASE_URL)
                .build();
    }

    public static ApiInterface getApiService() {
        return retrofit.create(ApiInterface.class);
    }

}
