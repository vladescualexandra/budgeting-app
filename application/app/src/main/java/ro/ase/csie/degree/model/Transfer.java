package ro.ase.csie.degree.model;

import ro.ase.csie.degree.R;
import ro.ase.csie.degree.firebase.services.BalanceService;
import ro.ase.csie.degree.firebase.services.TransactionService;

public class Transfer extends Transaction {

    public Transfer(Transaction transaction) {
        super(transaction);
        this.category = getTransferCategory();
    }

    public static Category getTransferCategory() {
        Category category = new Category();
        category.setType(TransactionType.TRANSFER);
        category.setName(TransactionType.TRANSFER.toString());
        category.setColor(R.color.rally_blue);
        return category;
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

    public static void deleteTransfer(Transfer transfer) {
        transfer.getBalance_from().deposit(transfer.getAmount());
        transfer.getBalance_to().withdraw(transfer.getAmount());

        TransactionService transactionService = new TransactionService();
        transactionService.delete(transfer);

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
