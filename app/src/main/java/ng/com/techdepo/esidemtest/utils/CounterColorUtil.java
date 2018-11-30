package ng.com.techdepo.esidemtest.utils;

import android.content.Context;
import androidx.core.content.ContextCompat;
import android.widget.TextView;

import ng.com.techdepo.esidemtest.R;

public class CounterColorUtil {

    public static void setTimerColor(long time, TextView textView, Context context){



        if(time<=30000 & time >=20000){
            textView.setTextColor(ContextCompat.getColor(context, R.color.colorAccent));
        }else if(time<=20000 & time >=10000){
            textView.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
        }else if(time<=10000 ){
            textView.setTextColor(ContextCompat.getColor(context, R.color.red));
        }
    }
}
