package com.yahoo.r4hu7.moviesdoughnut.ui.dependency.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ObservableList;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.yahoo.r4hu7.moviesdoughnut.R;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.response.model.Cast;
import com.yahoo.r4hu7.moviesdoughnut.databinding.AdapterCastBinding;
import com.yahoo.r4hu7.moviesdoughnut.ui.viewmodel.CastViewModel;

public class CastAdapter extends RecyclerView.Adapter<CastAdapter.ViewHolder> {

    private ObservableList<Cast> casts;

    public CastAdapter(ObservableList<Cast> casts) {
        this.casts = casts;
        casts.addOnListChangedCallback(new ObservableList.OnListChangedCallback<ObservableList<Cast>>() {
            @Override
            public void onChanged(ObservableList<Cast> sender) {
                notifyDataSetChanged();
            }

            @Override
            public void onItemRangeChanged(ObservableList<Cast> sender, int positionStart, int itemCount) {
                notifyItemRangeChanged(positionStart, itemCount);

            }

            @Override
            public void onItemRangeInserted(ObservableList<Cast> sender, int positionStart, int itemCount) {
                notifyItemRangeInserted(positionStart, itemCount);

            }

            @Override
            public void onItemRangeMoved(ObservableList<Cast> sender, int fromPosition, int toPosition, int itemCount) {
                notifyItemRangeChanged(fromPosition, itemCount);

            }

            @Override
            public void onItemRangeRemoved(ObservableList<Cast> sender, int positionStart, int itemCount) {
                notifyItemRangeRemoved(positionStart, itemCount);

            }
        });
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        AdapterCastBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.adapter_cast, parent, false);
        return new ViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.viewModel.setCast(casts.get(position));
    }

    @Override
    public int getItemCount() {
        return casts.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        CastViewModel viewModel;

        ViewHolder(AdapterCastBinding binding) {
            super(binding.getRoot());
            viewModel = new CastViewModel();
            binding.setVm(viewModel);
        }

    }
}
