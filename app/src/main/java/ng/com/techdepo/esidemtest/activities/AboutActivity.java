package ng.com.techdepo.esidemtest.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import ng.com.techdepo.esidemtest.R;
import ng.com.techdepo.esidemtest.databinding.AboutDialogueBinding;

public class AboutActivity extends AppCompatActivity {

    AboutDialogueBinding aboutDialogueBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        aboutDialogueBinding = DataBindingUtil.setContentView(this,R.layout.about_dialogue);
        ClickHandler clickHandler= new ClickHandler();
        aboutDialogueBinding.setHandler(clickHandler);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(getString(R.string.about_page));
    }


    public class ClickHandler {
        public void onClick(View view) {

            int id = view.getId();

            if (id == R.id.fb) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://web.facebook.com/esidem"));
                startActivity(browserIntent);

            } else if (id == R.id.twitter) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/Esidemsam"));
                startActivity(browserIntent);

            } else if (id == R.id.linkin) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.linkedin.com/in/esidem/"));
                startActivity(browserIntent);
            } else if (id == R.id.git) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/esidem"));
                startActivity(browserIntent);

            }


        }
    }
}
