package ro.ase.csie.degree.model;

import java.util.concurrent.Executor;

public class Expense extends Transaction{

    public Expense() {
        super();
        this.category.setType(TransactionType.EXPENSE);
    }


}
