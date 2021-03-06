package ng.com.techdepo.esidemtest.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import javax.inject.Inject;

import ng.com.techdepo.esidemtest.R;
import ng.com.techdepo.esidemtest.utils.QuestionsApplication;

public class SplashScreen extends AppCompatActivity {

    // Splash Screen Timer
    private static int SPLASH_TIME_OUT = 3000;
    @Inject SharedPreferences prefs ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        ((QuestionsApplication) getApplication()).getAppComponent().inject(this);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed (new Runnable() {
            @Override
            public void run() {


                checkFirstRun();

                finish ();
            }
        }, SPLASH_TIME_OUT);


    }

    private void checkFirstRun(){

        if (prefs.getBoolean("firstrun", true)) {
            // Do first run stuff here then set 'firstrun' as false
            startActivity(new Intent(getApplicationContext(),DataActivity.class));
            // using the following line to edit/commit prefs

        }else {

            Intent i = new Intent (SplashScreen.this, MainActivity.class);
            startActivity(i);
        }

        prefs.edit().putBoolean("firstrun", false).apply();

    }
}
