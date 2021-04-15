package ro.ase.csie.degree.util;

import android.content.Context;
import android.util.Log;
import android.util.Patterns;

import ro.ase.csie.degree.R;
import ro.ase.csie.degree.model.Transaction;

import com.google.android.material.textfield.TextInputEditText;

import java.util.regex.Pattern;


public class InputValidation {

    public static int NUMBER_OF_MINIMUM_CHARACTERS_PASSWORD = 6;

    public static boolean nameValidation(Context context, TextInputEditText tiet) {
        if (tiet.getText().toString().trim().length() < 3) {
            tiet.setError(context.getString(R.string.error_invalid_name));
            return false;
        } else {
            tiet.setError(null);
            return true;
        }
    }

    private static boolean isValidEmail(String email) {
        if (email == null) {
            return false;
        }

        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }

    public static boolean emailValidation(Context context, TextInputEditText tiet) {
        if (Patterns.EMAIL_ADDRESS.matcher(tiet.getText().toString().trim()).matches()
                && isValidEmail(tiet.getText().toString().trim())) {
            tiet.setError(null);
            return true;
        } else {
            tiet.setError(context.getString(R.string.error_invalid_email));
            return false;
        }

    }

    public static boolean passwordValidation(Context context, TextInputEditText tiet) {
        if (tiet.getText().toString().trim().length() < NUMBER_OF_MINIMUM_CHARACTERS_PASSWORD) {
            tiet.setError(context.getString(R.string.error_invalid_password));
            return false;
        } else {
            tiet.setError(null);
            return true;
        }
    }

    public static boolean loginValidation(Context context, TextInputEditText email, TextInputEditText password) {
        return emailValidation(context, email) && passwordValidation(context, password);
    }


    public static boolean registerValidation(Context context, TextInputEditText name, TextInputEditText email, TextInputEditText password) {
        return nameValidation(context, name) && emailValidation(context, email) && passwordValidation(context, password);
    }

    public static boolean availableAmountValidation(Context context, TextInputEditText available_amount) {
        if (Double.parseDouble(available_amount.getText().toString()) >= 0.0) {
            available_amount.setError(null);
            return true;
        } else {
            available_amount.setError(context.getString(R.string.invalid_available_amount));
            return false;
        }

    }

    public static boolean expenseValidation(Transaction expense) {
        expense.setBalance_to(null);
        return expense.getAmount() > 0 && expense.getBalance_from().getAvailable_amount() >= expense.getAmount();
    }

    public static boolean incomeValidation(Transaction income) {
        income.setBalance_from(null);
        return income.getAmount() > 0;
    }

    public static boolean transferValidation(Transaction transfer) {
        return transfer.getBalance_from() != null
                && transfer.getBalance_to() != null
                && !transfer.getBalance_from().getId().equals(transfer.getBalance_to().getId())
                && transfer.getAmount() > 0
                && transfer.getBalance_from().getAvailable_amount() >= transfer.getAmount();
    }

    public static boolean amountValidation(TextInputEditText tiet_amount) {
        if (tiet_amount.getText().toString() == null
                || tiet_amount.getText().toString().trim().isEmpty()) {
            tiet_amount.setError("Insert an amount greater than 0.");
            return false;
        }
        tiet_amount.setError(null);
        return true;
    }

}
