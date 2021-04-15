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

    public static int MINIMUM_NUMBER_OF_CHARACTERS_NAME = 3;
    public static int MAXIMUM_NUMBER_OF_CHARACTERS_NAME = 12;

    public static String REGEX_ONLY_LETTERS = "[a-zA-Z]+";

    public static double MINIMUM_BALANCE = 0.0;
    public static double MAXIMUM_BALANCE = 1_000_000.0;


    public static boolean nameValidation(Context context, TextInputEditText tiet) {
        if (tiet.getText() == null) {
            throw new NullPointerException();
        }
        String name = tiet.getText().toString().trim();

        if (name.length() < MINIMUM_NUMBER_OF_CHARACTERS_NAME || name.length() > MAXIMUM_NUMBER_OF_CHARACTERS_NAME) {
            tiet.setError(context.getString(R.string.error_invalid_name_length));
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

        String text = tiet.getText().toString();
        if (text.isEmpty()) {
            text = "0";
        }

        double amount = Double.parseDouble(text);
        if (amount > MINIMUM_BALANCE && amount < MAXIMUM_BALANCE) {
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

    public static boolean amountValidation(Context context, TextInputEditText tiet) {
        if (tiet.getText() == null) {
            throw new NullPointerException();
        }

        double amount = MINIMUM_BALANCE;

        String text = tiet.getText().toString().trim();
        if (!text.isEmpty()) {
            amount = Double.parseDouble(text);
        }

        if (amount > MINIMUM_BALANCE && amount < MAXIMUM_BALANCE) {
            tiet.setError(null);
            return true;
        } else {
            tiet.setError(context.getString(R.string.invalid_available_amount));
            return false;
        }
    }


}
