package com.example.popularmovies.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.popularmovies.ui.HomeActivity;

import java.util.Objects;

public class NetworkState {

    public static boolean isConnectedToNetwork(@NonNull final Context context) {
        try {
            final ConnectivityManager connectivityManager =
                    (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            final NetworkInfo activeNetwork = Objects.requireNonNull(connectivityManager).getActiveNetworkInfo();

            return activeNetwork != null && activeNetwork.isConnected();
        } catch (NullPointerException e) {
            Log.e(HomeActivity.class.getSimpleName(), "Error retrieving connection state: ", e);
            return false;
        }
    }
}