package ro.ase.csie.degree.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.preference.PreferenceManager;
import android.widget.Toast;


import androidx.annotation.RequiresApi;

import java.util.Locale;

public class LocaleHelper {

    public static Configuration configuration;

    private static final String SELECTED_LANGUAGE = "Locale.Helper.Selected.Language";
    private static final String SELECTED_THEME = "Locale.Helper.Selected.Theme";
    public static final String SELECTED_REMINDERS = "Locale.Helper.Selected.Reminders";

    public static String getSelectedLanguage(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String language = preferences.getString(SELECTED_LANGUAGE, null);
        if (language == null) {
            language = Languages.ENGLISH.toString();
        }
        return language;
    }

    public static String getSelectedTheme(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String theme = preferences.getString(SELECTED_THEME, null);
        if (theme == null) {
            theme = Themes.NIGHT.toString();
        }
        return theme;
    }

    public static boolean getSelectedReminders(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getBoolean(SELECTED_REMINDERS, false);
    }

    public static void getSettings(Context context) {
        Toast.makeText(context,
                getSelectedLanguage(context),
                Toast.LENGTH_LONG).show();
        setLanguage(context, getSelectedLanguage(context));
        setTheme(context, getSelectedTheme(context));
    }

    public static void apply(Context context) {
        context.getResources().updateConfiguration(configuration,
                context.getResources().getDisplayMetrics());
    }


    public static Context setLanguage(Context context, String language) {
        persist(context, SELECTED_LANGUAGE, language);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return updateResources(context, language);
        }
        return updateResourcesLegacy(context, language);
    }

    public static Context setTheme(Context context, String theme) {
        persist(context, SELECTED_THEME, theme);

        return null;
    }


    private static Context updateResources(Context context, String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);


        configuration = context.getResources().getConfiguration();
        configuration.locale = locale;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            configuration.setLayoutDirection(locale);
        }

        return context;
    }

    private static Context updateResourcesLegacy(Context context, String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);

        Resources resources = context.getResources();

        Configuration configuration = resources.getConfiguration();
        configuration.locale = locale;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            configuration.setLayoutDirection(locale);
        }

        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
        return context;
    }

    public static void persist(Context context, String key, String value) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.apply();
    }
}
