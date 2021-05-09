package ro.ase.csie.degree.util;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.preference.PreferenceManager;
import android.util.Log;


import androidx.annotation.RequiresApi;

import java.util.Locale;

public class LanguageManager {

    public static Configuration configuration;

    private static final String SELECTED_LANGUAGE = "Locale.Helper.Selected.Language";

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static Context setLanguage(Context context, String language) {
        persist(context, language);

        Log.e("test", "Build.VERSION.SDK_INT: " + Build.VERSION.SDK_INT);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return updateResources(context, language);
        }
        return updateResourcesLegacy(context, language);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private static Context updateResources(Context context, String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);

        configuration = context.getResources().getConfiguration();
        configuration.locale = locale;
        configuration.setLayoutDirection(locale);

        Log.e("test", "updateResources: " + locale.getDisplayLanguage());

        return context;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private static Context updateResourcesLegacy(Context context, String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);

        Resources resources = context.getResources();

        Configuration configuration = resources.getConfiguration();
        configuration.setLocale(locale);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            configuration.setLayoutDirection(locale);
        }

        resources.updateConfiguration(configuration, resources.getDisplayMetrics());

        Log.e("test", "updateResourcesLegacy");

        return context;
    }

    private static void persist(Context context, String language) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(SELECTED_LANGUAGE, language);
        editor.apply();
    }

}
