package ro.ase.csie.degree.model;

import ro.ase.csie.degree.firebase.FirebaseService;

public class Expense extends Transaction{

    public Expense(Transaction transaction) {
        super(transaction);
    }

    public static void saveExpense(FirebaseService firebaseService, Expense expense) {
        expense.getBalance_from().withdraw(expense.getAmount());
        firebaseService.upsert(expense);
        firebaseService.upsert(expense.getBalance_from());
    }
}
