package ng.com.techdepo.esidemtest.api;

import ng.com.techdepo.esidemtest.constants.Constants;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public final class ApiService {

    private static ApiInterface endpoints;

    public static ApiInterface getService() {
        if (endpoints == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(Constants.BASE_URL)
                    .build();

            endpoints = retrofit.create(ApiInterface.class);
        }

        return endpoints;

    }
}
