package ng.com.techdepo.esidemtest.repository;

import androidx.lifecycle.LiveData;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ng.com.techdepo.esidemtest.api.ApiInterface;
import ng.com.techdepo.esidemtest.database.AppDatabase;
import ng.com.techdepo.esidemtest.database.QuestionEntity;
import ng.com.techdepo.esidemtest.database.Result;
import ng.com.techdepo.esidemtest.models.Question;
import ng.com.techdepo.esidemtest.models.QuestionResponse;
import ng.com.techdepo.esidemtest.utils.QuestionConverter;
import ng.com.techdepo.esidemtest.utils.QuestionsApplication;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AppRepository {

    @Inject ApiInterface apiInterface;
    @Inject SharedPreferences sharedPreferences;
    @Inject
    AppDatabase appDatabase;
    LiveData<List<QuestionEntity>> quetions;
    LiveData<List<Result>> results;
    List<QuestionEntity> widgetQuestions;

    public AppRepository(Context context) {
        ((QuestionsApplication) context.getApplicationContext()).getAppComponent().inject(this);
        quetions = appDatabase.databaseDAO().getAllQuestions();
        results = appDatabase.resultDAO().getAllResult();

    }

    public LiveData<List<QuestionEntity>>getQuestionsFromDb(){


        return quetions;
    }

    public  List<QuestionEntity> getWidgetQuestions(){

        try {
            widgetQuestions= new BackgroudQuesry(appDatabase).execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return  widgetQuestions;
    }



    public LiveData<List<Result>> getAllResult(){
        return results;
    }

    public void  getQuestionsFromAPI(){
        String subject = sharedPreferences.getString("subject", "english");

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
            public  void deleteResult(int resultId){
        new BackgroundDeleteResult(appDatabase).execute(resultId);
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

    private static class BackgroundDeleteResult extends AsyncTask<Integer,Void,Void>{
        private AppDatabase db;

        public BackgroundDeleteResult(AppDatabase db) {
            this.db = db;
        }

        @Override
        protected Void doInBackground(Integer... integers) {
            int position = integers[0];
            db.resultDAO().deleteById(position);
            return null;
        }
    }

    private static class BackgroudQuesry extends AsyncTask<Void,Void,List<QuestionEntity>>{
        private  AppDatabase db;
        List<QuestionEntity> widgetQuestions;
        public BackgroudQuesry(AppDatabase db) {
            this.db = db;
        }

        @Override
        protected List<QuestionEntity> doInBackground(Void... voids) {
           widgetQuestions = db.databaseDAO().getWidgetQuestions();
            return widgetQuestions;
        }
    }
}
