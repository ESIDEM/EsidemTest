package ng.com.techdepo.esidemtest.view_model;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import java.util.List;
import ng.com.techdepo.esidemtest.database.AppDatabase;
import ng.com.techdepo.esidemtest.database.QuestionEntity;
import ng.com.techdepo.esidemtest.repository.AppRepository;

public class QuestionsViewModel extends AndroidViewModel {


    private AppRepository appRepository;
    AppDatabase appDatabase;
    LiveData<List<QuestionEntity>> quetions;
    public QuestionsViewModel(@NonNull Application application) {
        super(application);

        appDatabase = AppDatabase.getInstance(this.getApplication());
        appRepository = new AppRepository(getApplication());
        appRepository.getQuestionsFromAPI();
        quetions = appRepository.getQuestionsFromDb();
    }

public LiveData<List<QuestionEntity>> getQuestions(){
        return  quetions;
}

public void getNewQuestions(){
        appRepository.getQuestionsFromAPI();
}
}
