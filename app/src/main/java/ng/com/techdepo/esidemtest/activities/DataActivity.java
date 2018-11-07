package ng.com.techdepo.esidemtest.activities;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import ng.com.techdepo.esidemtest.databinding.NameDialogueBinding;
import ng.com.techdepo.esidemtest.R;


public class DataActivity extends AppCompatActivity {

    SharedPreferences prefs = null;
    NameDialogueBinding nameDialogueBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);
        prefs = getSharedPreferences("ng.com.techdepo.esidemtest", MODE_PRIVATE);
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
                prefs.edit().putString("user_name", editText.getText().toString()).apply();

                startActivity(new Intent(v.getContext(), MainActivity.class));
                dialog.dismiss();

                finish();
            }
        });

        dialog.show();

    }
}
