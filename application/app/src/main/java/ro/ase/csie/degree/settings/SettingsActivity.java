package ro.ase.csie.degree.settings;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;


import ro.ase.csie.degree.R;

import ro.ase.csie.degree.SplashActivity;
import ro.ase.csie.degree.authentication.GoogleAuthentication;
import ro.ase.csie.degree.async.Callback;
import ro.ase.csie.degree.firebase.FirebaseService;
import ro.ase.csie.degree.model.Account;
import ro.ase.csie.degree.settings.balances.BalancesActivity;
import ro.ase.csie.degree.settings.categories.CategoriesActivity;
import ro.ase.csie.degree.util.ReminderReceiver;
import ro.ase.csie.degree.util.Streak;

public class SettingsActivity extends AppCompatActivity {

    public static final String REMINDERS = "reminders";
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
    private Switch switch_reminder;
    private Button btn_contact;
    private Button btn_sign_out;

    public static final String CHANNEL_ID = "CHANNEL_ID";

    SharedPreferences settings;
    SharedPreferences.Editor settingsEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        settings = getSharedPreferences("settings", Context.MODE_PRIVATE);

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
        btn_sign_out.setOnClickListener(v -> {
            Account.signOut(getApplicationContext());

            GoogleAuthentication googleAuthentication = new GoogleAuthentication(getApplicationContext());
            googleAuthentication.signOut();

            Intent intent = new Intent(getApplicationContext(), SplashActivity.class);
            startActivity(intent);
        });
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

        };
    }

    private View.OnClickListener themeEventListener() {
        return v -> {

        };
    }

    private View.OnClickListener languageEventListener() {
        return v -> {

        };
    }

    private CompoundButton.OnCheckedChangeListener remindersEventListener() {
        return (buttonView, isChecked) -> {
            PendingIntent pendingIntent = getPendingIntent(buildNotification());
            if (isChecked) {
                scheduleNotification(pendingIntent);
            } else {
                cancelNotification(pendingIntent);
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

    private Notification buildNotification() {
        return new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.common_google_signin_btn_icon_dark)
                .setContentTitle("Making transactions?")
                .setContentText("Don't forget to budget!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true)
                .build();
    }

    private PendingIntent getPendingIntent(Notification notification) {
        Intent notificationIntent = new Intent(this, ReminderReceiver.class);
        notificationIntent.putExtra(ReminderReceiver.NOTIFICATION_ID, 1);
        notificationIntent.putExtra(ReminderReceiver.NOTIFICATION, notification);
        return PendingIntent.getBroadcast(this,
                0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private void scheduleNotification(PendingIntent pendingIntent) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        assert alarmManager != null;
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, 0,
                AlarmManager.INTERVAL_DAY, pendingIntent);
    }

    private void cancelNotification(PendingIntent pendingIntent) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        assert alarmManager != null;
        alarmManager.cancel(pendingIntent);
    }

}