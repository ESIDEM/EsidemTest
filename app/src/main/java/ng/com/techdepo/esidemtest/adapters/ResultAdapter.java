package ng.com.techdepo.esidemtest.adapters;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import ng.com.techdepo.esidemtest.R;
import ng.com.techdepo.esidemtest.database.Result;
import ng.com.techdepo.esidemtest.databinding.ResultItemBinding;



public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.ResultViewHolder>{

    private List<Result> resultList;
    private LayoutInflater layoutInflater;

    public ResultAdapter(List<Result> resultList) {
        this.resultList = resultList;
    }

    @NonNull
    @Override
    public ResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater==null){
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        ResultItemBinding resultItemBinding = DataBindingUtil.inflate(layoutInflater, R.layout.result_item,parent,false);
        return new ResultViewHolder(resultItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ResultViewHolder holder, int position) {
            holder.resultItemBinding.setResult(resultList.get(position));
            holder.itemView.setTag(resultList.get(position).getId());
    }

    @Override
    public int getItemCount() {
        return resultList.size();
    }

    public class ResultViewHolder extends RecyclerView.ViewHolder{
        ResultItemBinding resultItemBinding;
        public ResultViewHolder( ResultItemBinding itemView) {
            super(itemView.getRoot());
            resultItemBinding = itemView;
        }
    }
}
