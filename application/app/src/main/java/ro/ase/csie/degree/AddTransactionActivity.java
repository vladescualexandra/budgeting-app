package ro.ase.csie.degree;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import ro.ase.csie.degree.async.Callback;
import ro.ase.csie.degree.firebase.FirebaseService;
import ro.ase.csie.degree.model.Balance;
import ro.ase.csie.degree.model.Category;
import ro.ase.csie.degree.model.Expense;
import ro.ase.csie.degree.model.Income;
import ro.ase.csie.degree.model.Transaction;
import ro.ase.csie.degree.model.TransactionType;
import ro.ase.csie.degree.model.Transfer;
import ro.ase.csie.degree.util.DateConverter;
import ro.ase.csie.degree.util.InputValidation;

public class AddTransactionActivity extends AppCompatActivity {


    private RadioGroup rg_type;
    private TextInputEditText tiet_details;
    private TextInputEditText tiet_amount;
    private Spinner spn_category;
    private Spinner spn_balances_from;
    private Spinner spn_balances_to;
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
        firebaseService = FirebaseService.getInstance(getApplicationContext());
        firebaseService = FirebaseService.getInstance(getApplicationContext());
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

        spn_balances_to.setEnabled(transaction.getCategory().getType().equals(TransactionType.TRANSFER));
    }

    private void initComponents() {
        rg_type = findViewById(R.id.add_transaction_type);
        tiet_details = findViewById(R.id.add_transaction_details);
        tiet_amount = findViewById(R.id.add_transaction_amount);
        spn_category = findViewById(R.id.add_transaction_category);
        spn_balances_from = findViewById(R.id.add_transaction_balance_from);
        spn_balances_to = findViewById(R.id.add_transaction_balance_to);
        btn_date = findViewById(R.id.add_transaction_date);
        btn_save = findViewById(R.id.add_transaction_save);

        rg_type.setOnCheckedChangeListener(changeTypeEventListener());
        btn_date.setOnClickListener(dateDialogEventListener());
        btn_save.setOnClickListener(saveTransactionEventListener());
    }

    private RadioGroup.OnCheckedChangeListener changeTypeEventListener() {
        return (group, checkedId) -> {
            switch (checkedId) {
                case R.id.add_transaction_type_expense:
                    transaction.getCategory().setType(TransactionType.EXPENSE);
                    setCategoryAdapter();
                    break;
                case R.id.add_transaction_type_income:
                    transaction.getCategory().setType(TransactionType.INCOME);
                    setCategoryAdapter();
                    break;
                default:
                    transaction.getCategory().setType(TransactionType.TRANSFER);
                    break;
            }

            spn_category.setEnabled(checkedId != R.id.add_transaction_type_transfer);
            spn_balances_to.setEnabled(checkedId == R.id.add_transaction_type_transfer);
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
            buildTransaction();

            if (rg_type.getCheckedRadioButtonId() == R.id.add_transaction_type_expense) {
                Expense expense = new Expense(transaction);
                if (InputValidation.expenseValidation(expense)) {
                    saveExpense(expense);
                    close();
                }

            } else if (rg_type.getCheckedRadioButtonId() == R.id.add_transaction_type_income) {
                Income income = new Income(transaction);
                if (InputValidation.incomeValidation(income)) {
                    saveIncome(income);
                    close();
                }

            } else {
                Transfer transfer = buildTransfer(transaction);
                if (!InputValidation.transferValidation(transfer)) {
                    Toast.makeText(getApplicationContext(),
                            "Invalid transfer.",
                            Toast.LENGTH_LONG).show();
                } else {
                    saveTransfer(transfer);
                    close();
                }
            }
        };

    }


    private void saveExpense(Expense expense) {
        expense.getBalance_from().withdraw(expense.getAmount());
        Log.e("expense", expense.toString());
        firebaseService.upsert(expense);
        firebaseService.upsert(expense.getBalance_from());
    }

    private void saveIncome(Income income) {
        income.getBalance_from().deposit(income.getAmount());
        firebaseService.upsert(income);
        firebaseService.upsert(income.getBalance_from());
    }

    private void saveTransfer(Transfer transfer) {
        transfer.getBalance_from().withdraw(transfer.getAmount());
        transfer.getBalance_to().deposit(transfer.getAmount());
        firebaseService.upsert(transfer);
        firebaseService.upsert(transfer.getBalance_from());
        firebaseService.upsert(transfer.getBalance_to());
    }

    private void buildTransaction() {
        if (!tiet_details.getText().toString().trim().isEmpty()) {
            transaction.setDetails(tiet_details.getText().toString().trim());
        }

        if (InputValidation.amountValidation(tiet_amount)) {
            double amount = Double.parseDouble(tiet_amount.getText().toString());
            transaction.setAmount(amount);
        }

        Balance balance = (Balance) spn_balances_from.getSelectedItem();
        transaction.setBalance_from(balance);

        if (rg_type.getCheckedRadioButtonId() != R.id.add_transaction_type_transfer) {
            Category category = (Category) spn_category.getSelectedItem();
            transaction.setCategory(category);
        }
    }

    private Transfer buildTransfer(Transaction transaction) {
        Transfer transfer = new Transfer();
        transfer.setDate(transaction.getDate());
        transfer.setDetails(transaction.getDetails());
        transfer.setAmount(transaction.getAmount());
        transfer.setBalance_from(transaction.getBalance_from());

        Balance balance_to = (Balance) spn_balances_to.getSelectedItem();
        transfer.setBalance_to(balance_to);

        return transfer;
    }

    private void close() {
        Intent intent = getIntent();
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
    }

    private void setBalanceAdapter() {
        ArrayAdapter<Balance> adapter =
                new ArrayAdapter<>
                        (getApplicationContext(),
                                android.R.layout.simple_spinner_item,
                                balances);
        spn_balances_from.setAdapter(adapter);
        spn_balances_to.setAdapter(adapter);
        if (balances.size() > 0) {
            transaction.setBalance_from(balances.get(0));
        }
    }

    private List<Category> getCategoriesByType() {
        if (rg_type.getCheckedRadioButtonId() == R.id.add_transaction_type_expense) {
            return expenseCategories;
        } else {
            return incomeCategories;
        }
    }
}