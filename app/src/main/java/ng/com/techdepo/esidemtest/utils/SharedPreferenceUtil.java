package ng.com.techdepo.esidemtest.utils;

import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Inject;

public class SharedPreferenceUtil {
    Context context;
    @Inject  SharedPreferences sharedPreferences;

    public SharedPreferenceUtil(Context context) {
        this.context = context;
        ((QuestionsApplication) context).getAppComponent().inject(this);
    }

    public  String subject(){

        String subject = sharedPreferences.getString("subject", "english");

        return  subject;
    }

    public  int numberOfQuestion(){

        int numberOfQuestion = sharedPreferences.getInt("number_of_question", 5);

        return numberOfQuestion;
    }

    public void setSubject(String subject){

        sharedPreferences.edit().putString("subject", subject).apply();

           }

}
