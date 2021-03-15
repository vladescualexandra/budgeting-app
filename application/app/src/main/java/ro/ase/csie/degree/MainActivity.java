package ro.ase.csie.degree;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
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


import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String NEW_TRANSACTION = "new_transaction";
    public static final int REQUEST_CODE_ADD_TRANSACTION = 201;

    private ImageButton btn_settings;
    private ImageButton btn_add;
    private TabLayout tabLayout;
    private ListView lv_transactions;

    private List<Transaction> transactionList = new ArrayList<>();

    private FirebaseService<Transaction> firebaseService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        initComponents();
        getTransactionsFromFirebase();
        show(new DayFragment());

    }

    private void initComponents() {
        btn_add = findViewById(R.id.main_add_transaction);
        btn_settings = findViewById(R.id.main_settings);

        btn_add.setOnClickListener(addEventListener());
        btn_settings.setOnClickListener(settingsEventListener());

        tabLayout = findViewById(R.id.main_tabs);
        tabLayout.addOnTabSelectedListener(changeTabEventListener());

        lv_transactions = findViewById(R.id.main_list_transactions);
        setAdapter();

        String USER_KEY = User.getUID(getApplicationContext());
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
                    case 1:
                        fragment = new MonthFragment();
                        break;
                    case 2:
                        fragment = new YearFragment();
                        break;
                    case 3:
                        fragment = new TotalFragment();
                        break;
                    default:
                        fragment = new DayFragment();
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