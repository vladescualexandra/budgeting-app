package ro.ase.csie.degree.settings.balances;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;

import ro.ase.csie.degree.R;
import ro.ase.csie.degree.model.Balance;
import ro.ase.csie.degree.util.InputValidation;

public class AddBalanceActivity extends AppCompatActivity {

    public static final String NEW_BALANCE = "new_balance";

    private TextInputEditText tiet_name;
    private TextInputEditText tiet_available_amount;
    private Button btn_save;

    private Balance balance;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_balance);

        initComponents();
    }

    private void initComponents() {
        intent = getIntent();

        tiet_name = findViewById(R.id.add_balance_name);
        tiet_available_amount = findViewById(R.id.add_balance_available_amount);
        btn_save = findViewById(R.id.add_balance_save);

        btn_save.setOnClickListener(saveBalanceEventListener());
    }

    private View.OnClickListener saveBalanceEventListener() {
        return v -> {
            if (InputValidation.nameValidation(getApplicationContext(), tiet_name)) {
                balance = new Balance();
                balance.setName(tiet_name.getText().toString().trim());
                if (InputValidation.availableAmountValidation(getApplicationContext(), tiet_available_amount)) {
                    balance.setAvailable_amount(Double.parseDouble(tiet_available_amount.getText().toString().trim()));
                    saveBalance();
                }
            }
        };
    }

    private void saveBalance() {
        intent.putExtra(NEW_BALANCE, balance);
        setResult(RESULT_OK, intent);
        finish();
    }

}