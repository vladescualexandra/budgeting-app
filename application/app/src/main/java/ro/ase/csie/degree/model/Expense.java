package ro.ase.csie.degree.model;

import ro.ase.csie.degree.firebase.FirebaseService;
import ro.ase.csie.degree.firebase.services.BalanceService;
import ro.ase.csie.degree.firebase.services.TransactionService;

public class Expense extends Transaction{

    public Expense(Transaction transaction) {
        super(transaction);
    }

    public static void saveExpense(Expense expense) {
        expense.getBalance_from().withdraw(expense.getAmount());

        TransactionService transactionService = new TransactionService();
        transactionService.upsert(expense);

        BalanceService balanceService = new BalanceService();
        balanceService.upsert(expense.getBalance_from());
    }
}
