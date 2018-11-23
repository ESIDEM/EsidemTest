package ng.com.techdepo.esidemtest.activities;

import android.app.ActivityOptions;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.appinvite.AppInviteInvitation;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.List;

import ng.com.techdepo.esidemtest.R;
import ng.com.techdepo.esidemtest.database.AppDatabase;
import ng.com.techdepo.esidemtest.database.QuestionEntity;
import ng.com.techdepo.esidemtest.databinding.ActivityMainBinding;

import ng.com.techdepo.esidemtest.databinding.NavHeaderMainBinding;
import ng.com.techdepo.esidemtest.models.Question;
import ng.com.techdepo.esidemtest.utils.DeleteFromDb;
import ng.com.techdepo.esidemtest.utils.NetworkUtil;
import ng.com.techdepo.esidemtest.utils.QuestionConverter;
import ng.com.techdepo.esidemtest.utils.SharedPreferenceUtil;
import ng.com.techdepo.esidemtest.utils.ToastMaker;
import ng.com.techdepo.esidemtest.view_model.QuestionsViewModel;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,GoogleApiClient.OnConnectionFailedListener {
    SharedPreferences prefs = null;
    ActivityMainBinding activityMainBinding;
    private ArrayList<Question> questionList = new ArrayList<>();
    AppDatabase appDatabase;
    QuestionsViewModel questionsViewModel;
    private GoogleApiClient mGoogleApiClient;
    private static final int REQUEST_INVITE = 1;
    private static final String TAG = "MainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        Toolbar toolbar = activityMainBinding.appBar.toolbar;
        setSupportActionBar(toolbar);
        prefs = getSharedPreferences("ng.com.techdepo.esidemtest", MODE_PRIVATE);
        activityMainBinding.appBar.contentMain.nameTextView.setText("Welcome"+" " + prefs.getString("user_name", "Sam Esidem"));
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API)
                .build();
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

    private void fetchQuestions() {
        questionsViewModel.getQuestions().observe(this, new Observer<List<QuestionEntity>>() {
            @Override
            public void onChanged(@Nullable List<QuestionEntity> questionEntities) {
                questionList.clear();
                for (QuestionEntity questionEntity : questionEntities) {

                    questionList.add(QuestionConverter.converEntityToQuestion(questionEntity));
                }

                activityMainBinding.appBar.contentMain.numberOfQuestion.setText(String.valueOf(questionList.size()) + " " +
                        SharedPreferenceUtil.subject(getApplicationContext()).substring(0, 1).toUpperCase() + SharedPreferenceUtil.subject(getApplicationContext()).substring(1) + " " + getString(R.string.questions_available));

            }
        });
    }

    private void refreshQuestions() {
        if (NetworkUtil.isNetworkAvailable(this)){
        questionsViewModel.getNewQuestions();
    }else {
            ToastMaker.makeShortToast(this,getString(R.string.no_internet));
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_english) {
            SharedPreferenceUtil.setSubject(this, "english");
            DeleteFromDb.deleteAllMovies(appDatabase);
            refreshQuestions();
            ToastMaker.makeShortToast(this, getString(R.string.english_lang) + getString(R.string.is_now_your_preferred_subject));
        } else if (id == R.id.nav_mathematics) {
            SharedPreferenceUtil.setSubject(this, "mathematics");
            DeleteFromDb.deleteAllMovies(appDatabase);
            refreshQuestions();
            ToastMaker.makeShortToast(this, getString(R.string.math) + getString(R.string.is_now_your_preferred_subject));
        } else if (id == R.id.nav_chemistry) {
            SharedPreferenceUtil.setSubject(this, "chemistry");
            DeleteFromDb.deleteAllMovies(appDatabase);
            refreshQuestions();
            ToastMaker.makeShortToast(this, getString(R.string.chemistry) + getString(R.string.is_now_your_preferred_subject));
        } else if (id == R.id.nav_physics) {
            SharedPreferenceUtil.setSubject(this, "physics");
            DeleteFromDb.deleteAllMovies(appDatabase);
            refreshQuestions();
            ToastMaker.makeShortToast(this, getString(R.string.physics) + getString(R.string.is_now_your_preferred_subject));
        } else if (id == R.id.nav_biology) {
            SharedPreferenceUtil.setSubject(this, "biology");
            DeleteFromDb.deleteAllMovies(appDatabase);
            refreshQuestions();
            ToastMaker.makeShortToast(this, getString(R.string.biology) + getString(R.string.is_now_your_preferred_subject));
        } else if (id == R.id.nav_economics) {
            SharedPreferenceUtil.setSubject(this, "economics");
            DeleteFromDb.deleteAllMovies(appDatabase);
            refreshQuestions();
            ToastMaker.makeShortToast(this, getString(R.string.economics) + getString(R.string.is_now_your_preferred_subject));
        } else if (id == R.id.nav_accounting) {
            SharedPreferenceUtil.setSubject(this, "accounting");
            DeleteFromDb.deleteAllMovies(appDatabase);
            refreshQuestions();
            ToastMaker.makeShortToast(this, getString(R.string.accounting) + getString(R.string.is_now_your_preferred_subject));
        } else if (id == R.id.nav_government) {
            SharedPreferenceUtil.setSubject(this, "government");
            DeleteFromDb.deleteAllMovies(appDatabase);
            refreshQuestions();
            ToastMaker.makeShortToast(this, getString(R.string.government) + getString(R.string.is_now_your_preferred_subject));
        } else if (id == R.id.nav_current_affair) {
            SharedPreferenceUtil.setSubject(this, "currentaffairs");
            DeleteFromDb.deleteAllMovies(appDatabase);
            refreshQuestions();
            ToastMaker.makeShortToast(this, getString(R.string.current_affairs) + getString(R.string.is_now_your_preferred_subject));
        } else if (id == R.id.nav_commerce) {
            SharedPreferenceUtil.setSubject(this, "commerce");
            DeleteFromDb.deleteAllMovies(appDatabase);
            refreshQuestions();
            ToastMaker.makeShortToast(this, getString(R.string.commerce) + getString(R.string.is_now_your_preferred_subject));
        } else if (id == R.id.nav_c_r_k) {
            SharedPreferenceUtil.setSubject(this, "crk");
            DeleteFromDb.deleteAllMovies(appDatabase);
            refreshQuestions();
            ToastMaker.makeShortToast(this, getString(R.string.c_r_k) + getString(R.string.is_now_your_preferred_subject));
        } else if (id == R.id.nav_history) {
            SharedPreferenceUtil.setSubject(this, "history");
            DeleteFromDb.deleteAllMovies(appDatabase);
            refreshQuestions();
            ToastMaker.makeShortToast(this, getString(R.string.history) + getString(R.string.is_now_your_preferred_subject));
        } else if (id == R.id.nav_geography) {
            SharedPreferenceUtil.setSubject(this, "geography");
            DeleteFromDb.deleteAllMovies(appDatabase);
            refreshQuestions();
            ToastMaker.makeShortToast(this, getString(R.string.geography) + getString(R.string.is_now_your_preferred_subject));
        } else if (id == R.id.nav_Literature_in_eng) {
            SharedPreferenceUtil.setSubject(this, "englishlit");
            DeleteFromDb.deleteAllMovies(appDatabase);
            refreshQuestions();
            ToastMaker.makeShortToast(this, getString(R.string.lit_in_english) + getString(R.string.is_now_your_preferred_subject));
        } else if (id == R.id.nav_insurance) {
            SharedPreferenceUtil.setSubject(this, "insurance");
            DeleteFromDb.deleteAllMovies(appDatabase);
            refreshQuestions();
            ToastMaker.makeShortToast(this, getString(R.string.insurance) + getString(R.string.is_now_your_preferred_subject));
        } else if (id == R.id.nav_civil_edu) {
            SharedPreferenceUtil.setSubject(this, "civiledu");
            DeleteFromDb.deleteAllMovies(appDatabase);
            refreshQuestions();
            ToastMaker.makeShortToast(this, getString(R.string.civil_edu) + getString(R.string.is_now_your_preferred_subject));
        }else if (id == R.id.nav_share){

            sendInvitation();
        }else if (id == R.id.nav_send){

            rateApp();
        }

        activityMainBinding.drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public void timeTrial(View view) {
        if (questionList.size()==0){
            ToastMaker.makeLongToast(this,getString(R.string.sorry_no_questions));
            questionsViewModel.getQuestions();
        }else {
            Intent intent = new Intent(this, QuestionActivity.class);
            intent.putExtra(QuestionActivity.TEST_TYPE, "time");

            startActivity(intent);
        }
    }

    public void classicTest(View view) {
        if (questionList.size()==0){
            ToastMaker.makeLongToast(this,getString(R.string.sorry_no_questions));
            questionsViewModel.getQuestions();
        }else {
            Intent intent = new Intent(this, QuestionActivity.class);
            intent.putExtra(QuestionActivity.TEST_TYPE, "classic");

            startActivity(intent);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == REQUEST_INVITE) {
            if (resultCode == RESULT_OK) {
                // Check how many invitations were sent and log.
                String[] ids = AppInviteInvitation.getInvitationIds(resultCode, data);
                Log.d(TAG, "Invitations sent: " + ids.length);
            } else {
                // Sending failed or it was canceled, show failure message to the user
                Log.d(TAG, "Failed to send invitation.");
            }
        }
    }

    public void testResult(View view){
        Intent intent = new Intent(this, ResultActivity.class);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            Bundle bundle = ActivityOptions.makeSceneTransitionAnimation(this).toBundle();
            startActivity(intent, bundle);
        } else {
            startActivity(intent);
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private void sendInvitation() {
        Intent intent = new AppInviteInvitation.IntentBuilder(getString(R.string.invitation_title))
                .setMessage(getString(R.string.invitation_message))
                .setCallToActionText(getString(R.string.invitation_cta))
                .build();
        startActivityForResult(intent, REQUEST_INVITE);
    }

    public void rateApp(){

        Uri uri = Uri.parse("market://details?id=" + this.getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        // To count with Play market backstack, After pressing back button,
        // to taken back to our application, we need to add following flags to intent.
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + this.getPackageName())));
        }
    }
}
