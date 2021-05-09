package ro.ase.csie.degree.settings;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;


import ro.ase.csie.degree.MainActivity;
import ro.ase.csie.degree.R;

import ro.ase.csie.degree.SplashActivity;
import ro.ase.csie.degree.authentication.GoogleAuthentication;
import ro.ase.csie.degree.model.Account;
import ro.ase.csie.degree.settings.balances.BalancesActivity;
import ro.ase.csie.degree.settings.categories.CategoriesActivity;
import ro.ase.csie.degree.util.LanguageManager;
import ro.ase.csie.degree.util.Languages;
import ro.ase.csie.degree.util.Notifications;
import ro.ase.csie.degree.util.Streak;

public class SettingsActivity extends AppCompatActivity {

    public static final String REMINDERS = "reminders";
    public static final String PREFS_SETTINGS = "settings";
    private TextView tv_user_name;
    private TextView tv_user_email;
    private TextView tv_user_currency;
    private TextView tv_streak;
    private ImageButton btn_back;
    private Button btn_currency;
    private Button btn_converter;
    private Button btn_balances;
    private Button btn_categories;
    private Button btn_templates;
    private Button btn_theme;
    private Button btn_language;
    private Button btn_reminders;
    private SwitchCompat switch_reminder;
    private Button btn_contact;
    private Button btn_sign_out;

    SharedPreferences settings;
    SharedPreferences.Editor settingsEditor;

    private Context context;
    private Resources resources;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        settings = getSharedPreferences(PREFS_SETTINGS, Context.MODE_PRIVATE);

        initComponents();
        setAccount();
        initEventListeners();

    }

    private void initComponents() {
        tv_user_name = findViewById(R.id.account_name);
        tv_user_email = findViewById(R.id.account_email);
        tv_user_currency = findViewById(R.id.account_currency);
        tv_streak = findViewById(R.id.account_streak);
        btn_back = findViewById(R.id.settings_back);
        btn_currency = findViewById(R.id.settings_currency);
        btn_converter = findViewById(R.id.settings_converter);
        btn_balances = findViewById(R.id.settings_balances);
        btn_categories = findViewById(R.id.settings_categories);
        btn_templates = findViewById(R.id.settings_templates);
        btn_theme = findViewById(R.id.settings_theme);
        btn_language = findViewById(R.id.settings_language);
        btn_reminders = findViewById(R.id.settings_reminders);
        switch_reminder = findViewById(R.id.settings_switch_reminders);
        btn_contact = findViewById(R.id.settings_contact);
        btn_sign_out = findViewById(R.id.settings_sign_out);
    }

    private void setAccount() {
        tv_user_name.setText(Account.getInstance().getName());
        tv_user_email.setText(Account.getInstance().getEmail());
        if (Account.getInstance().getCurrency() != null) {
            tv_user_currency.setText("Currency: " + Account.getInstance().getCurrency().toString());
        } else {
            tv_user_currency.setText("");
        }
        tv_streak.setText(Streak.days + " days streak");
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void initEventListeners() {
        btn_back.setOnClickListener(v -> finish());

        btn_balances.setOnClickListener(balancesEventListener());
        btn_categories.setOnClickListener(categoriesEventListener());
        btn_currency.setOnClickListener(currencyEventListener());
        btn_converter.setOnClickListener(converterEventListener());
        btn_templates.setOnClickListener(templatesEventListener());
        btn_theme.setOnClickListener(themeEventListener());
        btn_language.setOnClickListener(languageEventListener());

        switch_reminder.setChecked(settings.getBoolean(REMINDERS, false));
        switch_reminder.setOnCheckedChangeListener(remindersEventListener());

        btn_contact.setOnClickListener(contactEventListener());
        btn_sign_out.setOnClickListener(signOutClickListener());
    }

    private View.OnClickListener signOutClickListener() {
        return v -> {
            Account.signOut(getApplicationContext());

            GoogleAuthentication googleAuthentication = new GoogleAuthentication(getApplicationContext());
            googleAuthentication.signOut();

            Intent intent = new Intent(getApplicationContext(), SplashActivity.class);
            startActivity(intent);
        };
    }


    private View.OnClickListener converterEventListener() {
        return v -> {
            Intent intent = new Intent(getApplicationContext(), ConverterActivity.class);
            startActivity(intent);
        };
    }

    private View.OnClickListener currencyEventListener() {
        return v -> {
            Intent intent = new Intent(getApplicationContext(), CurrencyActivity.class);
            startActivity(intent);
        };
    }


    private View.OnClickListener balancesEventListener() {
        return v -> {
            Intent intent = new Intent(getApplicationContext(), BalancesActivity.class);
            startActivity(intent);
        };
    }

    private View.OnClickListener categoriesEventListener() {
        return v -> {
            Intent intent = new Intent(getApplicationContext(), CategoriesActivity.class);
            startActivity(intent);
        };
    }

    private View.OnClickListener templatesEventListener() {
        return v -> {
            Intent intent = new Intent(getApplicationContext(), TemplatesActivity.class);
            startActivity(intent);
        };
    }

    private View.OnClickListener themeEventListener() {
        return v -> {

        };
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private View.OnClickListener languageEventListener() {
        return v -> {
            new AlertDialog.Builder(this)
                    .setSingleChoiceItems(new CharSequence[]{Languages.ENGLISH.toString(), Languages.ROMANIAN.toString()},
                            0, (dialog, which) -> {
                                String selectedLanguage = Languages.ENGLISH.toString();
                                if (which == 1) {
                                    selectedLanguage = Languages.ROMANIAN.toString();
                                }
                                context = LanguageManager.setLanguage(this, selectedLanguage);

                                getBaseContext().getResources().updateConfiguration(LanguageManager.configuration,
                                        getBaseContext().getResources().getDisplayMetrics());

                                resources = context.getResources();

                                setContentView(R.layout.activity_settings);

                                Toast.makeText(getApplicationContext(),
                                        selectedLanguage,
                                        Toast.LENGTH_LONG).show();
                            })
                    .show();
        };
    }

    private CompoundButton.OnCheckedChangeListener remindersEventListener() {
        return (buttonView, isChecked) -> {
            if (isChecked) {
                Notifications.scheduleNotification(this);
            } else {
                Notifications.cancelNotification(this);
            }

            settingsEditor = settings.edit();
            settingsEditor.putBoolean(REMINDERS, isChecked);
            settingsEditor.apply();
        };
    }

    private View.OnClickListener contactEventListener() {
        return v -> {
            Intent intent = new Intent(getApplicationContext(), ContactActivity.class);
            startActivity(intent);
        };
    }


}