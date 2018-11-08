package ng.com.techdepo.esidemtest.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.content.SharedPreferences;

import ng.com.techdepo.esidemtest.api.ApiInterface;
import ng.com.techdepo.esidemtest.api.ApiService;
import ng.com.techdepo.esidemtest.models.QuestionResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AppRepository {

    ApiInterface apiInterface;
    SharedPreferences sharedPreferences;

    public AppRepository(Context context) {

        apiInterface = ApiService.getService();
        sharedPreferences =context.getSharedPreferences("ng.com.techdepo.esidemtest", Context.MODE_APPEND);
    }

    public LiveData<QuestionResponse> getQuestionsFromAPI(){
        String subject = sharedPreferences.getString("subject", "chemistry");
        final MutableLiveData<QuestionResponse> apiResponse = new MutableLiveData<>();

        apiInterface.getQuestions(subject).enqueue(new Callback<QuestionResponse>() {
            @Override
            public void onResponse(Call<QuestionResponse> call, Response<QuestionResponse> response) {

                if (response.isSuccessful()) {
                    QuestionResponse questionResponse = response.body();
                    apiResponse.postValue(questionResponse);
                }
            }

            @Override
            public void onFailure(Call<QuestionResponse> call, Throwable t) {

            }
        });

        return apiResponse;
    }
}
