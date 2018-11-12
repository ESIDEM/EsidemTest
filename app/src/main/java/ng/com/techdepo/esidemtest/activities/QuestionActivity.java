package ng.com.techdepo.esidemtest.activities;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;


import ng.com.techdepo.esidemtest.R;
import ng.com.techdepo.esidemtest.fragments.QuestionActivityFragment;
import ng.com.techdepo.esidemtest.fragments.QuestionFragment;

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
        Fragment fragment = null;
        Class fragmentClass = null;
        if (test_type.equals("time")){
            fragmentClass = QuestionFragment.class;
        }else if (test_type.equals("classic")){

            fragmentClass = QuestionActivityFragment.class;
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();


//        getSupportFragmentManager()
//                .beginTransaction()
//                .replace(R.id.fragment, new QuestionFragment())
//                .commit();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}
