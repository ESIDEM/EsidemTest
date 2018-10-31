package ng.com.techdepo.esidemtest.fragments;


import android.app.Fragment;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import ng.com.techdepo.esidemtest.R;
import ng.com.techdepo.esidemtest.utils.QuestionBackground;


/**
 * A simple {@link Fragment} subclass.
 */
public class QuestionFragment extends Fragment implements View.OnClickListener {

    TextView option1;
    TextView option2;
    TextView option3;
    TextView option4;


    public QuestionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.question_layout, container, false);

        option1 = (TextView) rootView.findViewById(R.id.option_1);
        option2 = (TextView) rootView.findViewById(R.id.option_2);
        option3 = (TextView) rootView.findViewById(R.id.option_3);
        option4 = (TextView) rootView.findViewById(R.id.option_4);

        option1.setOnClickListener(this);
        option2.setOnClickListener(this);
        option3.setOnClickListener(this);
        option4.setOnClickListener(this);

        return  rootView;
    }

    @Override
    public void onClick(View v) {


        int id = v.getId();
        if(id ==R.id.option_1){
            option1.setBackground(QuestionBackground.getSelectedQuetionBackground(getActivity()));
        }else if(id ==R.id.option_2){
            Toast.makeText(v.getContext(),"Option 2",Toast.LENGTH_LONG).show();
        }else if (id ==R.id.option_3){
            Toast.makeText(v.getContext(),"Option 3",Toast.LENGTH_LONG).show();
        }else if(id ==R.id.option_4) {
            Toast.makeText(v.getContext(),"Option 4",Toast.LENGTH_LONG).show();
        }

    }
}
