package ng.com.techdepo.esidemtest.view_model;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import ng.com.techdepo.esidemtest.models.QuestionResponse;
import ng.com.techdepo.esidemtest.repository.AppRepository;

public class QuestionsViewModel extends AndroidViewModel {

   private MediatorLiveData<QuestionResponse> questionResponseLiveData;
    private AppRepository appRepository;
    public QuestionsViewModel(@NonNull Application application) {
        super(application);

        questionResponseLiveData = new MediatorLiveData<>();
        appRepository = new AppRepository(getApplication());
    }

    public LiveData<QuestionResponse> getQuestions(){
        questionResponseLiveData.addSource(appRepository.getQuestionsFromAPI(), new Observer<QuestionResponse>() {
            @Override
            public void onChanged(@Nullable QuestionResponse questionResponse) {
                questionResponseLiveData.setValue(questionResponse);
            }
        });

        return questionResponseLiveData;
    }
}
