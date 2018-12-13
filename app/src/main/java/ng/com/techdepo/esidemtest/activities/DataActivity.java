package ng.com.techdepo.esidemtest.activities;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import androidx.databinding.DataBindingUtil;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import javax.inject.Inject;

import ng.com.techdepo.esidemtest.databinding.NameDialogueBinding;
import ng.com.techdepo.esidemtest.R;
import ng.com.techdepo.esidemtest.utils.QuestionsApplication;


public class DataActivity extends AppCompatActivity {

    @Inject SharedPreferences prefs ;
    NameDialogueBinding nameDialogueBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);
        ((QuestionsApplication) getApplication()).getAppComponent().inject(this);

    }


    public void showDialog(View view) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        nameDialogueBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.name_dialogue, null, false);
        dialog.setContentView(nameDialogueBinding.getRoot());

        final EditText editText = nameDialogueBinding.nameEditText;
        Button dialogButton = nameDialogueBinding.continueButton;
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!dataValidated(editText.getText().toString())) {
                    return;
                }
                prefs.edit().putString("user_name", editText.getText().toString()).apply();
                startActivity(new Intent(v.getContext(), MainActivity.class));
                dialog.dismiss();

                finish();
            }
        });

        dialog.show();

    }

    boolean dataValidated(String name) {

        if (name.isEmpty() ) {
            nameDialogueBinding.nameEditText.setError("Please enter your name");
            return false;
        }

        return true;
    }
}
