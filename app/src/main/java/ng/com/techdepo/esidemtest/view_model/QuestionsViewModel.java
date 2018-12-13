package ng.com.techdepo.esidemtest.view_model;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.annotation.NonNull;
import java.util.List;

import javax.inject.Inject;

import ng.com.techdepo.esidemtest.database.QuestionEntity;
import ng.com.techdepo.esidemtest.database.Result;
import ng.com.techdepo.esidemtest.repository.AppRepository;
import ng.com.techdepo.esidemtest.utils.QuestionsApplication;

public class QuestionsViewModel extends AndroidViewModel {

@Inject
    AppRepository appRepository;
    LiveData<List<QuestionEntity>> quetions;
    LiveData<List<Result>> results;
    public QuestionsViewModel(@NonNull Application application) {
        super(application);
        ((QuestionsApplication) application.getApplicationContext()).getAppComponent().inject(this);
        appRepository.getQuestionsFromAPI();
        quetions = appRepository.getQuestionsFromDb();
        results = appRepository.getAllResult();
    }

public LiveData<List<QuestionEntity>> getQuestions(){
        return  quetions;
}

public void getNewQuestions(){
        appRepository.getQuestionsFromAPI();
}

public void insertResult(Result result){

        appRepository.inSertResult(result);
}

public LiveData<List<Result>> getAllResult(){
        return results;
}

public void deleteResult(int resultId){

        appRepository.deleteResult(resultId);
}
}


