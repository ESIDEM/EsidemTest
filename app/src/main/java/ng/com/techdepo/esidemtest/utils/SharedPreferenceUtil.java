package ng.com.techdepo.esidemtest.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceUtil {

    public static String subject(Context context){
        SharedPreferences prefs = context.getSharedPreferences("ng.com.techdepo.esidemtest", Context.MODE_APPEND);
        String subject = prefs.getString("subject", "chemistry");

        return  subject;
    }

    public static int numberOfQuestion(Context context){
        SharedPreferences prefs = context.getSharedPreferences("ng.com.techdepo.esidemtest", Context.MODE_APPEND);
        int numberOfQuestion = prefs.getInt("number_of_question", 5);

        return numberOfQuestion;
    }

    public static void setSubject(Context context,String subject){
        SharedPreferences prefs = context.getSharedPreferences("ng.com.techdepo.esidemtest", Context.MODE_APPEND);
        prefs.edit().putString("subject", subject).apply();

           }

}
