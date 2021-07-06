package ro.ase.csie.degree.settings.target;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import ro.ase.csie.degree.R;
import ro.ase.csie.degree.async.Callback;
import ro.ase.csie.degree.firebase.DateDisplayType;
import ro.ase.csie.degree.firebase.services.BalanceService;
import ro.ase.csie.degree.firebase.services.TransactionService;
import ro.ase.csie.degree.model.Balance;
import ro.ase.csie.degree.model.Transaction;
import ro.ase.csie.degree.util.Streak;

public class TargetActivity extends AppCompatActivity {

    /*
     * TARGET
     * * Period of time: Current month (edit maybe?)
     * * MAX SPENDINGS: 80%          GRAFIC DEMONSTRATIV
     * * SAVINGS: 20%
     * ********* edit
     *
     * The actual case:
     * Spendings: x %
     *
     * You spent x.xx out of y.yy.
     * You have z.zz to spend before reaching your targeted limit.
     *
     *   GRAFIC DEMONSTRATIV
     *
     * */


    TextView tv_target_spendings;
    TextView tv_target_savings;

    TextView tv_actual_spendings_percentage;
    TextView tv_actual_spendings;

    Target target;
    TransactionService transactionService;
    BalanceService balanceService;

    private List<Transaction> transactionList = new ArrayList<>();
    private List<Balance> balanceList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_target);

        initComponents();
        setTarget();
    }

    private void initComponents() {
        tv_target_spendings = findViewById(R.id.target_target_spendings);
        tv_target_savings = findViewById(R.id.target_target_savings);
        tv_actual_spendings_percentage = findViewById(R.id.target_actual_spendings_percentage);
        tv_actual_spendings = findViewById(R.id.target_actual_spendings);
    }

    private void setTarget() {
        Date currentDate = Calendar.getInstance().getTime();

        transactionService = new TransactionService();
        transactionService.updateTransactionsUI(getAllTransactionsCallback(), DateDisplayType.MONTH_YEAR, currentDate);

        balanceService = new BalanceService();
        balanceService.updateBalancesUI(getAllBalancesCallback());

        target = new Target(getApplicationContext(), transactionList, balanceList);
        updateTarget();
    }

    private void updateTarget() {
        target.update();

        tv_target_spendings.setText("SPENDINGS: " + Target.TARGET_SPENDINGS  + " %");
        tv_target_savings.setText("SAVINGS: " + Target.TARGET_SAVINGS + "%");
        tv_actual_spendings_percentage.setText("Spendings: " + target.getSpendingsPercent() + "%");
        tv_actual_spendings.setText("You spent " + target.getSpendings() + " out of " + target.getTargetSpendings());

        setTargetChart();
        setActualChart();
    }

    private Callback<List<Transaction>> getAllTransactionsCallback() {
        return result -> {
            if (result != null) {
                transactionList.clear();
                transactionList.addAll(result);
                updateTarget();
            }
        };
    }

    private Callback<List<Balance>> getAllBalancesCallback() {
        return result -> {
            if (result != null) {
                balanceList.clear();
                balanceList.addAll(result);
                updateTarget();
            }
        };
    }


    private void setTargetChart() {
        Fragment fragment = new TargetChartFragment(target.buildTargetMap());
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager
                .beginTransaction()
                .replace(R.id.target_frame_chart_target, fragment)
                .commitAllowingStateLoss();
    }

    private void setActualChart() {
        Fragment fragment = new ActualChartFragment(target.buildActualMap());
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager
                .beginTransaction()
                .replace(R.id.target_frame_chart_actual, fragment)
                .commitAllowingStateLoss();
    }


}