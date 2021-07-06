package ro.ase.csie.degree.settings.target;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ro.ase.csie.degree.model.Balance;
import ro.ase.csie.degree.model.Transaction;
import ro.ase.csie.degree.model.TransactionType;

public class Target {

    private static final String PREFS_TARGET = "target";
    private static final String PREFS_TARGET_SPENDINGS = "spendings";

    public static final float DEFAULT_SPENDINGS = 80;
    public static final float DEFAULT_SAVINGS = 100 - DEFAULT_SPENDINGS;

    public static float TARGET_SPENDINGS = -1;
    public static float TARGET_SAVINGS = -1;

    private Context context;
    private List<Transaction> transactionList;
    private List<Balance> balancesList;
    private Map<String, Float> targetMap = new HashMap<>();
    private Map<String, Float> actualMap = new HashMap<>();


    private float target_spendings;
    private float spendings;
    private float savings;
    private float current_balance;
    private float initial_balance;
    private float spendings_percent;


    public Target(Context context, List<Transaction> transactionList, List<Balance> balanceList) {
        this.context = context;
        this.transactionList = transactionList;
        this.balancesList = balanceList;
        if (getUserTargetSpendings() != -1) {
            TARGET_SPENDINGS = getUserTargetSpendings();
            TARGET_SAVINGS = 100 - TARGET_SPENDINGS;
        } else {
            TARGET_SPENDINGS = DEFAULT_SPENDINGS;
            TARGET_SAVINGS = DEFAULT_SAVINGS;
        }
        calcs();
    }

    private void calcs() {
        this.current_balance = getBalancesValue();
        this.initial_balance = getSpendingsValue() + getBalancesValue();

        this.savings = this.current_balance;
        this.spendings = getSpendingsValue();
        this.spendings_percent = (100 * this.spendings) / this.initial_balance;
        this.target_spendings = (TARGET_SPENDINGS / 100) * this.initial_balance;
    }

    public static void changeTarget(int value) {
        TARGET_SPENDINGS = value;
        TARGET_SAVINGS = 100 - TARGET_SPENDINGS;
    }

    private void persist() {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_TARGET, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putFloat(PREFS_TARGET_SPENDINGS, TARGET_SPENDINGS);
        editor.apply();
    }

    private int getUserTargetSpendings() {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_TARGET, Context.MODE_PRIVATE);
        return prefs.getInt(PREFS_TARGET_SPENDINGS, -1);
    }

    public Map<String, Float> buildTargetMap() {
        this.targetMap.put(Targets.SPENDINGS.toString(), TARGET_SPENDINGS);
        this.targetMap.put(Targets.SAVINGS.toString(), TARGET_SAVINGS);
        return this.targetMap;
    }

    public Map<String, Float> buildActualMap() {
        this.actualMap.put(Targets.SPENDINGS.toString(), spendings);
        this.actualMap.put(Targets.SAVINGS.toString(), savings);
        return this.actualMap;
    }

    public float getSpendingsValue() {
        float spendings = 0.0f;

        for (Transaction transaction : transactionList) {
            if (transaction.getCategory().getType().equals(TransactionType.EXPENSE)) {
                spendings += transaction.getAmount();
            }
        }
        return spendings;
    }

    public float getBalancesValue() {
        float amount = 0.0f;
        for (Balance balance : balancesList) {
            amount += balance.getAvailable_amount();
        }
        return amount;
    }

    public float getPercentage() {
        return getSpendingsValue() / (getBalancesValue() + getSpendingsValue());
    }

    public float getActualTargetSpendings() {
        return (TARGET_SPENDINGS / 100) * (getBalancesValue() + getSpendingsValue());
    }

    public void update() {
        calcs();
        buildActualMap();
    }

    public float getTargetSpendings() {
        return target_spendings;
    }

    public float getSpendings() {
        return spendings;
    }

    public float getSavings() {
        return savings;
    }

    public float getCurrentBalance() {
        return current_balance;
    }

    public float getInitialBalance() {
        return initial_balance;
    }

    public float getSpendingsPercent() {
        return spendings_percent;
    }
}
