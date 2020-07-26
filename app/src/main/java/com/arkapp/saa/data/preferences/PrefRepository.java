package com.arkapp.saa.data.preferences;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.arkapp.saa.data.models.Course;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import static com.arkapp.saa.data.preferences.Constants.PREFERENCE_NAME;
import static com.arkapp.saa.data.preferences.Constants.PREF_CURRENT_COURSE;
import static com.arkapp.saa.data.preferences.Constants.PREF_LOGGED_IN;

/**
 * This is the UTILITY class for using shared preferences easily.
 */

public class PrefRepository {
    private SharedPreferences pref;
    private Editor editor;
    private Gson gson;

    @SuppressLint("CommitPrefEdits")
    public PrefRepository(@NotNull Context context) {
        this.pref = context.getSharedPreferences(PREFERENCE_NAME, 0);
        this.editor = pref.edit();
        this.gson = new Gson();
    }

    public void clearData() {
        editor.clear();
        editor.commit();
    }

    public void setLoggedIn(boolean isLoggedIn) {
        editor.putBoolean(PREF_LOGGED_IN, isLoggedIn);
        editor.commit();
    }

    public boolean setLoggedIn() {
        return pref.getBoolean(PREF_LOGGED_IN, false);
    }

    public void setCurrentCourse(Course course) {
        editor.putString(PREF_CURRENT_COURSE, gson.toJson(course));
        editor.commit();
    }

    public Course getCurrentCourse() {
        String data = pref.getString(PREF_CURRENT_COURSE, "");
        if (data.isEmpty()) {
            return null;
        } else
            return gson.fromJson(data, Course.class);
    }
}
