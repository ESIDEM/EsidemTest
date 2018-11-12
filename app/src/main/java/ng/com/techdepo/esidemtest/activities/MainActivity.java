package ng.com.techdepo.esidemtest.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import java.util.ArrayList;
import java.util.List;

import ng.com.techdepo.esidemtest.R;
import ng.com.techdepo.esidemtest.database.AppDatabase;
import ng.com.techdepo.esidemtest.database.QuestionEntity;
import ng.com.techdepo.esidemtest.databinding.ActivityMainBinding;

import ng.com.techdepo.esidemtest.databinding.NavHeaderMainBinding;
import ng.com.techdepo.esidemtest.fragments.QuestionFragment;
import ng.com.techdepo.esidemtest.models.Question;
import ng.com.techdepo.esidemtest.utils.DeleteFromDb;
import ng.com.techdepo.esidemtest.utils.QuestionConverter;
import ng.com.techdepo.esidemtest.utils.SharedPreferenceUtil;
import ng.com.techdepo.esidemtest.view_model.QuestionsViewModel;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    SharedPreferences prefs = null;
     ActivityMainBinding activityMainBinding;
    private ArrayList<Question> questionList = new ArrayList<>();
    AppDatabase appDatabase;
    QuestionsViewModel questionsViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        Toolbar toolbar = activityMainBinding.appBar.toolbar;
        setSupportActionBar(toolbar);
        prefs = getSharedPreferences("ng.com.techdepo.esidemtest", MODE_PRIVATE);
        activityMainBinding.appBar.contentMain.nameTextView.setText("Welcome " + prefs.getString("user_name", "Sam Esidem"));

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, activityMainBinding.drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        activityMainBinding.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        appDatabase = AppDatabase.getInstance(this);
        questionsViewModel = ViewModelProviders.of(this).get(QuestionsViewModel.class);
        activityMainBinding.navView.setNavigationItemSelectedListener(this);
        View header = activityMainBinding.navView.getHeaderView(0);
        NavHeaderMainBinding navHeaderMainBinding = NavHeaderMainBinding.bind(header);
        navHeaderMainBinding.nameText.setText(prefs.getString("user_name", "Sam Esidem"));
        fetchQuestions();




    }

    @Override
    public void onBackPressed() {
            if (activityMainBinding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            activityMainBinding.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    private void fetchQuestions(){
        questionsViewModel.getQuestions().observe(this, new Observer<List<QuestionEntity>>() {
            @Override
            public void onChanged(@Nullable List<QuestionEntity> questionEntities) {
                questionList.clear();
                for (QuestionEntity questionEntity: questionEntities){

                    questionList.add(QuestionConverter.converEntityToQuestion(questionEntity));
                }

                activityMainBinding.appBar.contentMain.numberOfQuestion.setText(String.valueOf(questionList.size())+" "+
                        SharedPreferenceUtil.subject(getApplicationContext()).substring(0,1).toUpperCase()+ SharedPreferenceUtil.subject(getApplicationContext()).substring(1)+" "+getString(R.string.questions_available));

            }
        });
    }

    private void refreshQuestions(){

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_english) {
            SharedPreferenceUtil.setSubject(this,"english");
            DeleteFromDb.deleteAllMovies(appDatabase);
            fetchQuestions();
        } else if (id == R.id.nav_mathematics) {
            SharedPreferenceUtil.setSubject(this,"mathematics");
            DeleteFromDb.deleteAllMovies(appDatabase);
            fetchQuestions();
        } else if (id == R.id.nav_chemistry) {
            SharedPreferenceUtil.setSubject(this,"chemistry");
            DeleteFromDb.deleteAllMovies(appDatabase);
            fetchQuestions();
        } else if (id == R.id.nav_physics) {
            SharedPreferenceUtil.setSubject(this,"physics");
            DeleteFromDb.deleteAllMovies(appDatabase);
            fetchQuestions();
        } else if (id == R.id.nav_biology) {
            SharedPreferenceUtil.setSubject(this,"biology");
            DeleteFromDb.deleteAllMovies(appDatabase);
            fetchQuestions();
        } else if (id == R.id.nav_economics) {
            SharedPreferenceUtil.setSubject(this,"economics");
            DeleteFromDb.deleteAllMovies(appDatabase);
            fetchQuestions();
        }else if (id == R.id.nav_accounting) {
            SharedPreferenceUtil.setSubject(this,"accounting");
            DeleteFromDb.deleteAllMovies(appDatabase);
            fetchQuestions();
        }else if (id == R.id.nav_government) {
            SharedPreferenceUtil.setSubject(this,"government");
            DeleteFromDb.deleteAllMovies(appDatabase);
            fetchQuestions();
        }else if (id == R.id.nav_current_affair) {
            SharedPreferenceUtil.setSubject(this,"currentaffairs");
            DeleteFromDb.deleteAllMovies(appDatabase);
            fetchQuestions();
        }else if (id == R.id.nav_commerce) {
            SharedPreferenceUtil.setSubject(this, "commerce");
            DeleteFromDb.deleteAllMovies(appDatabase);
            fetchQuestions();
        }else if (id == R.id.nav_c_r_k) {
            SharedPreferenceUtil.setSubject(this, "crk");
            DeleteFromDb.deleteAllMovies(appDatabase);
            fetchQuestions();
        }else if (id == R.id.nav_history) {
            SharedPreferenceUtil.setSubject(this, "history");
            DeleteFromDb.deleteAllMovies(appDatabase);
            fetchQuestions();
        }else if (id == R.id.nav_geography) {
            SharedPreferenceUtil.setSubject(this, "geography");
            DeleteFromDb.deleteAllMovies(appDatabase);
            fetchQuestions();
        }else if (id == R.id.nav_Literature_in_eng) {
            SharedPreferenceUtil.setSubject(this, "englishlit");
            DeleteFromDb.deleteAllMovies(appDatabase);
            fetchQuestions();
        }else if (id == R.id.nav_insurance) {
            SharedPreferenceUtil.setSubject(this, "insurance");
            DeleteFromDb.deleteAllMovies(appDatabase);
            fetchQuestions();
        }else if (id == R.id.nav_civil_edu) {
            SharedPreferenceUtil.setSubject(this, "civiledu");
            DeleteFromDb.deleteAllMovies(appDatabase);
            fetchQuestions();
        }

        activityMainBinding.drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public void timeTrial(View view) {
        Intent intent = new Intent(this, QuestionActivity.class);
        intent.putExtra(QuestionFragment.TEST_TYPE, "time");

        startActivity(intent);
    }

    public void classicTest(View view) {
        Intent intent = new Intent(this, QuestionActivity.class);
        intent.putExtra(QuestionFragment.TEST_TYPE, "classic");

        startActivity(intent);
    }
}
