package ng.com.techdepo.esidemtest.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import ng.com.techdepo.esidemtest.R;
import ng.com.techdepo.esidemtest.adapters.ResultAdapter;
import ng.com.techdepo.esidemtest.database.Result;
import ng.com.techdepo.esidemtest.databinding.ActivityResultBinding;
import ng.com.techdepo.esidemtest.utils.ToastMaker;
import ng.com.techdepo.esidemtest.view_model.QuestionsViewModel;


public class ResultActivity extends AppCompatActivity {

    ResultAdapter adapter;
    RecyclerView recyclerView;
    ActivityResultBinding activityResultBinding;
    public  ArrayList<Result> resultList = new ArrayList<>();
    LinearLayoutManager layoutManager;
    QuestionsViewModel questionsViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activityResultBinding = DataBindingUtil.setContentView(this,R.layout.activity_result);
        setUpRecyclerView();
        fetchResults();


    }

    private void setUpRecyclerView(){
        layoutManager = new LinearLayoutManager(this);
        recyclerView = activityResultBinding.resultRecyclerView;
        recyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getApplicationContext(),layoutManager.getOrientation());
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.addItemDecoration(dividerItemDecoration);
        adapter = new ResultAdapter(resultList);
        recyclerView.setAdapter(adapter);
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT |ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                questionsViewModel.deleteResult(Integer.parseInt(viewHolder.itemView.getTag().toString()));
                resultList.clear();

            }
        }).attachToRecyclerView(recyclerView);


    }

    private void fetchResults(){
        questionsViewModel = ViewModelProviders.of(this).get(QuestionsViewModel.class);
        questionsViewModel.getAllResult().observe(this, new Observer<List<Result>>() {
            @Override
            public void onChanged(@Nullable List<Result> results) {
                resultList.addAll(results);
                adapter.notifyDataSetChanged();
                emptyListCheck();

            }
        });
    }

    private void emptyListCheck(){

        if (resultList.size()==0){
            activityResultBinding.noResult.setVisibility(View.VISIBLE);
        }else {
            activityResultBinding.noResult.setVisibility(View.GONE);
        }
    }
}
