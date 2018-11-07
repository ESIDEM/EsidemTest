package ng.com.techdepo.esidemtest.fragments;


import android.app.Dialog;
import android.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;

import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ng.com.techdepo.esidemtest.R;
import ng.com.techdepo.esidemtest.activities.MainActivity;
import ng.com.techdepo.esidemtest.api.ApiInterface;
import ng.com.techdepo.esidemtest.constants.Constants;
import ng.com.techdepo.esidemtest.databinding.QuestionLayoutBinding;
import ng.com.techdepo.esidemtest.models.Question;
import ng.com.techdepo.esidemtest.models.QuestionResponse;
import ng.com.techdepo.esidemtest.utils.CounterColorUtil;
import ng.com.techdepo.esidemtest.utils.NetworkUtil;
import ng.com.techdepo.esidemtest.utils.QuestionBackground;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 */
public class QuestionFragment extends Fragment  {

    public static String TEST_TYPE = "test_type";

    TextView option1;
    TextView option2;
    TextView option3;
    TextView option4;
    TextView questionTimer;
    TextView quentionTextView;
    Button netxButton;
    TextView questionYear;
    Boolean isTestRunning = true;
    private long timeCountInMilliSeconds = 20000;
    SharedPreferences prefs = null;
    String subject;
    private CountDownTimer countDownTimer;
    int selected;
    Question question;
    private ProgressBar progressBarCircle;
    int numberOfQuestions = 0;
    int correctAnswers = 0;
    QuestionLayoutBinding questionLayoutBinding;

    private ArrayList<Question> questionList = new ArrayList<>();

    private enum TimerStatus {
        STARTED,
        STOPPED
    }

    private TimerStatus timerStatus = TimerStatus.STOPPED;


