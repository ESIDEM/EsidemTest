package ng.com.techdepo.esidemtest.repository;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import java.util.List;
import java.util.concurrent.Executor;

import ng.com.techdepo.esidemtest.api.ApiInterface;
import ng.com.techdepo.esidemtest.api.ApiService;
import ng.com.techdepo.esidemtest.database.AppDatabase;
import ng.com.techdepo.esidemtest.database.QuestionEntity;
import ng.com.techdepo.esidemtest.database.Result;
import ng.com.techdepo.esidemtest.models.Question;
import ng.com.techdepo.esidemtest.models.QuestionResponse;
import ng.com.techdepo.esidemtest.utils.QuestionConverter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AppRepository {

    ApiInterface apiInterface;
    SharedPreferences sharedPreferences;
    AppDatabase appDatabase;
    Executor executor;
    LiveData<List<QuestionEntity>> quetions;
    LiveData<List<Result>> results;

    public AppRepository(Context context) {

        apiInterface = ApiService.getService();
        sharedPreferences =context.getSharedPreferences("ng.com.techdepo.esidemtest", Context.MODE_APPEND);
        appDatabase = AppDatabase.getInstance(context);
        quetions = appDatabase.databaseDAO().getAllQuestions();
        results = appDatabase.resultDAO().getAllResult();
    }

    public LiveData<List<QuestionEntity>>getQuestionsFromDb(){


        return quetions;
    }

    public LiveData<List<Result>> getAllResult(){
        return results;
    }

    public void  getQuestionsFromAPI(){
        String subject = sharedPreferences.getString("subject", "chemistry");
        apiInterface.getQuestions(subject).enqueue(new Callback<QuestionResponse>() {
            @Override
            public void onResponse(Call<QuestionResponse> call, Response<QuestionResponse> response) {

                if (response.isSuccessful()) {
                    QuestionResponse questionResponse = response.body();
                    for (Question question: questionResponse.getData()){


                                inSertItem(QuestionConverter.convertQuestionToEntity(question));
                            }


                }
            }

            @Override
            public void onFailure(Call<QuestionResponse> call, Throwable t) {

            }
        });

            }

            public void inSertItem(QuestionEntity questionEntity){
        new BackGroundInsert(appDatabase).execute(questionEntity);
            }
            public void inSertResult(Result result){

        new BackGroundInsertResult(appDatabase).execute(result);
            }

            private static class BackGroundInsert extends AsyncTask<QuestionEntity,Void,Void>{
        private AppDatabase db;


                public BackGroundInsert(AppDatabase db) {
                    this.db = db;
                }

                @Override
                protected Void doInBackground(QuestionEntity... questionEntities) {
                    db.databaseDAO().inSertQuestions(questionEntities[0]);
                    return null;
                }
            }

    private static class BackGroundInsertResult extends AsyncTask<Result,Void,Void>{
        private AppDatabase db;


        public BackGroundInsertResult(AppDatabase db) {
            this.db = db;
        }


        @Override
        protected Void doInBackground(Result... results) {
            db.resultDAO().inSertResult(results[0]);
            return null;
        }
    }
}
