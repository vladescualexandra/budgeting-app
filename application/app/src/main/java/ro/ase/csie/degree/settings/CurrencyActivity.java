package ro.ase.csie.degree.settings;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;


import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import ro.ase.csie.degree.R;
import ro.ase.csie.degree.SplashActivity;
import ro.ase.csie.degree.async.Callback;
import ro.ase.csie.degree.firebase.FirebaseService;
import ro.ase.csie.degree.model.Account;
import ro.ase.csie.degree.model.Currency;
import ro.ase.csie.degree.util.CurrencyJSONParser;


public class CurrencyActivity extends AppCompatActivity {

    private ImageButton ib_back;
    private EditText et_search;
    private ListView lv_currencies;

    private List<Currency> currencyList = new ArrayList<>();
    private Currency currency = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency);

        initComponents();
    }

    private void initComponents() {
        ib_back = findViewById(R.id.currency_back);
        ib_back.setOnClickListener(v -> finish());

        et_search = findViewById(R.id.currency_search);
        et_search.addTextChangedListener(textChangedEventListener());
        currencyList = CurrencyJSONParser.getCurrencies();

        lv_currencies = findViewById(R.id.currency_list);
        setAdapter();
        lv_currencies.setOnItemClickListener((parent, view, position, id) -> {
            currency = currencyList.get(position);
            Account.getInstance().setCurrency(currency);
            Account.updateAccount();
        });

        lv_currencies.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        lv_currencies.setSelector(R.color.rally_dark_green);
    }

    private void setAdapter() {
        ArrayAdapter<Currency> adapter = new ArrayAdapter<>
                (getApplicationContext(),
                        R.layout.row_item_currency,
                        currencyList);
        lv_currencies.setAdapter(adapter);
    }


    private TextWatcher textChangedEventListener() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ArrayAdapter adapter = (ArrayAdapter) lv_currencies.getAdapter();
                adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
    }
}