package ng.com.techdepo.esidemtest.activities;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;


import ng.com.techdepo.esidemtest.R;
import ng.com.techdepo.esidemtest.fragments.QuestionFragment;

public class QuestionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment, new QuestionFragment())
                .commit();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}
