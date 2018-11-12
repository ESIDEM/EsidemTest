package ng.com.techdepo.esidemtest.fragments;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import ng.com.techdepo.esidemtest.activities.MainActivity;
import ng.com.techdepo.esidemtest.database.QuestionEntity;
import ng.com.techdepo.esidemtest.databinding.FragmentQuestionBinding;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ng.com.techdepo.esidemtest.R;
import ng.com.techdepo.esidemtest.models.Question;
import ng.com.techdepo.esidemtest.utils.QuestionBackground;
import ng.com.techdepo.esidemtest.utils.QuestionConverter;
import ng.com.techdepo.esidemtest.utils.SharedPreferenceUtil;
import ng.com.techdepo.esidemtest.utils.TextViewVisibilityUtil;
import ng.com.techdepo.esidemtest.view_model.QuestionsViewModel;

/**
 * A placeholder fragment containing a simple view.
 */
public class QuestionActivityFragment extends Fragment {

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
    FragmentQuestionBinding fragmentQuestionBinding;

    int selected;
    Question question;

     public  ArrayList<Question> questionList = new ArrayList<>();


    public QuestionActivityFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fetchQuestions();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentQuestionBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_question, container, false);
        getActivity().setTitle(getString(R.string.classic_test));
        View view = fragmentQuestionBinding.getRoot();
        ClickHandler clickHandler = new ClickHandler();
        fragmentQuestionBinding.setOnclick(clickHandler);
        bindViews();
        return view;
    }

    private void fetchQuestions(){
        QuestionsViewModel questionsViewModel = ViewModelProviders.of(this).get(QuestionsViewModel.class);
        questionsViewModel.getQuestions().observe(this, new Observer<List<QuestionEntity>>() {
            @Override
            public void onChanged(@Nullable List<QuestionEntity> questionEntities) {
                questionList.clear();
                for (QuestionEntity questionEntity: questionEntities){

                    questionList.add(QuestionConverter.converEntityToQuestion(questionEntity));
                    fragmentQuestionBinding.questionNumbers.setText(String.valueOf(questionList.size()));
                    selectRandom(questionList);
                }


            }
        });


    }

    private void selectRandom(List<Question> questionList) {
        if (questionList.size()!=0) {
            Random randomizer = new Random();
            question = questionList.get(randomizer.nextInt(questionList.size()));
            fragmentQuestionBinding.setQuestion(question);
            questionTextView.setText(Html.fromHtml(question.getQuestion()));
            questionList.remove(question);
            fragmentQuestionBinding.questionNumbers.setText(String.valueOf(questionList.size()));
        }else {

            Toast.makeText(getActivity(), R.string.no_question_available,Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getActivity(),MainActivity.class));
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

        } else {
            netxButton.setVisibility(View.VISIBLE);

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

                }


            } else if (question.getAnswer().equals("b")) {
                option2.setBackground(QuestionBackground.getCorrectQuetionBackground(getActivity()));
                if (selectedTextView.getId() != option2.getId()) {
                    selectedTextView.setBackground(QuestionBackground.getWrongQuetionBackground(getActivity()));
                } else {
                    selectedTextView.setBackground(QuestionBackground.getCorrectQuetionBackground(getActivity()));

                }
            } else if (question.getAnswer().equals("c")) {
                option3.setBackground(QuestionBackground.getCorrectQuetionBackground(getActivity()));
                if (selectedTextView.getId() != option3.getId()) {
                    selectedTextView.setBackground(QuestionBackground.getWrongQuetionBackground(getActivity()));
                } else {
                    selectedTextView.setBackground(QuestionBackground.getCorrectQuetionBackground(getActivity()));

                }
            } else if (question.getAnswer().equals("d")) {
                option4.setBackground(QuestionBackground.getCorrectQuetionBackground(getActivity()));
                if (selectedTextView.getId() != option4.getId()) {
                    selectedTextView.setBackground(QuestionBackground.getWrongQuetionBackground(getActivity()));
                } else {
                    selectedTextView.setBackground(QuestionBackground.getCorrectQuetionBackground(getActivity()));

                }
            }
        }

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
    private void bindViews() {
        option1 = fragmentQuestionBinding.option1;
        option2 = fragmentQuestionBinding.option2;
        option3 = fragmentQuestionBinding.option3;
        option4 = fragmentQuestionBinding.option4;
        questionTextView = fragmentQuestionBinding.questionTextView;
        sectionText = fragmentQuestionBinding.sectionTextView;
        netxButton = fragmentQuestionBinding.nextButton;
        netxButton.setVisibility(View.GONE);
        if (!SharedPreferenceUtil.subject(getActivity()).equals("english")){
            sectionText.setVisibility(View.GONE);
        }else {
            sectionText.setVisibility(View.VISIBLE);
        }

        fragmentQuestionBinding.subjectTextView.setText(SharedPreferenceUtil.subject(getActivity()).substring(0, 1).toUpperCase() +
                SharedPreferenceUtil.subject(getActivity()).substring(1));
    }

    public class ClickHandler {
        public void onClick(View view) {
            TextViewVisibilityUtil.disAbleView(option1,option2,option3,option4);
            isTestRunning =false;
            int id = view.getId();
            selected = view.getId();
            if (id == R.id.option_1) {
                option1.setBackground(QuestionBackground.getSelectedQuetionBackground(getActivity()));

                deLay();
                //  showViews();
            } else if (id == R.id.option_2) {
                option2.setBackground(QuestionBackground.getSelectedQuetionBackground(getActivity()));


                deLay();
                // showViews();
            } else if (id == R.id.option_3) {
                option3.setBackground(QuestionBackground.getSelectedQuetionBackground(getActivity()));


                deLay();
                // showViews();
            } else if (id == R.id.option_4) {
                option4.setBackground(QuestionBackground.getSelectedQuetionBackground(getActivity()));


                deLay();
                //  showViews();
            } else if (id == R.id.next_button) {
               nextButton();
            }

        }


    }

}
