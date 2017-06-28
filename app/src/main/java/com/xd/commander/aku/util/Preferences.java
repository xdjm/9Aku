package com.xd.commander.aku.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Fantasy on 2016/3/3.
 */
class Preferences {
    private static final String shared_name="user_guide";

    static String getString(Context context, String key,
                            String defaultValues) {
        SharedPreferences sp = context.getSharedPreferences(shared_name,
                Context.MODE_PRIVATE);
        return sp.getString(key, defaultValues);
    }

    static void setString(Context context, String key, String Values) {
        SharedPreferences sp = context.getSharedPreferences(shared_name,
                Context.MODE_PRIVATE);
        sp.edit().putString(key, Values).apply();
    }
}
