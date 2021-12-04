package atm.machine.atm.tablesets;

import atm.machine.atm.models.Account;
import atm.machine.atm.models.Session;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class SessionSet extends HashSet<Session> implements Queryable<Session> {
    @Override
    public Session retrieveOneOrNull(Session object) {
        String token = object.getToken();
        List<Session> thisASList = this.stream().filter(a->a.getToken().equals(token)).collect(Collectors.toList());

        if(thisASList.size() == 1){
            return thisASList.get(0);
        }else{
            return null;
        }
    }
}
