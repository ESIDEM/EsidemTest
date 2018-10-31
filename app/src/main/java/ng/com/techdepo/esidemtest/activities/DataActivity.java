package ng.com.techdepo.esidemtest.activities;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import ng.com.techdepo.esidemtest.R;

public class DataActivity extends AppCompatActivity {

    SharedPreferences prefs = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);
        prefs = getSharedPreferences("ng.com.techdepo.esidemtest", MODE_PRIVATE);
    }

//    public void startMainActivity(View view) {
//
//
//    }

    public void showDialog(View view){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.name_dialogue);

        final EditText editText = (EditText) dialog.findViewById(R.id.name_edit_text);
        Button dialogButton = (Button) dialog.findViewById(R.id.continue_button);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prefs.edit().putString("user_name", editText.getText().toString()).apply();
                dialog.dismiss();

                startActivity(new Intent(v.getContext(),MainActivity.class));
                finish();
            }
        });

        dialog.show();

    }
}
