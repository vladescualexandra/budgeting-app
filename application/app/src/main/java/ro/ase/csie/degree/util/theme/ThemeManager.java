package ro.ase.csie.degree.util.theme;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDelegate;

import ro.ase.csie.degree.util.theme.Themes;

public class ThemeManager {

    private static final String SELECTED_THEME = "Locale.Helper.Selected.Theme";

    public static void setSelectedTheme(Context context, Themes theme) {
        Toast.makeText(context,
                theme.toString(),
                Toast.LENGTH_LONG).show();
        if (theme.equals(Themes.NIGHT)) {
            AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_YES
            );
            context.setTheme(android.R.style.Theme_Holo_Light_DarkActionBar);
        } else {
            AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_NO
            );
        }

        persist(context, theme.toString());
    }

    public static Themes getTheme(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String s = preferences.getString(SELECTED_THEME, Themes.NIGHT.toString());
        if (s.equals(Themes.NIGHT.toString())) {
            return Themes.NIGHT;
        } else {
            return Themes.LIGHT;
        }
    }

    public static void persist(Context context, String theme) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(SELECTED_THEME, theme);
        editor.apply();
    }
}
