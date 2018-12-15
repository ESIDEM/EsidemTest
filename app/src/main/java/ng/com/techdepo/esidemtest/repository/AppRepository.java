package ng.com.techdepo.esidemtest.repository;

import androidx.lifecycle.LiveData;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
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

        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                widgetQuestions = appDatabase.databaseDAO().getWidgetQuestions();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });

        return  widgetQuestions;
    }



    public LiveData<List<Result>> getAllResult(){
        return results;
    }

    public void  getQuestionsFromAPI(){
        String subject = sharedPreferences.getString("subject", "english");

        apiInterface.getQuestions(subject)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(new Observer<QuestionResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(QuestionResponse questionResponse) {
                        for (Question question:questionResponse.getData()
                             ) {
                            appDatabase.databaseDAO().inSertQuestions(QuestionConverter.convertQuestionToEntity(question));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });


            }


            public void inSertResult(final Result result){

                Completable.fromAction(new Action() {
                    @Override
                    public void run() throws Exception {
                        appDatabase.resultDAO().inSertResult(result);
                    }
                }).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new CompletableObserver() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onComplete() {

                            }

                            @Override
                            public void onError(Throwable e) {

                            }
                        });

            }
            public  void deleteResult(final int resultId){
                Completable.fromAction(new Action() {
                    @Override
                    public void run() throws Exception {
                        appDatabase.resultDAO().deleteById(resultId);
                    }
                }).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new CompletableObserver() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onComplete() {

                            }

                            @Override
                            public void onError(Throwable e) {

                            }
                        });

            }



}
