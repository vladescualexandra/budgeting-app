package ro.ase.csie.degree;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import ro.ase.csie.degree.model.Transaction;

public class TemplateActivity extends TransactionActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Toast.makeText(getApplicationContext(),
                "TEMPLATE ACTIVITY",
                Toast.LENGTH_LONG).show();

        btn_save.setText("Save as template");
    }


    @Override
    protected View.OnClickListener saveTransactionEventListener() {
        return v -> {
            Toast.makeText(getApplicationContext(),
                    "SAVE TEMPLATE EVENT LISTENER",
                    Toast.LENGTH_LONG).show();

            buildTransaction();
        };
    }

}
