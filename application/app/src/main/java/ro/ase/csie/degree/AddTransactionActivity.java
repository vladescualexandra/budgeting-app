package ro.ase.csie.degree;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputEditText;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import ro.ase.csie.degree.firebase.Callback;
import ro.ase.csie.degree.firebase.FirebaseService;
import ro.ase.csie.degree.firebase.Table;
import ro.ase.csie.degree.model.Balance;
import ro.ase.csie.degree.model.Category;
import ro.ase.csie.degree.model.Transaction;
import ro.ase.csie.degree.model.TransactionType;
import ro.ase.csie.degree.util.DateConverter;
import ro.ase.csie.degree.util.InputValidation;

public class AddTransactionActivity extends AppCompatActivity {


    private RadioGroup rg_type;
    private TextInputEditText tiet_details;
    private TextInputEditText tiet_amount;
    private Spinner spn_category;
    private Spinner spn_balances;
    private Button btn_date;
    private Button btn_save;


    private Transaction transaction = new Transaction();

    private List<Category> expenseCategories = new ArrayList<>();
    private List<Category> incomeCategories = new ArrayList<>();
    private List<Balance> balances = new ArrayList<>();


    int year;
    int month;
    int day;

    private FirebaseService firebaseService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);

        initComponents();


        initDefaults();
        retrieveDataFromFirebase();

    }

    private void retrieveDataFromFirebase() {
        firebaseService = FirebaseService.getInstance(getApplicationContext(), Table.BUDGET);
        firebaseService.updateCategoriesUI(updateCategoriesCallback());
        firebaseService.updateBalancesUI(updateBalancesCallback());

    }

    private Callback<List<Category>> updateCategoriesCallback() {
        return result -> {

            if (result != null) {
                for (Category category : result) {
                    if (category != null) {
                        if (category.getType() != null) {
                            if (category.getType().equals(TransactionType.EXPENSE)) {
                                expenseCategories.add(category);
                            } else if (category.getType().equals(TransactionType.INCOME)) {
                                incomeCategories.add(category);
                            }
                        }
                    }
                    setCategoryAdapter();
                }
            }
        };
    }

    private Callback<List<Balance>> updateBalancesCallback() {
        return result -> {

            if (result != null) {
                balances.addAll(result);
                setBalanceAdapter();
            }
        };
    }


    private void initDefaults() {
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        transaction.setDate(DateConverter.toDate(day, month, year));

        transaction.getCategory().setType(rg_type.getCheckedRadioButtonId()
                == R.id.add_transaction_type_expense
                ? TransactionType.EXPENSE : TransactionType.INCOME);
    }

    private void initComponents() {
        rg_type = findViewById(R.id.add_transaction_type);
        tiet_details = findViewById(R.id.add_transaction_details);
        tiet_amount = findViewById(R.id.add_transaction_amount);
        spn_category = findViewById(R.id.add_transaction_category);
        spn_balances = findViewById(R.id.add_transaction_balance);
        btn_date = findViewById(R.id.add_transaction_date);
        btn_save = findViewById(R.id.add_transaction_save);

        rg_type.setOnCheckedChangeListener(changeTypeEventListener());
        btn_date.setOnClickListener(dateDialogEventListener());
        btn_save.setOnClickListener(saveTransactionEventListener());
    }

    private RadioGroup.OnCheckedChangeListener changeTypeEventListener() {
        return (group, checkedId) -> {
            if (checkedId == R.id.add_transaction_type_expense) {
                transaction.getCategory().setType(TransactionType.EXPENSE);
            } else {
                transaction.getCategory().setType(TransactionType.INCOME);
            }
            setCategoryAdapter();
        };
    }

    private View.OnClickListener dateDialogEventListener() {
        return v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    AddTransactionActivity.this,
                    setDateEventListener(),
                    year, month, day);
            datePickerDialog.show();
        };
    }

    private DatePickerDialog.OnDateSetListener setDateEventListener() {
        return (view, year, month, dayOfMonth) -> {
            transaction.setDate(DateConverter.toDate(dayOfMonth, month, year));
            btn_date.setText(DateConverter.format(dayOfMonth, month, year));
        };
    }

    private View.OnClickListener saveTransactionEventListener() {
        return v -> {

            if (!tiet_details.getText().toString().trim().isEmpty()) {
                transaction.setDetails(tiet_details.getText().toString().trim());
            }

            InputValidation validation = new InputValidation(getApplicationContext());

            TransactionType type = transaction.getCategory().getType();
            double available_amount = transaction.getBalance().getAvailable_amount();
            double amount = Double.parseDouble(tiet_amount.getText().toString());

            if (validation.amountValidation(type, available_amount, amount)) {
                transaction.setAmount(amount);

                Balance balance = (Balance) spn_balances.getSelectedItem();
                Category category = (Category) spn_category.getSelectedItem();

                transaction.setBalance(balance);
                transaction.setCategory(category);


                saveTransaction();
            }

        };
    }

    private void saveTransaction() {
        Intent intent = getIntent();
        intent.putExtra(MainActivity.NEW_TRANSACTION, transaction);
        setResult(RESULT_OK, intent);
        finish();
    }

    private void setCategoryAdapter() {
        ArrayAdapter<Category> adapter =
                new ArrayAdapter<>
                        (getApplicationContext(),
                                android.R.layout.simple_spinner_item,
                                getCategoriesByType());
        spn_category.setAdapter(adapter);
        if (getCategoriesByType().size() > 0) {
            transaction.setCategory(getCategoriesByType().get(0));
        }
        spn_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (transaction.getCategory().getType().equals(TransactionType.EXPENSE)) {
                    transaction.setCategory(expenseCategories.get(position));
                } else {
                    transaction.setCategory(incomeCategories.get(position));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setBalanceAdapter() {
        ArrayAdapter<Balance> adapter =
                new ArrayAdapter<>
                        (getApplicationContext(),
                                android.R.layout.simple_spinner_item,
                                balances);
        spn_balances.setAdapter(adapter);
        if (balances.size() > 0) {
            transaction.setBalance(balances.get(0));
        }

        spn_balances.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                transaction.setBalance(balances.get(position));
                Log.e("setBalanceAdapter", transaction.getBalance().getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private List<Category> getCategoriesByType() {
        if (rg_type.getCheckedRadioButtonId() == R.id.add_transaction_type_expense) {
            return expenseCategories;
        } else {
            return incomeCategories;
        }
    }
}