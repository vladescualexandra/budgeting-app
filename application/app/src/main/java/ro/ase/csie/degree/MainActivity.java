package ro.ase.csie.degree;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import ro.ase.csie.degree.adapters.TransactionAdapter;
import ro.ase.csie.degree.async.Callback;
import ro.ase.csie.degree.firebase.DateDisplayType;
import ro.ase.csie.degree.firebase.FirebaseService;
import ro.ase.csie.degree.fragments.PieChartFragment;
import ro.ase.csie.degree.fragments.MonthFragment;
import ro.ase.csie.degree.fragments.TotalFragment;
import ro.ase.csie.degree.fragments.YearFragment;
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

    public static final int REQUEST_CODE_ADD_TRANSACTION = 201;

    private ImageButton ib_refresh;
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
        setDefaultDate();
        getTransactionsFromFirebase(DateDisplayType.DAY_MONTH_YEAR);
        show(new PieChartFragment());
    }

    private void setDefaultDate() {
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        filter(DateDisplayType.DAY_MONTH_YEAR);
    }

    private void initComponents() {
        ib_refresh = findViewById(R.id.main_refresh);
        ib_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDefaultDate();
            }
        });

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
                    buildMonthYearPicker(DateDisplayType.MONTH_YEAR);
                    break;
                case 2:
                    buildMonthYearPicker(DateDisplayType.YEAR);
                    break;
                default:
                    filter(DateDisplayType.TOTAL);
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
                    filter(DateDisplayType.DAY_MONTH_YEAR);
                },
                year, month, day);
        datePicker.show();
    }

    private void buildMonthYearPicker(DateDisplayType type) {
        MonthPickerDialog.Builder picker = new MonthPickerDialog.Builder(MainActivity.this,
                (selectedMonth, selectedYear) -> {
                    month = selectedMonth;
                    year = selectedYear;
                    filter(type);
                }
                , year, month);


        picker.setActivatedMonth(month)
                .setMinYear(1990)
                .setActivatedYear(year)
                .setMaxYear(2099);

        if (type.equals(DateDisplayType.YEAR)) {
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

    private void getTransactionsFromFirebase(DateDisplayType type) {
        Date date = DateConverter.toDate(day, month, year);
        firebaseService = FirebaseService.getInstance(getApplicationContext());
        firebaseService.updateTransactionsUI(updateTransactionsCallback(), type, date);
    }

    private Callback<List<Transaction>> updateTransactionsCallback() {
        return result -> {
            if (result != null) {
                transactionList.clear();
                transactionList.addAll(result);
                notifyAdapter();
                show(new PieChartFragment(transactionList));
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
            getTransactionsFromFirebase(DateDisplayType.DAY_MONTH_YEAR);
        }
    }

    private TabLayout.OnTabSelectedListener changeTabEventListener() {
        return new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Fragment fragment;
                switch (tab.getPosition()) {
                    case 0:
                        fragment = new PieChartFragment();
                        filter(DateDisplayType.DAY_MONTH_YEAR);
                        break;
                    case 1:
                        fragment = new MonthFragment();
                        filter(DateDisplayType.MONTH_YEAR);
                        break;
                    case 2:
                        fragment = new YearFragment();
                        filter(DateDisplayType.YEAR);
                        break;
                    default:
                        fragment = new TotalFragment();
                        filter(DateDisplayType.TOTAL);
                        tv_date_filter.setText("");
                        break;

                }
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

    private void filter(DateDisplayType type) {
        switch (type) {
            case DAY_MONTH_YEAR:
                tv_date_filter.setText(DateConverter.toDisplayDate(day, month, year));
                break;
            case MONTH_YEAR:
                tv_date_filter.setText(DateConverter.toMonthYear(month, year));
                break;
            case YEAR:
                tv_date_filter.setText(DateConverter.toYear(year));
                break;
            default:
                tv_date_filter.setText("");
                break;
        }
        getTransactionsFromFirebase(type);
    }

    private void show(Fragment fragment) {
        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.main_fragment, fragment)
                    .commitAllowingStateLoss();
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }

}