package ng.com.techdepo.esidemtest.view_model;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import java.util.List;
import ng.com.techdepo.esidemtest.database.QuestionEntity;
import ng.com.techdepo.esidemtest.database.Result;
import ng.com.techdepo.esidemtest.repository.AppRepository;

public class QuestionsViewModel extends AndroidViewModel {


    private AppRepository appRepository;
    LiveData<List<QuestionEntity>> quetions;
    LiveData<List<Result>> results;
    public QuestionsViewModel(@NonNull Application application) {
        super(application);

        appRepository = new AppRepository(getApplication());
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
}


