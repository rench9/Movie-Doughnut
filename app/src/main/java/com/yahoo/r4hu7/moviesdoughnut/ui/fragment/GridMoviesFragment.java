package com.yahoo.r4hu7.moviesdoughnut.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yahoo.r4hu7.moviesdoughnut.R;

public class GridMoviesFragment extends Fragment {

    public static GridMoviesFragment newInstance() {
        return new GridMoviesFragment();
    }

    public GridMoviesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_grid_movies, container, false);
    }

}
