package ro.ase.csie.degree.settings.balances;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ro.ase.csie.degree.R;
import ro.ase.csie.degree.firebase.Callback;
import ro.ase.csie.degree.firebase.FirebaseService;
import ro.ase.csie.degree.firebase.Table;
import ro.ase.csie.degree.model.Account;
import ro.ase.csie.degree.model.Balance;
import ro.ase.csie.degree.adapters.BalanceAdapter;
import ro.ase.csie.degree.model.Currency;

public class BalancesActivity extends AppCompatActivity {


    public static final int REQUEST_CODE_ADD_BALANCE = 201;
    private TextView tv_total_text;
    private ImageButton iv_balance_add;
    private ListView lv_balances;

    private Currency currency;
    private List<Balance> balanceList = new ArrayList<>();
    private FirebaseService<Balance> firebaseService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balances);

        initComponents();
        getBalancesFromFirebase();
    }

    private void getBalancesFromFirebase() {
        firebaseService = FirebaseService.getInstance(getApplicationContext());
        firebaseService.updateBalancesUI(updateBalancesCallback());
    }

    private Callback<List<Balance>> updateBalancesCallback() {
        return result -> {
            if (result != null) {
                balanceList.clear();
                balanceList.addAll(result);
                notifyAdapter();
            }
        };
    }


    private void initComponents() {
        tv_total_text = findViewById(R.id.balances_total);
        iv_balance_add = findViewById(R.id.balances_add);

        lv_balances = findViewById(R.id.balances_list);
        setAdapter();
        lv_balances.setOnItemLongClickListener(deleteBalanceEventListener());

        iv_balance_add.setOnClickListener(addBalanceEventListener());
    }

    private AdapterView.OnItemLongClickListener deleteBalanceEventListener() {
        return (parent, view, position, id) -> {
            firebaseService.delete(balanceList.get(position));
            notifyAdapter();
            return true;
        };
    }

    private View.OnClickListener addBalanceEventListener() {
        return v -> {
            Intent intent = new Intent(getApplicationContext(), AddBalanceActivity.class);
            startActivityForResult(intent, REQUEST_CODE_ADD_BALANCE);
        };
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_ADD_BALANCE & data != null) {
            Balance balance = (Balance) data.getSerializableExtra(AddBalanceActivity.NEW_BALANCE);
            firebaseService.upsert(balance);
        }
    }

    private void setAdapter() {
        BalanceAdapter adapter = new BalanceAdapter(getApplicationContext(),
                R.layout.row_item_balance,
                balanceList,
                getLayoutInflater());
        lv_balances.setAdapter(adapter);
    }

    private void notifyAdapter() {
        BalanceAdapter adapter = (BalanceAdapter) lv_balances.getAdapter();
        adapter.notifyDataSetChanged();
        setTotal(tv_total_text, getTotalAmount());
    }

    private double getTotalAmount() {
        double total = 0.0;
        for (Balance balance : balanceList) {
            total += balance.getAvailable_amount();
        }
        return total;
    }

    private void setTotal(TextView tv_total_text, double totalAmount) {
        tv_total_text.setText("Total balance: " + String.format("%.2f", totalAmount));
    }
}