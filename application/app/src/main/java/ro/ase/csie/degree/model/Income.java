package ro.ase.csie.degree.model;

import ro.ase.csie.degree.firebase.FirebaseService;

public class Income extends Transaction{

    public Income(Transaction transaction) {
        super(transaction);
    }


    public static void saveIncome(FirebaseService firebaseService, Income income) {
        income.getBalance_from().deposit(income.getAmount());
        firebaseService.upsert(income);
        firebaseService.upsert(income.getBalance_from());
    }

}
