package com.example.popularmovies.util;

import android.content.Context;
import android.content.SharedPreferences;

public class UserPreferenceStorage {

    private static final String POPULAR_MOVIES_STORAGE_KEY = "popular_movies_storage_key";

    private static UserPreferenceStorage userPreferenceStorage;
    private final SharedPreferences sharedPreferences;

    private UserPreferenceStorage(final Context context) {
        sharedPreferences = context.getSharedPreferences(
                POPULAR_MOVIES_STORAGE_KEY, Context.MODE_PRIVATE
        );
    }

    public static UserPreferenceStorage getInstance(final Context context) {
        if (userPreferenceStorage != null) {
            return userPreferenceStorage;
        }

        userPreferenceStorage = new UserPreferenceStorage(context);
        return userPreferenceStorage;
    }

    public void savePreference(final String key, final String value) {
        sharedPreferences.edit().putString(key, value).apply();
    }

    public final String getPreference(final String key) {
        return sharedPreferences.getString(key, "");
    }

    public final Boolean contains(final String key) {
        return sharedPreferences.contains(key);
    }
}