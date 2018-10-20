package com.yahoo.r4hu7.moviesdoughnut.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public abstract class ConnectivityChangeListener extends BroadcastReceiver {

    public static final String NETWORK_STATE_KEY = "NETWORK_STATE_KEY";

    public abstract void connected();

    public abstract void disConnected();

    @Override
    public void onReceive(Context context, Intent intent) {

        if (context == null)
            return;
        Intent in = new Intent();
        if (isConnected(context)) {
            in.putExtra(NETWORK_STATE_KEY, true);
            connected();
        } else {
            in.putExtra(NETWORK_STATE_KEY, false);
            disConnected();
        }
        context.sendBroadcast(in);
    }

    private boolean isConnected(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null &&
                activeNetwork.isConnected();
    }
}
