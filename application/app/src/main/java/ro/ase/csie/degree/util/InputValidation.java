package ro.ase.csie.degree.util;

import android.content.Context;
import android.util.Log;
import android.util.Patterns;

import androidx.annotation.NonNull;

import ro.ase.csie.degree.R;
import ro.ase.csie.degree.model.Transaction;

import com.google.android.material.textfield.TextInputEditText;

import java.util.regex.Pattern;


public class InputValidation {

    public static int NUMBER_OF_MINIMUM_CHARACTERS_PASSWORD = 6;

    public static String REGEX_ONLY_LETTERS = "[a-zA-Z]+";

    public static boolean nameValidation(Context context, TextInputEditText tiet) {
        if (tiet.getText() == null) {
            throw new NullPointerException();
        }
        String name = tiet.getText().toString().trim();

        if (name.length() < 3) {
            tiet.setError(context.getString(R.string.error_invalid_name_too_short));
            return false;
        } else if (!isMatching(REGEX_ONLY_LETTERS, name)) {
            tiet.setError(context.getString(R.string.error_invalid_name_not_letters));
            return false;
        } else {
            tiet.setError(null);
            return true;
        }
    }

    private static boolean isMatching(String regex, String string) {
        if (string == null) {
            return false;
        }
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(string).matches();
    }

    public static boolean emailValidation(Context context, TextInputEditText tiet) {
        if (tiet.getText() == null) {
            throw new NullPointerException();
        }
        String email = tiet.getText().toString().trim();
        if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            tiet.setError(null);
            return true;
        } else {
            tiet.setError(context.getString(R.string.error_invalid_email));
            return false;
        }

    }

    public static boolean passwordValidation(Context context, TextInputEditText tiet) {
        if (tiet.getText() == null) {
            throw new NullPointerException();
        }
        if (tiet.getText().toString().trim().length() >= NUMBER_OF_MINIMUM_CHARACTERS_PASSWORD) {
            tiet.setError(null);
            return true;
        } else {
            tiet.setError(context.getString(R.string.error_invalid_password));
            return false;
        }
    }

    public static boolean loginValidation(Context context, TextInputEditText email, TextInputEditText password) {
        return emailValidation(context, email) && passwordValidation(context, password);
    }


    public static boolean registerValidation(Context context, TextInputEditText name, TextInputEditText email, TextInputEditText password) {
        return nameValidation(context, name) && emailValidation(context, email) && passwordValidation(context, password);
    }

    public static boolean availableAmountValidation(Context context, TextInputEditText tiet) {
        if (tiet.getText() == null) {
            throw new NullPointerException();
        }
        if (Double.parseDouble(tiet.getText().toString()) >= 0.0) {
            tiet.setError(null);
            return true;
        } else {
            tiet.setError(context.getString(R.string.invalid_available_amount));
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

    public static boolean amountValidation(TextInputEditText tiet) {
        if (tiet.getText() == null) {
            throw new NullPointerException();
        }
        if (tiet.getText().toString() == null
                || tiet.getText().toString().trim().isEmpty()) {
            tiet.setError("Insert an amount greater than 0.");
            return false;
        }
        tiet.setError(null);
        return true;
    }

}
