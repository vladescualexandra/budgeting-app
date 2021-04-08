package ro.ase.csie.degree.firebase;

import ro.ase.csie.degree.SplashActivity;
import ro.ase.csie.degree.model.Balance;

public class BalanceService extends FirebaseService {

    public BalanceService() {
    }

    public static Balance upsert(Balance balance) {
        if (balance == null) {
            return null;
        }

        if (balance.getId() == null || balance.getId().trim().isEmpty()) {
            String id = database.push().getKey();
            balance.setId(id);
        }

        balance.setUser(SplashActivity.KEY);

        database
                .child(Table.BALANCES.toString())
                .child(balance.getId())
                .setValue(balance);

        return balance;
    }

}
