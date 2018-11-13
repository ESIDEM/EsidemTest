package ng.com.techdepo.esidemtest.activities;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;


import java.util.List;

import ng.com.techdepo.esidemtest.R;
import ng.com.techdepo.esidemtest.database.QuestionEntity;
import ng.com.techdepo.esidemtest.fragments.ClassicTestFragment;
import ng.com.techdepo.esidemtest.fragments.QuestionFragment;
import ng.com.techdepo.esidemtest.utils.ToastMaker;
import ng.com.techdepo.esidemtest.view_model.QuestionsViewModel;

public class QuestionActivity extends AppCompatActivity {

    public static  String TEST_TYPE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        String test_type = intent.getStringExtra(TEST_TYPE);
        QuestionsViewModel questionsViewModel = ViewModelProviders.of(this).get(QuestionsViewModel.class);
        questionsViewModel.getQuestions().observe(this, new Observer<List<QuestionEntity>>() {
            @Override
            public void onChanged(@Nullable List<QuestionEntity> questionEntityList) {

            }
        });
        Fragment fragment = null;
        Class fragmentClass = null;
        if (test_type.equals("time")){
            fragmentClass = QuestionFragment.class;
        }else if (test_type.equals("classic")){

            fragmentClass = ClassicTestFragment.class;
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();



    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();
    }
}
