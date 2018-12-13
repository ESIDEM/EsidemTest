package ng.com.techdepo.esidemtest.activities;


import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import java.util.List;

import javax.inject.Inject;

import ng.com.techdepo.esidemtest.R;
import ng.com.techdepo.esidemtest.database.QuestionEntity;
import ng.com.techdepo.esidemtest.fragments.ClassicTestFragment;
import ng.com.techdepo.esidemtest.fragments.QuestionFragment;
import ng.com.techdepo.esidemtest.utils.QuestionsApplication;
import ng.com.techdepo.esidemtest.view_model.QuestionsViewModel;

public class QuestionActivity extends AppCompatActivity {

    public static  String TEST_TYPE;
    @Inject QuestionsViewModel questionsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getTheme().applyStyle(R.style.AppThemeClassic,true);
        setContentView(R.layout.activity_question);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ((QuestionsApplication) getApplication()).getAppComponent().inject(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        String test_type = intent.getStringExtra(TEST_TYPE);

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

        }


}
