package ng.com.techdepo.esidemtest.utils;

import android.widget.TextView;

public class TextViewVisibilityUtil {

    public static void disAbleView(TextView option1,TextView option2,TextView option3, TextView option4) {
        option1.setClickable(false);
        option2.setClickable(false);
        option3.setClickable(false);
        option4.setClickable(false);

    }

    public static void enAbleView(TextView option1,TextView option2,TextView option3, TextView option4) {
        option1.setClickable(true);
        option2.setClickable(true);
        option3.setClickable(true);
        option4.setClickable(true);
    }
}
