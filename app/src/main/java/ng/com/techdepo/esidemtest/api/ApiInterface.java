package ng.com.techdepo.esidemtest.api;

import io.reactivex.Observable;
import ng.com.techdepo.esidemtest.models.QuestionResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("m")
    Observable<QuestionResponse> getQuestions(@Query("subject") String subject);
}
