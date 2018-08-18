package com.yahoo.r4hu7.moviesdoughnut.ui.dependency.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.yahoo.r4hu7.moviesdoughnut.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SortSpinnerAdapter extends ArrayAdapter<Filter> {
    private Filter[] objects;

    public SortSpinnerAdapter(Context context, int textViewResourceId, Filter[] objects) {
        super(context, textViewResourceId, objects);
        this.objects = objects;
    }

    @Override
    public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        ViewHolder holder;
        View rowview = convertView;
        if (rowview == null) {

            rowview = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_spinner, parent, false);

            holder = new ViewHolder(rowview);
            rowview.setTag(holder);
        } else {
            holder = (ViewHolder) rowview.getTag();
        }
        holder.tvItem.setText(objects[position].getName());
        holder.tvItem.setTextColor(ContextCompat.getColor(getContext(), R.color.grd_1_s));
        holder.cvContainer.setCardElevation(0f);

        Animation hyperspaceJump = AnimationUtils.loadAnimation(getContext(), R.anim.slide_top);
        hyperspaceJump.setInterpolator(new DecelerateInterpolator());
        hyperspaceJump.setStartOffset(150);
        holder.tvItem.setAnimation(hyperspaceJump);
        return rowview;
    }

    private View getCustomView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        View rowview = convertView;
        if (rowview == null) {

            rowview = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_spinner, parent, false);

            holder = new ViewHolder(rowview);
            rowview.setTag(holder);
        } else {
            holder = (ViewHolder) rowview.getTag();
        }
        holder.tvItem.setText(objects[position].getName());

        Animation hyperspaceJump = AnimationUtils.loadAnimation(getContext(), R.anim.zoom_in);
        hyperspaceJump.setInterpolator(new DecelerateInterpolator());
        hyperspaceJump.setStartOffset(100 * position);
        rowview.setAnimation(hyperspaceJump);

        return rowview;
    }

    class ViewHolder {
        @BindView(R.id.tvItem)
        TextView tvItem;
        @BindView(R.id.cvContainer)
        CardView cvContainer;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

}
