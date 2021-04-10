package ro.ase.csie.degree.model;

import ro.ase.csie.degree.R;
import ro.ase.csie.degree.firebase.FirebaseService;
import ro.ase.csie.degree.firebase.services.BalanceService;
import ro.ase.csie.degree.firebase.services.TransactionService;

public class Transfer extends Transaction {

    public Transfer(Transaction transaction) {
        super(transaction);
        setTransferCategory();
    }

    private void setTransferCategory() {
        this.category = new Category();
        this.category.setType(TransactionType.TRANSFER);
        this.category.setName(TransactionType.TRANSFER.toString());
        this.category.setColor(R.color.rally_blue);
    }

    public static void saveTransfer(Transfer transfer) {
        transfer.getBalance_from().withdraw(transfer.getAmount());
        transfer.getBalance_to().deposit(transfer.getAmount());

        TransactionService transactionService = new TransactionService();
        transactionService.upsert(transfer);

        BalanceService balanceService = new BalanceService();
        balanceService.upsert(transfer.getBalance_from());
        balanceService.upsert(transfer.getBalance_to());
    }

    @Override
    public String toString() {
        return "Transfer{" +
                "balance_to=" + balance_to +
                ", details='" + details + '\'' +
                ", category=" + category +
                ", balance_from=" + balance_from +
                ", amount=" + amount +
                ", date=" + date +
                '}';
    }
}
