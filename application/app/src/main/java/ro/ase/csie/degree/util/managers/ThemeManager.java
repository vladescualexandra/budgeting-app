package ro.ase.csie.degree.util.managers;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import androidx.appcompat.app.AppCompatDelegate;

public class ThemeManager {

    private static final String SELECTED_THEME = "Locale.Helper.Selected.Theme";

    public static void setSelectedTheme(Context context, boolean on) {
        if (on) {
            AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_YES
            );
        } else {
            AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_NO
            );
        }

        persist(context, on);
    }

    public static boolean getTheme(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getBoolean(SELECTED_THEME, false);
    }

    public static void persist(Context context, boolean on) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(SELECTED_THEME, on);
        editor.apply();
    }
}
