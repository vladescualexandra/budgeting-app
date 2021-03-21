package ro.ase.csie.degree.settings;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

import ro.ase.csie.degree.R;
import ro.ase.csie.degree.firebase.Callback;
import ro.ase.csie.degree.firebase.FirebaseService;
import ro.ase.csie.degree.model.Account;
import ro.ase.csie.degree.model.Currency;
import ro.ase.csie.degree.util.CurrencyJSONParser;


public class CurrencyActivity extends AppCompatActivity {

    private ImageButton ib_back;
    private EditText et_search;
    private ListView lv_currencies;

    private FirebaseService<Account> accountFirebaseService;
    private List<Currency> currencyList = new ArrayList<>();
    private Currency currency = new Currency();

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
        lv_currencies.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                accountFirebaseService = FirebaseService.getInstance(getApplicationContext());
                accountFirebaseService.getAccount(getAccountCallback());
                currency = currencyList.get(position);
            }
        });
    }

    private Callback<Account> getAccountCallback() {
        return result -> {
            if (result != null) {
                Account account = new Account();
                account = result;
                account.setCurrency(currency);

                Log.e("getAccCallback", account.toString());
                Log.e("getAccCallback", account.getCurrency().toString());

                accountFirebaseService.upsert(account);
            }
        };
    }

    private void setAdapter() {
        ArrayAdapter<Currency> adapter = new ArrayAdapter<>
                (getApplicationContext(),
                        R.layout.support_simple_spinner_dropdown_item,
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