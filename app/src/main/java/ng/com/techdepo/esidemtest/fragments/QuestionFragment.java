package ng.com.techdepo.esidemtest.fragments;


import android.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import ng.com.techdepo.esidemtest.R;
import ng.com.techdepo.esidemtest.utils.CounterColorUtil;
import ng.com.techdepo.esidemtest.utils.QuestionBackground;


/**
 * A simple {@link Fragment} subclass.
 */
public class QuestionFragment extends Fragment implements View.OnClickListener {

    public static String TEST_TYPE = "test_type";

    TextView option1;
    TextView option2;
    TextView option3;
    TextView option4;
    TextView questionTimer;
    Button netxButton;
    private long timeCountInMilliSeconds = 20000;
    SharedPreferences prefs = null;
    String subject;
    private CountDownTimer countDownTimer;

    private enum TimerStatus {
        STARTED,
        STOPPED
    }

    private TimerStatus timerStatus = TimerStatus.STOPPED;


    public QuestionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.question_layout, container, false);
        prefs = this.getActivity().getSharedPreferences("ng.com.techdepo.esidemtest", Context.MODE_APPEND);
        subject = prefs.getString("subject","chemistry");
        setTimeValue(subject);
        option1 = (TextView) rootView.findViewById(R.id.option_1);
        option2 = (TextView) rootView.findViewById(R.id.option_2);
        option3 = (TextView) rootView.findViewById(R.id.option_3);
        option4 = (TextView) rootView.findViewById(R.id.option_4);
        questionTimer = (TextView) rootView.findViewById(R.id.question_timer);
        netxButton = (Button) rootView.findViewById(R.id.next_button);
        netxButton.setVisibility(View.GONE);
        Intent intent = getActivity().getIntent();
        option1.setOnClickListener(this);
        option2.setOnClickListener(this);
        option3.setOnClickListener(this);
        option4.setOnClickListener(this);
        showTimer(intent.getStringExtra(TEST_TYPE));

        return  rootView;

    }

    @Override
    public void onClick(View v) {


        int id = v.getId();
        if(id ==R.id.option_1){
            option1.setBackground(QuestionBackground.getSelectedQuetionBackground(getActivity()));
        }else if(id ==R.id.option_2){
            Toast.makeText(v.getContext(),"Option 2",Toast.LENGTH_LONG).show();
        }else if (id ==R.id.option_3){
            Toast.makeText(v.getContext(),"Option 3",Toast.LENGTH_LONG).show();
        }else if(id ==R.id.option_4) {
            Toast.makeText(v.getContext(),"Option 4",Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        stopTimer();
    }

    @Override
    public void onStop() {
        super.onStop();
        stopTimer();
    }

    @Override
    public void onResume() {
        super.onResume();
        subject = prefs.getString("subject","chemistry");
        setTimeValue(subject);
    }

    private void setTimeValue(String subject){
        
        if(subject.equals("mathematics")){
            timeCountInMilliSeconds = 50000;
        }else {
            timeCountInMilliSeconds = 30000;
        }
    }

    private void startCountDownTimer() {
        timerStatus = TimerStatus.STARTED;

        countDownTimer = new CountDownTimer(timeCountInMilliSeconds, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                CounterColorUtil.setTimerColor(millisUntilFinished,questionTimer,getActivity());
            questionTimer.setText(String.valueOf(millisUntilFinished/1000));

            }

            @Override
            public void onFinish() {


                // changing the timer status to stopped
                timerStatus = TimerStatus.STOPPED;
                questionTimer.setVisibility(View.GONE);
                netxButton.setVisibility(View.VISIBLE);

            }

        }.start();
        countDownTimer.start();
    }

    private void showTimer(String type){
     if(type.equals("time")){
         startCountDownTimer();
     }else if(type.equals("classic")){
         questionTimer.setVisibility(View.GONE);
     }
    }

    private void stopTimer(){

        if(timerStatus==TimerStatus.STARTED){
            countDownTimer.cancel();
        }
    }
}
