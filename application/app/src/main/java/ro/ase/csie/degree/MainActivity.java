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
import ro.ase.csie.degree.fragments.ChartType;
import ro.ase.csie.degree.fragments.PieChartFragment;
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
    private ImageButton ib_chart_pie;
    private ImageButton ib_chart_bar;
    private ImageButton ib_chart_line;


    private TabLayout tabLayout;
    private ListView lv_transactions;

    private ArrayList<Transaction> transactionList = new ArrayList<>();

    private FirebaseService firebaseService;

    private int day, month, year;

    private Fragment fragment = new PieChartFragment();
    private DateDisplayType dateDisplayType = DateDisplayType.DAY_MONTH_YEAR;
    private ChartType selectedChartType = ChartType.PIE_CHART;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initComponents();
        setDefaultDate();
        filterTransactions();
    }

    private void setDefaultDate() {
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
    }

    private void initComponents() {
        ib_refresh = findViewById(R.id.main_refresh);
        ib_refresh.setOnClickListener(v -> setDefaultDate());

        tv_date_filter = findViewById(R.id.main_date_filter);
        tv_date_filter.setOnClickListener(filterEventListener());

        btn_add = findViewById(R.id.main_add_transaction);
        btn_settings = findViewById(R.id.main_settings);

        btn_add.setOnClickListener(addEventListener());
        btn_settings.setOnClickListener(settingsEventListener());

        tabLayout = findViewById(R.id.main_tabs);
        tabLayout.addOnTabSelectedListener(changeTabEventListener());


        ib_chart_pie = findViewById(R.id.main_chart_pie);
        ib_chart_bar = findViewById(R.id.main_chart_bar);
        ib_chart_line = findViewById(R.id.main_chart_line);

        ib_chart_pie.setOnClickListener(changeChart(ChartType.PIE_CHART));
        ib_chart_bar.setOnClickListener(changeChart(ChartType.BAR_CHART));
        ib_chart_line.setOnClickListener(changeChart(ChartType.LINE_CHART));

        lv_transactions = findViewById(R.id.main_list_transactions);
        setAdapter();
    }

    private TabLayout.OnTabSelectedListener changeTabEventListener() {
        return new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                displaySelectedFragment(tab);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        };
    }

    private void displaySelectedFragment(TabLayout.Tab tab) {
        dateDisplayType = DateDisplayType.getDateDisplayType(tab.getPosition());
        filterTransactions();
//        getTransactionsFromFirebase();
    }


    private View.OnClickListener changeChart(ChartType chartType) {
        return v -> {
            selectedChartType = chartType;
            show();
        };
    }


    private View.OnClickListener filterEventListener() {
        return v -> {
            dateDisplayType = DateDisplayType.getDateDisplayType(tabLayout.getSelectedTabPosition());
            buildDatePicker();
        };
    }

    private void buildDatePicker() {
        switch (dateDisplayType) {
            case MONTH_YEAR:
            case YEAR:
                buildMonthYearPicker(dateDisplayType);
                break;
            case TOTAL:
                break;
            default:
                buildDayMonthYearPicker();
                break;

        }
    }

    private void buildDayMonthYearPicker() {
        DatePickerDialog datePicker = new DatePickerDialog(
                MainActivity.this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    day = selectedDay;
                    month = selectedMonth;
                    year = selectedYear;
                    filterTransactions();
                },
                year, month, day);
        datePicker.show();
    }

    private void buildMonthYearPicker(DateDisplayType type) {
        MonthPickerDialog.Builder picker = new MonthPickerDialog.Builder(MainActivity.this,
                (selectedMonth, selectedYear) -> {
                    month = selectedMonth;
                    year = selectedYear;
                    filterTransactions();
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


    private void getTransactionsFromFirebase() {
        Date date = DateConverter.toDate(day, month, year);
        firebaseService = FirebaseService.getInstance(getApplicationContext());
        firebaseService.updateTransactionsUI(updateTransactionsCallback(), dateDisplayType, date);
    }

    private Callback<List<Transaction>> updateTransactionsCallback() {
        return result -> {
            if (result != null) {
                transactionList.clear();
                transactionList.addAll(result);
                notifyAdapter();
                show();
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
            getTransactionsFromFirebase();
        }
    }


    private void filterTransactions() {
        setFilterText();
        getTransactionsFromFirebase();
    }

    private void setFilterText() {
        tv_date_filter.setText(DateDisplayType.display(dateDisplayType, day, month, year));
    }


    private void show() {
        this.fragment = ChartType.getFragment(selectedChartType, transactionList);

//        Bundle args = new Bundle();
//        args.putParcelableArrayList(ChartFragment.TRANSACTIONS, transactionList);
//        this.fragment.setArguments(args);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager
                .beginTransaction()
                .replace(R.id.main_fragment, this.fragment)
                .commitAllowingStateLoss();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }

}