package ng.com.techdepo.esidemtest.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;

import ng.com.techdepo.esidemtest.R;

public class QuestionBackground {


    public static Drawable getSelectedQuetionBackground(Context context){

        Resources res = context.getResources();
        Drawable drawable = ResourcesCompat.getDrawable(res, R.drawable.question_selected_background, null);
        return drawable;
    }

    public static Drawable getWrongQuetionBackground(Context context){

        Resources res = context.getResources();
        Drawable drawable = ResourcesCompat.getDrawable(res, R.drawable.question_answer_wrong_background, null);
        return drawable;
    }

    public static Drawable getCorrectQuetionBackground(Context context){

        Resources res = context.getResources();
        Drawable drawable = ResourcesCompat.getDrawable(res, R.drawable.question_answer_correct_background, null);
        return drawable;
    }

    public static Drawable getNormalQuetionBackground(Context context){

        Resources res = context.getResources();
        Drawable drawable = ResourcesCompat.getDrawable(res, R.drawable.question_text_background, null);
        return drawable;
    }
}
