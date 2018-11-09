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
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import android.widget.TextView;
import android.widget.Toast;


import java.util.List;

import ng.com.techdepo.esidemtest.R;
import ng.com.techdepo.esidemtest.database.QuestionEntity;
import ng.com.techdepo.esidemtest.databinding.ActivityMainBinding;
import ng.com.techdepo.esidemtest.fragments.QuestionFragment;
import ng.com.techdepo.esidemtest.utils.QuestionConverter;
import ng.com.techdepo.esidemtest.view_model.QuestionsViewModel;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    SharedPreferences prefs = null;
    View timeTrialLayout;
    ActivityMainBinding activityMainBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        Toolbar toolbar = activityMainBinding.appBar.toolbar;
        setSupportActionBar(toolbar);
        prefs = getSharedPreferences("ng.com.techdepo.esidemtest", MODE_PRIVATE);
        // timeTrialLayout = (LinearLayout) findViewById(R.id.time_trial_layout);

        activityMainBinding.appBar.contentMain.nameTextView.setText("Welcome " + prefs.getString("user_name", "Sam Esidem"));

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, activityMainBinding.drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        activityMainBinding.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        activityMainBinding.navView.setNavigationItemSelectedListener(this);
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
        QuestionsViewModel questionsViewModel = ViewModelProviders.of(this).get(QuestionsViewModel.class);
        questionsViewModel.getQuestions().observe(this, new Observer<List<QuestionEntity>>() {
            @Override
            public void onChanged(@Nullable List<QuestionEntity> questionEntities) {

            }
        });
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

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

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
