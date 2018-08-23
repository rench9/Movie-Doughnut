package com.yahoo.r4hu7.moviesdoughnut.ui.dependency.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ObservableList;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.yahoo.r4hu7.moviesdoughnut.R;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.response.model.Video;
import com.yahoo.r4hu7.moviesdoughnut.databinding.AdapterVideoBinding;
import com.yahoo.r4hu7.moviesdoughnut.ui.viewmodel.VideoViewModel;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {

    private ObservableList<Video> videos;

    public VideoAdapter(ObservableList<Video> videos) {
        this.videos = videos;
        videos.addOnListChangedCallback(new ObservableList.OnListChangedCallback<ObservableList<Video>>() {
            @Override
            public void onChanged(ObservableList<Video> sender) {
                notifyDataSetChanged();
            }

            @Override
            public void onItemRangeChanged(ObservableList<Video> sender, int positionStart, int itemCount) {
                notifyItemRangeChanged(positionStart, itemCount);
            }

            @Override
            public void onItemRangeInserted(ObservableList<Video> sender, int positionStart, int itemCount) {
                notifyItemRangeInserted(positionStart, itemCount);
            }

            @Override
            public void onItemRangeMoved(ObservableList<Video> sender, int fromPosition, int toPosition, int itemCount) {
                notifyItemRangeChanged(fromPosition, itemCount);
            }

            @Override
            public void onItemRangeRemoved(ObservableList<Video> sender, int positionStart, int itemCount) {
                notifyItemRangeRemoved(positionStart, itemCount);
            }
        });
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        AdapterVideoBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.adapter_video, parent, false);
        return new ViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.viewModel.setVideo(videos.get(position));
    }

    @Override
    public int getItemCount() {
        return videos.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        VideoViewModel viewModel;

        ViewHolder(AdapterVideoBinding binding) {
            super(binding.getRoot());
            viewModel = new VideoViewModel();
            binding.setVm(viewModel);
        }

    }
}
