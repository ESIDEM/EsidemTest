package ng.com.techdepo.esidemtest.fragments;


import android.app.Dialog;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;

import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import ng.com.techdepo.esidemtest.R;
import ng.com.techdepo.esidemtest.activities.MainActivity;
import ng.com.techdepo.esidemtest.database.QuestionEntity;
import ng.com.techdepo.esidemtest.database.Result;
import ng.com.techdepo.esidemtest.databinding.QuestionLayoutBinding;
import ng.com.techdepo.esidemtest.databinding.ResultDialogueBinding;
import ng.com.techdepo.esidemtest.models.Question;
import ng.com.techdepo.esidemtest.utils.CounterColorUtil;
import ng.com.techdepo.esidemtest.utils.QuestionBackground;
import ng.com.techdepo.esidemtest.utils.QuestionConverter;
import ng.com.techdepo.esidemtest.utils.SharedPreferenceUtil;
import ng.com.techdepo.esidemtest.utils.TextViewVisibilityUtil;
import ng.com.techdepo.esidemtest.view_model.QuestionsViewModel;



/**
 * A simple {@link Fragment} subclass.
 */
public class QuestionFragment extends Fragment{


    TextView option1;
    TextView option2;
    TextView option3;
    TextView option4;
    TextView sectionText;
    TextView questionTimer;
    TextView questionTextView;
    Button netxButton;
    TextView questionYear;
    Boolean isTestRunning = true;
    private long timeCountInMilliSeconds = 20000;
    private CountDownTimer countDownTimer;
    int selected;
    Question question;
    private ProgressBar progressBarCircle;
    int numberOfQuestions = 0;
    int correctAnswers = 0;
    QuestionLayoutBinding questionLayoutBinding;
    ResultDialogueBinding resultDialogueBinding;
    public  ArrayList<Question> questionList = new ArrayList<>();
    QuestionsViewModel questionsViewModel;


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
        setRetainInstance(true);
        questionsViewModel = ViewModelProviders.of(getActivity()).get(QuestionsViewModel.class);
            dialog();
            fetchQuestions();
            }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        questionLayoutBinding = DataBindingUtil.inflate(inflater, R.layout.question_layout, container, false);
        // View rootView = inflater.inflate(R.layout.question_layout, container, false);
        getActivity().setTitle("Time Trial");
        View view = questionLayoutBinding.getRoot();
        setTimeValue(SharedPreferenceUtil.subject(getActivity()));
        bindViews();

        ClickHandler clickHandler = new ClickHandler();
        questionLayoutBinding.setOnclick(clickHandler);

        return view;

    }


    @Override
    public void onPause() {
        super.onPause();
        countDownTimer.cancel();
    }


    @Override
    public void onStop() {
        super.onStop();
        countDownTimer.cancel();

    }

    @Override
    public void onResume() {
        super.onResume();
        setTimeValue(SharedPreferenceUtil.subject(getActivity()));

    }


    private void setTimeValue(String subject) {

        if (subject.equals("mathematics")) {
            timeCountInMilliSeconds = 50000;
        } else {
            timeCountInMilliSeconds = 30000;
        }
    }

    private void startCountDownTimer() {
        timerStatus = TimerStatus.STARTED;

        countDownTimer = new CountDownTimer(timeCountInMilliSeconds, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                CounterColorUtil.setTimerColor(millisUntilFinished, questionTimer, getActivity());
                questionTimer.setText(String.valueOf(millisUntilFinished / 1000));

            }

            @Override
            public void onFinish() {


                // changing the timer status to stopped
                timerStatus = TimerStatus.STOPPED;
                isTestRunning = false;
                TextViewVisibilityUtil.disAbleView(option1,option2,option3,option4);
                selected = 0;
                showViews();
                checkAnswer(question);
                checkNumberOfQuestions();

            }

        }.start();
        countDownTimer.start();
    }


    private void stopTimer() {

        if (timerStatus == TimerStatus.STARTED) {
            countDownTimer.cancel();
            isTestRunning = false;
        }
    }


    private void fetchQuestions(){

        questionsViewModel.getQuestions().observe(getActivity(), new Observer<List<QuestionEntity>>() {
            @Override
            public void onChanged(@Nullable List<QuestionEntity> questionEntities) {
                questionList.clear();
                for (QuestionEntity questionEntity: questionEntities){

                    questionList.add(QuestionConverter.converEntityToQuestion(questionEntity));
                }


            }
        });


    }

       private void selectRandom(List<Question> questionList) {
        if (questionList.size()!=0) {
            Random randomizer = new Random();
            question = questionList.get(randomizer.nextInt(questionList.size()));
            questionLayoutBinding.setQuestion(question);
            questionTextView.setText(Html.fromHtml(question.getQuestion()));
            startCountDownTimer();
            questionList.remove(question);
        }else {

            Toast.makeText(getActivity(), R.string.no_question_available,Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getActivity(),MainActivity.class));
            getActivity().finish();
        }

    }

    public void nextButton() {
        TextViewVisibilityUtil.enAbleView(option1,option2,option3,option4);
        selectRandom(questionList);
        isTestRunning = true;

        showViews();
        QuestionBackground.reSetQuetionBackground(getActivity(), option1, option2, option3, option4);
    }

    private void showViews() {

        if (isTestRunning) {

            netxButton.setVisibility(View.GONE);
            questionTimer.setVisibility(View.VISIBLE);
        } else {
            netxButton.setVisibility(View.VISIBLE);
            questionTimer.setVisibility(View.GONE);
        }
    }

    private void checkAnswer(Question question) {

        if (selected == 0) {

            if (question.getAnswer().equals("a")) {
                option1.setBackground(QuestionBackground.getCorrectQuetionBackground(getActivity()));
            } else if (question.getAnswer().equals("b")) {
                option2.setBackground(QuestionBackground.getCorrectQuetionBackground(getActivity()));
            } else if (question.getAnswer().equals("c")) {
                option3.setBackground(QuestionBackground.getCorrectQuetionBackground(getActivity()));
            } else if (question.getAnswer().equals("d")) {
                option4.setBackground(QuestionBackground.getCorrectQuetionBackground(getActivity()));
            }
        } else {

            TextView selectedTextView = (TextView) getActivity().findViewById(selected);

            if (question.getAnswer().equals("a")) {
                option1.setBackground(QuestionBackground.getCorrectQuetionBackground(getActivity()));
                if (selectedTextView.getId() != option1.getId()) {
                    selectedTextView.setBackground(QuestionBackground.getWrongQuetionBackground(getActivity()));
                } else {
                    selectedTextView.setBackground(QuestionBackground.getCorrectQuetionBackground(getActivity()));
                    correctAnswers = correctAnswers + 1;
                }


            } else if (question.getAnswer().equals("b")) {
                option2.setBackground(QuestionBackground.getCorrectQuetionBackground(getActivity()));
                if (selectedTextView.getId() != option2.getId()) {
                    selectedTextView.setBackground(QuestionBackground.getWrongQuetionBackground(getActivity()));
                } else {
                    selectedTextView.setBackground(QuestionBackground.getCorrectQuetionBackground(getActivity()));
                    correctAnswers = correctAnswers + 1;
                }
            } else if (question.getAnswer().equals("c")) {
                option3.setBackground(QuestionBackground.getCorrectQuetionBackground(getActivity()));
                if (selectedTextView.getId() != option3.getId()) {
                    selectedTextView.setBackground(QuestionBackground.getWrongQuetionBackground(getActivity()));
                } else {
                    selectedTextView.setBackground(QuestionBackground.getCorrectQuetionBackground(getActivity()));
                    correctAnswers = correctAnswers + 1;
                }
            } else if (question.getAnswer().equals("d")) {
                option4.setBackground(QuestionBackground.getCorrectQuetionBackground(getActivity()));
                if (selectedTextView.getId() != option4.getId()) {
                    selectedTextView.setBackground(QuestionBackground.getWrongQuetionBackground(getActivity()));
                } else {
                    selectedTextView.setBackground(QuestionBackground.getCorrectQuetionBackground(getActivity()));
                    correctAnswers = correctAnswers + 1;
                }
            }
        }

        numberOfQuestions = numberOfQuestions + 1;
        checkNumberOfQuestions();

    }



    private void deLay() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {


                checkAnswer(question);
                showViews();


            }
        }, 1000);
    }


    public void showDialog() {
        QuestionBackground.reSetQuetionBackground(getActivity(), option1, option2, option3, option4);
        countDownTimer.cancel();
        netxButton.setVisibility(View.GONE);
        isTestRunning =false;
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        resultDialogueBinding = DataBindingUtil.inflate(LayoutInflater.from(getActivity()), R.layout.result_dialogue, null, false);
        dialog.setContentView(resultDialogueBinding.getRoot());
        progressBarCircle = resultDialogueBinding.progressBarCircle;
        progressBarCircle.setMax(SharedPreferenceUtil.numberOfQuestion(getActivity()));
        TextView correctAnswerText = resultDialogueBinding.scoreTextView;
        correctAnswerText.setText(correctAnswers + "/"+SharedPreferenceUtil.numberOfQuestion(getActivity()));
        Button reTakeButton = resultDialogueBinding.reTakeButton;
        progressBarCircle.setProgress(correctAnswers);
       // DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        Result result = new Result(SharedPreferenceUtil.subject(getActivity()).substring(0, 1).toUpperCase() +
                SharedPreferenceUtil.subject(getActivity()).substring(1),
                SharedPreferenceUtil.numberOfQuestion(getActivity()),correctAnswers,date);
        questionsViewModel.insertResult(result);
        reTakeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
                isTestRunning = true;
                selectRandom(questionList);
                correctAnswers = 0;
                numberOfQuestions = 0;
                questionTimer.setVisibility(View.VISIBLE);
                TextViewVisibilityUtil.enAbleView(option1,option2,option3,option4);
                timerStatus = TimerStatus.STARTED;
                showViews();



            }
        });

        Button homeButton = resultDialogueBinding.homeButton;
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

                startActivity(new Intent(getActivity(), MainActivity.class));
                getActivity().finish();
            }
        });

        dialog.show();

    }

    private void checkNumberOfQuestions() {
        if (numberOfQuestions == SharedPreferenceUtil.numberOfQuestion(getActivity())) {
            isTestRunning = false;
            showDialog();

        }
    }

    private void bindViews() {
        option1 = questionLayoutBinding.option1;
        option2 = questionLayoutBinding.option2;
        option3 = questionLayoutBinding.option3;
        option4 = questionLayoutBinding.option4;
       questionTextView = questionLayoutBinding.questionTextView;
        sectionText = questionLayoutBinding.sectionTextView;
        questionTimer = questionLayoutBinding.questionTimer;
        netxButton = questionLayoutBinding.nextButton;
        netxButton.setVisibility(View.GONE);
        if (!SharedPreferenceUtil.subject(getActivity()).equals("english")){
            sectionText.setVisibility(View.GONE);
        }else {
            sectionText.setVisibility(View.VISIBLE);
        }

        questionLayoutBinding.subjectTextView.setText(SharedPreferenceUtil.subject(getActivity()).substring(0, 1).toUpperCase() +
                SharedPreferenceUtil.subject(getActivity()).substring(1));
    }

    public class ClickHandler {
        public void onClick(View view) {
            TextViewVisibilityUtil.disAbleView(option1,option2,option3,option4);
            int id = view.getId();
            selected = view.getId();
            if (id == R.id.option_1) {
                option1.setBackground(QuestionBackground.getSelectedQuetionBackground(getActivity()));
                stopTimer();

                deLay();
              //  showViews();
            } else if (id == R.id.option_2) {
                option2.setBackground(QuestionBackground.getSelectedQuetionBackground(getActivity()));
                stopTimer();

                deLay();
               // showViews();
            } else if (id == R.id.option_3) {
                option3.setBackground(QuestionBackground.getSelectedQuetionBackground(getActivity()));
                stopTimer();

                deLay();
               // showViews();
            } else if (id == R.id.option_4) {
                option4.setBackground(QuestionBackground.getSelectedQuetionBackground(getActivity()));
                stopTimer();

                deLay();
              //  showViews();
            } else if (id == R.id.next_button) {
                nextButton();
            }

        }


    }

    void dialog(){

        final String[] items = {"5", "10", "15","20"};

        AlertDialog.Builder dialogue = new AlertDialog.Builder(getActivity());
        //alt_bld.setIcon(R.drawable.icon);

         dialogue.setTitle(R.string.number_of_question_dialogue_title);
         dialogue.setCancelable(false);
        dialogue.setSingleChoiceItems(items, 0, new DialogInterface
                .OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {

                SharedPreferences prefs = getActivity().getSharedPreferences("ng.com.techdepo.esidemtest", Context.MODE_APPEND);
                 prefs.edit().putInt("number_of_question", Integer.parseInt(items[item])).apply();
            }
        }).setPositiveButton(R.string.continue_, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                selectRandom(questionList);
            }
        });
        AlertDialog alert = dialogue.create();
        alert.show();

    }
}
