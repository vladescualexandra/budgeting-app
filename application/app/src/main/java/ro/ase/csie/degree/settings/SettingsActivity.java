package ro.ase.csie.degree.settings;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.List;

import ro.ase.csie.degree.R;

import ro.ase.csie.degree.SplashActivity;
import ro.ase.csie.degree.authentication.GoogleAuthentication;
import ro.ase.csie.degree.model.Account;
import ro.ase.csie.degree.model.Currency;
import ro.ase.csie.degree.settings.balances.BalancesActivity;
import ro.ase.csie.degree.settings.categories.CategoriesActivity;
import ro.ase.csie.degree.util.CurrencyJSONParser;

public class SettingsActivity extends AppCompatActivity {

    private TextView tv_user_name;
    private TextView tv_user_email;
    private ImageButton btn_back;
    private SearchableSpinner spn_currency;
    private Button btn_balances;
    private Button btn_categories;
    private Button btn_templates;
    private Button btn_theme;
    private Button btn_language;
    private Button btn_reminders;
    private Button btn_contact;
    private Button btn_sign_out;

    private SharedPreferences user_info;
    private Account account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        initComponents();
        initUser();
        initEventListeners();
        setCurrencyAdapter();

    }

    private void initComponents() {
        tv_user_name = findViewById(R.id.account_name);
        tv_user_email = findViewById(R.id.account_email);

        btn_back = findViewById(R.id.settings_back);
        spn_currency = findViewById(R.id.settings_currency);
        btn_balances = findViewById(R.id.settings_balances);
        btn_categories = findViewById(R.id.settings_categories);
        btn_templates = findViewById(R.id.settings_templates);
        btn_theme = findViewById(R.id.settings_theme);
        btn_language = findViewById(R.id.settings_language);
        btn_reminders = findViewById(R.id.settings_reminders);
        btn_contact = findViewById(R.id.settings_contact);
        btn_sign_out = findViewById(R.id.settings_sign_out);
    }

    private void initUser() {
        user_info = getSharedPreferences(Account.USER_PREFS, MODE_PRIVATE);

        String user_key = user_info.getString(Account.USER_KEY, null);
//        String user_name = user_info.getString(User.USER_NAME, null);
//        String user_email = user_info.getString(User.USER_EMAIL, null);
//
//        if (user_key != null && user_name != null && user_email != null) {
//            user = new User(user_key, user_name, user_email);
//
//            tv_user_name.setText(user.getName());
//            tv_user_email.setText(user.getEmail());
//        } else {
//            Toast.makeText(getApplicationContext(),
//                    "Something is null.", Toast.LENGTH_LONG).show();
//        }
    }

    private void initEventListeners() {
        btn_back.setOnClickListener(v -> finish());

        btn_balances.setOnClickListener(balancesEventListener());
        btn_categories.setOnClickListener(categoriesEventListener());
        btn_templates.setOnClickListener(templatesEventListener());
        btn_theme.setOnClickListener(themeEventListener());
        btn_language.setOnClickListener(languageEventListener());
        btn_reminders.setOnClickListener(remindersEventListener());
        btn_contact.setOnClickListener(contactEventListener());

        btn_sign_out.setOnClickListener(v -> {
            SharedPreferences.Editor editor = user_info.edit();
            editor.remove(Account.USER_KEY);
            editor.apply();

            GoogleAuthentication googleAuthentication = new GoogleAuthentication(getApplicationContext());
            googleAuthentication.signOut();

            Intent intent = new Intent(getApplicationContext(), SplashActivity.class);
            startActivity(intent);
        });
    }

    private void setCurrencyAdapter() {
        List<Currency> currencyList = CurrencyJSONParser.getCurrencies();
        ArrayAdapter<Currency> adapter = new ArrayAdapter<>
                (getApplicationContext(),
                        R.layout.support_simple_spinner_dropdown_item,
                        currencyList);
        spn_currency.setAdapter(adapter);
        spn_currency.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(),
                        currencyList.get(position).toString(),
                        Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
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

        };
    }


}