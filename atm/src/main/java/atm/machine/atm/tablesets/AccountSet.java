package atm.machine.atm.tablesets;

import atm.machine.atm.models.Account;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class AccountSet extends HashSet <Account> implements Queryable<Account>{
    @Override
    public Account retrieveOneOrNull(Account acc) {
        Long accountNumber = acc.getAccountNumber();
        List<Account> thisASList = this.stream().filter(a->a.getAccountNumber().equals(accountNumber)).collect(Collectors.toList());

        if(thisASList.size() == 1){
            return thisASList.get(0);
        }else{
            return null;
        }
    }
}
