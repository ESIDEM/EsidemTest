package ng.com.techdepo.esidemtest.api;

import ng.com.techdepo.esidemtest.models.QuestionResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("m")
    Call<QuestionResponse> getQuestions(@Query("subject") String subject);
}
