package ro.ase.csie.degree;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.google.android.material.textfield.TextInputEditText;

public class AddTransactionActivity extends AppCompatActivity {


    private RadioGroup rg_type;
    private TextInputEditText tiet_details;
    private TextInputEditText tiet_amount;
    private Spinner spn_category;
    private Spinner spn_balances;
    private Button btn_date;
    private Button btn_save;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);

        initComponents();
    }

    private void initComponents() {
        rg_type = findViewById(R.id.add_transaction_type);
        tiet_details = findViewById(R.id.add_transaction_details);
        tiet_amount = findViewById(R.id.add_transaction_amount);
        spn_category = findViewById(R.id.add_transaction_category);
        spn_balances = findViewById(R.id.add_transaction_balance);
        btn_date = findViewById(R.id.add_transaction_date);
        btn_save = findViewById(R.id.add_transaction_save);

        btn_date.setOnClickListener(dateDialogEventListener());

        btn_save.setOnClickListener(saveTransactionEventListener());

        setCategoryAdapter();
        setBalanceAdapter();

    }

    private View.OnClickListener dateDialogEventListener() {
        return v -> {

        };
    }

    private View.OnClickListener saveTransactionEventListener() {
        return v -> {

        };
    }

    private void setCategoryAdapter() {
    }

    private void setBalanceAdapter() {
    }
}