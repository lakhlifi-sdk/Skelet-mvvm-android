package com.example.skelet_android_mvvm.datamanager.sharedpref;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Size;

import java.util.Map;
import java.util.Set;

public class PreferenceManager {

    private static final String TAG = PreferenceManager.class.getSimpleName();

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private SharedPreferences.OnSharedPreferenceChangeListener changeListener;

    public PreferenceManager(Builder builder) {
        setup(builder);
    }

    @SuppressLint("CommitPrefEdits")
    private void setup(Builder builder) {
        Context context = builder.context;
        int mode = builder.preferencesMode;
        String name = builder.preferencesName.trim().replace(" ", "");
        Activity activity = builder.preferencesActivity;
        this.changeListener = builder.preferencesListener;

        if (activity != null) {
            sharedPreferences = activity.getPreferences(mode);
            sharedPreferences.registerOnSharedPreferenceChangeListener(this.changeListener);
            this.editor = sharedPreferences.edit();
        } else {
            if (!name.equals("")) {
                sharedPreferences = context.getSharedPreferences(name, mode);
                sharedPreferences.registerOnSharedPreferenceChangeListener(this.changeListener);
                this.editor = sharedPreferences.edit();
            } else {
                throw new IllegalArgumentException("PreferencesManager: Missing Argument: provide either a name or an activity.");
            }
        }
    }

    public void unregisterChangeListener() {
        if (this.changeListener != null) {
            sharedPreferences.unregisterOnSharedPreferenceChangeListener(this.changeListener);
        }
    }

    public boolean contains(String key) {
        return sharedPreferences.contains(key);
    }

    public String getValue(String key, String defaultValue) {
        return sharedPreferences.getString(key, defaultValue);
    }

    public Boolean getValue(String key, boolean defaultValue) {
        return sharedPreferences.getBoolean(key, defaultValue);
    }

    public int getValue(String key, int defaultValue) {
        return sharedPreferences.getInt(key, defaultValue);
    }

    public float getValue(String key, float defaultValue) {
        return sharedPreferences.getFloat(key, defaultValue);
    }

    public long getValue(String key, long defaultValue) {
        return sharedPreferences.getLong(key, defaultValue);
    }

    public void putValue(@NonNull String key, @NonNull Object value) {
        if (!key.isEmpty()) {
            try {
                if (value instanceof String) {
                    editor.putString(key, value.toString()).apply();
                    return;
                }
                if (value instanceof Boolean) {
                    editor.putBoolean(key, (boolean) value).apply();
                    return;
                }
                if (value instanceof Integer) {
                    editor.putInt(key, (Integer) value).apply();
                    return;
                }
                if (value instanceof Float) {
                    editor.putFloat(key, (Float) value).apply();
                    return;
                }
                if (value instanceof Long) {
                    editor.putLong(key, (Long) value).apply();
                }
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }
        } else {
            Log.w(TAG, "putValue(): Null args!");
        }
    }

    public void putAll(@NonNull Map<String, ?> data) {
        try {
            Set set = data.entrySet();
            for (Object aSet : set) {
                Map.Entry entry = (Map.Entry) aSet;
                putValue(entry.getKey().toString(), entry.getValue());
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }

    public void clearAll() {
        editor.clear().apply();
    }

    public void clearValue(@NonNull @Size(min = 1) String key) {
        try {
            editor.remove(key).apply();
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }

    public static class Builder {

        private Context context;
        private int preferencesMode;
        private String preferencesName;
        private Activity preferencesActivity;
        private SharedPreferences.OnSharedPreferenceChangeListener preferencesListener;

        public Builder(@NonNull Context context, int mode) {
            this.context = context;
            this.preferencesMode = mode;
        }

        public Builder name(String name) {
            this.preferencesName = name;
            return this;
        }

        public Builder activity(Activity activity) {
            this.preferencesActivity = activity;
            return this;
        }

        public Builder listener(SharedPreferences.OnSharedPreferenceChangeListener listener) {
            this.preferencesListener = listener;
            return this;
        }

        public PreferenceManager build() {
            return new PreferenceManager(this);
        }

    }
}