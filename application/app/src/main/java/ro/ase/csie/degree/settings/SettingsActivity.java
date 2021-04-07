package ro.ase.csie.degree.settings;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;


import ro.ase.csie.degree.R;

import ro.ase.csie.degree.SplashActivity;
import ro.ase.csie.degree.authentication.GoogleAuthentication;
import ro.ase.csie.degree.async.Callback;
import ro.ase.csie.degree.firebase.FirebaseService;
import ro.ase.csie.degree.model.Account;
import ro.ase.csie.degree.settings.balances.BalancesActivity;
import ro.ase.csie.degree.settings.categories.CategoriesActivity;

public class SettingsActivity extends AppCompatActivity {

    private TextView tv_user_name;
    private TextView tv_user_email;
    private TextView tv_user_currency;
    private ImageButton btn_back;
    private Button btn_currency;
    private Button btn_converter;
    private Button btn_balances;
    private Button btn_categories;
    private Button btn_templates;
    private Button btn_theme;
    private Button btn_language;
    private Button btn_reminders;
    private Button btn_contact;
    private Button btn_sign_out;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        initComponents();
        setAccount();
        initEventListeners();
    }

    private void initComponents() {
        tv_user_name = findViewById(R.id.account_name);
        tv_user_email = findViewById(R.id.account_email);
        tv_user_currency = findViewById(R.id.account_currency);
        btn_back = findViewById(R.id.settings_back);
        btn_currency = findViewById(R.id.settings_currency);
        btn_converter = findViewById(R.id.settings_converter);
        btn_balances = findViewById(R.id.settings_balances);
        btn_categories = findViewById(R.id.settings_categories);
        btn_templates = findViewById(R.id.settings_templates);
        btn_theme = findViewById(R.id.settings_theme);
        btn_language = findViewById(R.id.settings_language);
        btn_reminders = findViewById(R.id.settings_reminders);
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
        btn_reminders.setOnClickListener(remindersEventListener());
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

    private View.OnClickListener remindersEventListener() {
        return v -> {

        };
    }

    private View.OnClickListener contactEventListener() {
        return v -> {
            Intent intent = new Intent(getApplicationContext(), ContactActivity.class);
            startActivity(intent);
        };
    }
}