    public QuestionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

         }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        questionLayoutBinding = DataBindingUtil.inflate(inflater,R.layout.question_layout, container, false);
       // View rootView = inflater.inflate(R.layout.question_layout, container, false);
        View view = questionLayoutBinding.getRoot();
        prefs = this.getActivity().getSharedPreferences("ng.com.techdepo.esidemtest", Context.MODE_APPEND);
        subject = prefs.getString("subject","chemistry");
        setTimeValue(subject);
         bindViews();

        netxButton = (Button) view.findViewById(R.id.next_button);
        netxButton.setVisibility(View.GONE);
        Intent intent = getActivity().getIntent();
        showTimer(intent.getStringExtra(TEST_TYPE));

        if(NetworkUtil.isNetworkAvailable(getActivity())){
            getQuestions(subject);
        }

        ClickHandler clickHandler = new ClickHandler();
        questionLayoutBinding.setOnclick(clickHandler);

      return  view;

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
                isTestRunning = false;
                disAbleView();
                selected = 0;
                showViews();
                checkAnswer(question);
                checkNumberOfQuestions();

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
            isTestRunning =false;
        }
    }

    private void getQuestions(String subject){

        Retrofit retrofit = new Retrofit.Builder().baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()).build();

        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

         apiInterface.getQuestions(subject).enqueue(new Callback<QuestionResponse>() {
            @Override
            public void onResponse(Call<QuestionResponse> call, Response<QuestionResponse> response) {
                if(response.isSuccessful()){

                    QuestionResponse questionResponse = response.body();
                   questionList.addAll(questionResponse.getData());
                    selectRandom(questionList);
                }
            }

            @Override
            public void onFailure(Call<QuestionResponse> call, Throwable t) {
                Toast.makeText(getActivity(),t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void selectRandom(List<Question> questionList){
        Random randomizer = new Random();
        question = questionList.get(randomizer.nextInt(questionList.size()));
        questionLayoutBinding.setQuestion(question);
          }

    public void nextButton(){
        enAbleView();
        selectRandom(questionList);
        isTestRunning = true;
        startCountDownTimer();
        showViews();
        QuestionBackground.reSetQuetionBackground(getActivity(),option1,option2,option3,option4);
    }

    private void showViews(){

        if(isTestRunning){

            netxButton.setVisibility(View.GONE);
            questionTimer.setVisibility(View.VISIBLE);
        }else {
            netxButton.setVisibility(View.VISIBLE);
            questionTimer.setVisibility(View.GONE);
        }
    }

    private void checkAnswer(Question question){

        if (selected==0){

            if (question.getAnswer().equals("a")){
                option1.setBackground(QuestionBackground.getCorrectQuetionBackground(getActivity()));
            }else if (question.getAnswer().equals("b")){
                option2.setBackground(QuestionBackground.getCorrectQuetionBackground(getActivity()));
            }else if (question.getAnswer().equals("c")){
                option3.setBackground(QuestionBackground.getCorrectQuetionBackground(getActivity()));
            }else if (question.getAnswer().equals("d")){
                option4.setBackground(QuestionBackground.getCorrectQuetionBackground(getActivity()));
            }
        }else {

            TextView selectedTextView = (TextView) getActivity().findViewById(selected);
            if (question.getAnswer().equals("a")) {
                 option1.setBackground(QuestionBackground.getCorrectQuetionBackground(getActivity()));
                if (selectedTextView.getId() != option1.getId()) {
                    selectedTextView.setBackground(QuestionBackground.getWrongQuetionBackground(getActivity()));
                } else {
                    selectedTextView.setBackground(QuestionBackground.getCorrectQuetionBackground(getActivity()));
                    correctAnswers=correctAnswers+1;
                }


            } else if (question.getAnswer().equals("b")) {
                  option2.setBackground(QuestionBackground.getCorrectQuetionBackground(getActivity()));
                if (selectedTextView.getId() != option2.getId()) {
                    selectedTextView.setBackground(QuestionBackground.getWrongQuetionBackground(getActivity()));
                } else {
                    selectedTextView.setBackground(QuestionBackground.getCorrectQuetionBackground(getActivity()));
                    correctAnswers=correctAnswers+1;
                }
            } else if (question.getAnswer().equals("c")) {
                    option3.setBackground(QuestionBackground.getCorrectQuetionBackground(getActivity()));
                if (selectedTextView.getId() != option3.getId()) {
                    selectedTextView.setBackground(QuestionBackground.getWrongQuetionBackground(getActivity()));
                } else {
                    selectedTextView.setBackground(QuestionBackground.getCorrectQuetionBackground(getActivity()));
                    correctAnswers=correctAnswers+1;
                }
            } else if (question.getAnswer().equals("d")) {
                option4.setBackground(QuestionBackground.getCorrectQuetionBackground(getActivity()));
                if (selectedTextView.getId() != option4.getId()) {
                    selectedTextView.setBackground(QuestionBackground.getWrongQuetionBackground(getActivity()));
                } else {
                    selectedTextView.setBackground(QuestionBackground.getCorrectQuetionBackground(getActivity()));
                    correctAnswers=correctAnswers+1;
                }
            }
        }

        numberOfQuestions = numberOfQuestions+1;
        checkNumberOfQuestions();

    }

    private void disAbleView(){
        option1.setClickable(false);
        option2.setClickable(false);
        option3.setClickable(false);
        option4.setClickable(false);
    }

    private void enAbleView(){
        option1.setClickable(true);
        option2.setClickable(true);
        option3.setClickable(true);
        option4.setClickable(true);
    }

     private void deLay(){

        new Handler().postDelayed (new Runnable() {
            @Override
            public void run() {


                checkAnswer(question);


            }
        }, 1000);
    }

    public void showDialog(){
        QuestionBackground.reSetQuetionBackground(getActivity(),option1,option2,option3,option4);
        countDownTimer.cancel();

        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.result_dialogue);
        progressBarCircle = (ProgressBar) dialog.findViewById(R.id.progressBarCircle);
        progressBarCircle.setMax(10);
        TextView correctAnswerText=(TextView)dialog.findViewById(R.id.score_textView);
        correctAnswerText.setText(correctAnswers+"/10");
        Button reTakeButton = (Button) dialog.findViewById(R.id.re_take_button);
        progressBarCircle.setProgress(correctAnswers);
        reTakeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
                isTestRunning=true;
                selectRandom(questionList);
                correctAnswers = 0;
                numberOfQuestions = 0;




            }
        });

        Button homeButton = (Button) dialog.findViewById(R.id.home_button);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

                startActivity(new Intent(getActivity(),MainActivity.class));
            }
        });

        dialog.show();

    }

    private void checkNumberOfQuestions(){
        if (numberOfQuestions==5){
            isTestRunning = false;
            showDialog();
        }
    }

    private void bindViews(){
        option1 = questionLayoutBinding.option1;
        option2 = questionLayoutBinding.option2;
        option3 = questionLayoutBinding.option3;
        option4 = questionLayoutBinding.option4;
        questionTimer = questionLayoutBinding.questionTimer;
            }

            public class ClickHandler{
        public void onClick(View view){
            disAbleView();
            int id = view.getId();
            selected = view.getId();
            if(id ==R.id.option_1){
                option1.setBackground(QuestionBackground.getSelectedQuetionBackground(getActivity()));
                stopTimer();
                showViews();
                deLay();
            }else if(id ==R.id.option_2){
                option2.setBackground(QuestionBackground.getSelectedQuetionBackground(getActivity()));
                stopTimer();
                showViews();
                deLay();
            }else if (id ==R.id.option_3){
                option3.setBackground(QuestionBackground.getSelectedQuetionBackground(getActivity()));
                stopTimer();
                showViews();
                deLay();
            }else if(id ==R.id.option_4) {
                option4.setBackground(QuestionBackground.getSelectedQuetionBackground(getActivity()));
                stopTimer();
                showViews();
                deLay();
            }else if(id==R.id.next_button){
                nextButton();
            }

        }

            }
}
