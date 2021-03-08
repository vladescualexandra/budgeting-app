package ro.ase.csie.degree.settings.balances;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import ro.ase.csie.degree.R;

public class AddBalanceActivity extends AppCompatActivity {

    public static final String NEW_BALANCE = "new balance";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_balance);
    }
}