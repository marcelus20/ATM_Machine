package atm.machine.atm.tablesets;

import atm.machine.atm.models.Account;

import java.util.HashMap;

public class AccountMap extends HashMap<Long, Account> implements Queryable<Account>{
    @Override
    public Account retrieveOneOrNull(Account acc) {
        Long accountNumber = acc.getAccountNumber();
        Account account = this.get(acc.getAccountNumber());
        return account;
    }



    @Override
    public void write(Account object) {
        this.put(object.getAccountNumber(), object);
    }
}
