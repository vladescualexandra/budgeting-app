package ro.ase.csie.degree;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.text.DateFormat;
import android.icu.text.DateFormatSymbols;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import ro.ase.csie.degree.adapters.TransactionAdapter;
import ro.ase.csie.degree.authentication.user.User;
import ro.ase.csie.degree.firebase.Callback;
import ro.ase.csie.degree.firebase.FirebaseService;
import ro.ase.csie.degree.firebase.Table;
import ro.ase.csie.degree.fragments.DayFragment;
import ro.ase.csie.degree.fragments.MonthFragment;
import ro.ase.csie.degree.fragments.TotalFragment;
import ro.ase.csie.degree.fragments.YearFragment;
import ro.ase.csie.degree.model.Balance;
import ro.ase.csie.degree.model.Transaction;
import ro.ase.csie.degree.settings.SettingsActivity;
import ro.ase.csie.degree.util.DateConverter;


import com.google.android.material.tabs.TabLayout;
import com.whiteelephant.monthpicker.MonthPickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String NEW_TRANSACTION = "new_transaction";
    public static final int REQUEST_CODE_ADD_TRANSACTION = 201;

    private TextView tv_date_filter;
    private ImageButton btn_settings;
    private ImageButton btn_add;
    private TabLayout tabLayout;
    private ListView lv_transactions;

    private List<Transaction> transactionList = new ArrayList<>();

    private FirebaseService firebaseService;

    private int day, month, year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        initComponents();
        getTransactionsFromFirebase();
        show(new DayFragment());

        setDefaultDate();

    }

    private void setDefaultDate() {
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        filter(0);
    }

    private void initComponents() {
        tv_date_filter = findViewById(R.id.main_date_filter);
        tv_date_filter.setOnClickListener(filterEventListener());

        btn_add = findViewById(R.id.main_add_transaction);
        btn_settings = findViewById(R.id.main_settings);

        btn_add.setOnClickListener(addEventListener());
        btn_settings.setOnClickListener(settingsEventListener());

        tabLayout = findViewById(R.id.main_tabs);
        tabLayout.addOnTabSelectedListener(changeTabEventListener());

        lv_transactions = findViewById(R.id.main_list_transactions);
        setAdapter();
    }

    private View.OnClickListener filterEventListener() {
        return v -> {
            switch (tabLayout.getSelectedTabPosition()) {
                case 0:
                    buildDayMonthYearPicker();
                    break;
                case 1:
                    buildMonthYearPicker(1);
                    break;
                case 2:
                    buildMonthYearPicker(2);
                    break;
                default:
                    filter(3);
                    break;
            }
        };
    }

    private void buildDayMonthYearPicker() {
        DatePickerDialog datePicker = new DatePickerDialog(
                MainActivity.this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    day = selectedDay;
                    month = selectedMonth;
                    year = selectedYear;
                    filter(0);
                },
                year, month, day);
        datePicker.show();
    }

    private void buildMonthYearPicker(int option) {
        MonthPickerDialog.Builder picker = new MonthPickerDialog.Builder(MainActivity.this,
                (selectedMonth, selectedYear) -> {
                    month = selectedMonth;
                    year = selectedYear;
                    filter(option);
                }
                , year, month);


        picker.setActivatedMonth(month)
                .setMinYear(1990)
                .setActivatedYear(year)
                .setMaxYear(2099);

        if (option == 2) {
            picker.showYearOnly();
        }

        picker.build()
                .show();
    }

    private DatePickerDialog.OnDateSetListener x() {
        return (view, year, month, dayOfMonth) -> {
            Date date = (DateConverter.toDate(dayOfMonth, month, year));
            tv_date_filter.setText(DateConverter.toString(date));
        };
    }

    private void getTransactionsFromFirebase() {
        firebaseService = FirebaseService.getInstance(getApplicationContext(), Table.BUDGET);
        firebaseService.updateTransactionsUI(updateTransactionsCallback());
    }

    private Callback<List<Transaction>> updateTransactionsCallback() {
        return result -> {
            if (result != null) {
                transactionList.clear();
                transactionList.addAll(result);
                notifyAdapter();
            }
        };
    }

    private View.OnClickListener addEventListener() {
        return v -> {
            Intent intent = new Intent(getApplicationContext(), AddTransactionActivity.class);
            startActivityForResult(intent, REQUEST_CODE_ADD_TRANSACTION);
        };
    }

    private View.OnClickListener settingsEventListener() {
        return v -> {
            Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
            startActivity(intent);
        };
    }

    private void setAdapter() {
        TransactionAdapter adapter = new TransactionAdapter
                (getApplicationContext(),
                        R.layout.row_item_transaction,
                        transactionList,
                        getLayoutInflater());
        lv_transactions.setAdapter(adapter);
    }

    private void notifyAdapter() {
        TransactionAdapter adapter = (TransactionAdapter) lv_transactions.getAdapter();
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_ADD_TRANSACTION && resultCode == RESULT_OK && data != null) {
            Transaction transaction = (Transaction) data.getParcelableExtra(MainActivity.NEW_TRANSACTION);
            if (transaction.getBalance().operation(transaction.getCategory().getType(), transaction.getAmount())) {
                updateBalance(transaction);
                firebaseService.upsert(transaction);
            }

        }
    }

    private void updateBalance(Transaction transaction) {
        Balance balance = transaction.getBalance();
        FirebaseService<Balance> balanceFirebaseService = FirebaseService.getInstance(getApplicationContext(), Table.BUDGET);
        balanceFirebaseService.upsert(balance);
    }

    private TabLayout.OnTabSelectedListener changeTabEventListener() {
        return new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Fragment fragment;
                switch (tab.getPosition()) {
                    case 0:
                        fragment = new DayFragment();
                        break;
                    case 1:
                        fragment = new MonthFragment();
                        break;
                    case 2:
                        fragment = new YearFragment();
                        break;
                    default:
                        fragment = new TotalFragment();
                        tv_date_filter.setText("");
                        break;

                }
                filter(tab.getPosition());
                show(fragment);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        };
    }

    private void filter(int position) {
        switch (position) {
            case 0:
                //TODO day filter
                tv_date_filter.setText(DateConverter.toDisplayDate(day, month, year));
                break;
            case 1:
                //TODO month filter
                tv_date_filter.setText(DateConverter.toMonthYear(month, year));
                break;
            case 2:
                //TODO year filter
                tv_date_filter.setText(DateConverter.toYear(year));
                break;
            default:
                //TODO total filter
                tv_date_filter.setText("");
                break;
        }
    }

    private void show(Fragment fragment) {
        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.main_fragment, fragment)
                    .addToBackStack(null)
                    .commit();
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}