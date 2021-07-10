package ro.ase.csie.degree.settings;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;


import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

import ro.ase.csie.degree.R;
import ro.ase.csie.degree.async.Callback;
import ro.ase.csie.degree.model.Account;
import ro.ase.csie.degree.model.Currency;
import ro.ase.csie.degree.network.CurrenciesManager;
import ro.ase.csie.degree.util.CurrencyJSONParser;


public class CurrencyActivity extends AppCompatActivity {

    private ImageButton ib_back;
    private TextInputEditText tiet_search;
    private ListView lv_currencies;

    private List<Currency> currencyList = new ArrayList<>();
    private List<Currency> filteredList = new ArrayList<>();
    private Currency currency = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency);

        initComponents();
        CurrenciesManager.getCurrenciesFromURL(currenciesCallback());
    }

    private void initComponents() {
        ib_back = findViewById(R.id.currency_back);
        ib_back.setOnClickListener(v -> finish());

        tiet_search = findViewById(R.id.currency_search);
        tiet_search.addTextChangedListener(textChangedEventListener());

        lv_currencies = findViewById(R.id.currency_list);
        lv_currencies.setOnItemClickListener(setCurrency());

        lv_currencies.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        lv_currencies.setSelector(R.color.rally_dark_green);
    }

    private AdapterView.OnItemClickListener setCurrency() {
        return (parent, view, position, id) -> {

            currency = filteredList.get(position);
            Log.e("test", currency.toString());

            Account.getInstance().setCurrency(currency);
            Account.updateAccount();
        };
    }

    private void setAdapter() {
        ArrayAdapter<Currency> adapter = new ArrayAdapter<>
                (getApplicationContext(),
                        R.layout.row_item_currency,
                        filteredList);
        lv_currencies.setAdapter(adapter);
    }

    private Callback<String> currenciesCallback() {
        return result -> {
            if (result != null) {
                currencyList.clear();
                currencyList = CurrencyJSONParser.getCurrencies(result);
                filteredList.clear();
                filteredList.addAll(currencyList);
                setAdapter();
            }
        };
    }


    private TextWatcher textChangedEventListener() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filteredList = filter(s);
                ArrayAdapter adapter = (ArrayAdapter) lv_currencies.getAdapter();
                adapter.notifyDataSetChanged();
                Log.e("test", filteredList.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        };
    }

    public List<Currency> filter(CharSequence filter) {
        filteredList.clear();
        for (Currency currency : currencyList) {
            if (currency.getCode().toLowerCase().contains(filter.toString().toLowerCase())
                    || currency.getName().toLowerCase().contains(filter.toString().toLowerCase())) {
                filteredList.add(currency);
            }
        }
        return filteredList;
    }
}