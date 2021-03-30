package ro.ase.csie.degree.settings;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.renderscript.Sampler;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.time.temporal.ValueRange;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import ro.ase.csie.degree.R;
import ro.ase.csie.degree.async.AsyncTaskRunner;
import ro.ase.csie.degree.async.Callback;
import ro.ase.csie.degree.model.Currency;
import ro.ase.csie.degree.network.HttpManager;
import ro.ase.csie.degree.util.CurrencyJSONParser;

public class ConverterActivity extends AppCompatActivity {

    private ImageButton btn_back;
    private EditText et_amount_from;
    private Spinner spn_currency_from;
    private TextView tv_amount_to;
    private Spinner spn_currency_to;
    private Button btn_convert;

    private List<Currency> currencyList = new ArrayList<>();


    private final String server = "https://free.currconv.com";
    private final String api = "de2df0aa08b9a8e38beb";

    private AsyncTaskRunner asyncTaskRunner = new AsyncTaskRunner();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_converter);

        initComponents();
        setAdapters();
    }

    private void initComponents() {
        btn_back = findViewById(R.id.converter_back);
        btn_back.setOnClickListener(v -> finish());
        et_amount_from = findViewById(R.id.converter_amount_from);
        spn_currency_from = findViewById(R.id.converter_currency_from);
        tv_amount_to = findViewById(R.id.converter_amount_to);
        spn_currency_to = findViewById(R.id.converter_currency_to);
        btn_convert = findViewById(R.id.converter_convert);
        btn_convert.setOnClickListener(convertEventListener());
    }

    private View.OnClickListener convertEventListener() {
        return v -> {
            double amount_from = Double.parseDouble(et_amount_from.getText().toString().trim());
            Currency currency_from = (Currency) spn_currency_from.getSelectedItem();
            Currency currency_to = (Currency) spn_currency_to.getSelectedItem();

            String url = buildURL(currency_from, currency_to);
            getConversionFromURL(url, amount_from, currency_from.getCode(), currency_to.getCode());
        };
    }

    private void getConversionFromURL(String url, double amount_from, String from, String to) {
        Callable<String> asyncOperation = new HttpManager(url);
        Callback<String> mainThreadOperation = getMainThreadOperation(amount_from, from, to);
        asyncTaskRunner.executeAsync(asyncOperation, mainThreadOperation);
    }

    private Callback<String> getMainThreadOperation(double amount_from, String from, String to) {
        return result -> {
            if (result != null) {
                String json = from + "_" + to;
                try {
                    JSONObject object = new JSONObject(result);
                    double rate = object.getDouble(json);
                    double amount_to = rate * amount_from;
                    tv_amount_to.setText(String.valueOf(amount_to));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
    }


    private String buildURL(Currency currency_from, Currency currency_to) {
        String url = server + "/api/v7/convert?q=" + currency_from.getCode() + "_" + currency_to.getCode() + "&compact=ultra&apiKey=" + api;
        Log.e("buildURL", url);
        return url;
    }

    private void setAdapters() {
        currencyList = CurrencyJSONParser.getCurrencies();
        ArrayAdapter<Currency> adapter = new ArrayAdapter<>
                (getApplicationContext(),
                        R.layout.row_spinner_currency,
                        currencyList);
        spn_currency_from.setAdapter(adapter);
        spn_currency_to.setAdapter(adapter);
    }


}