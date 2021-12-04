package atm.machine.atm.tablesets;

import atm.machine.atm.models.Session;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class SessionMap extends HashMap<String, Session> implements Queryable<Session> {
    @Override
    public Session retrieveOneOrNull(Session object) {
        String token = object.getToken();
        Session session = this.get(token);
        return session;
    }


    @Override
    public void write(Session object) {
        this.put(object.getToken(), object);
    }
}
