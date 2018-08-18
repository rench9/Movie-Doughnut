package com.yahoo.r4hu7.moviesdoughnut.ui.dependency;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.AppCompatSpinner;
import android.util.AttributeSet;

public class SortOrderSpinner extends AppCompatSpinner {
    private boolean hidden_f = false;

    public SortOrderSpinner(Context context) {
        super(context);
    }

    public SortOrderSpinner(Context context, int mode) {
        super(context, mode);
    }

    public SortOrderSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SortOrderSpinner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public SortOrderSpinner(Context context, AttributeSet attrs, int defStyleAttr, int mode) {
        super(context, attrs, defStyleAttr, mode);
    }

    public SortOrderSpinner(Context context, AttributeSet attrs, int defStyleAttr, int mode, Resources.Theme popupTheme) {
        super(context, attrs, defStyleAttr, mode, popupTheme);
    }

    public boolean isForceHidden() {
        return hidden_f;
    }

    public void forceHide() {
        hidden_f = true;
        this.setVisibility(GONE);
    }

    public void forceVisible() {
        hidden_f = false;
        this.setVisibility(VISIBLE);
    }
}
