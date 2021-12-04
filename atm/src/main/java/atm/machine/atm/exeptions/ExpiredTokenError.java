package atm.machine.atm.exeptions;

public class ExpiredTokenError extends Exception {
    public ExpiredTokenError() {
        super("The provided token has expired and thus cannot be used. You need to login again.");
    }
}
