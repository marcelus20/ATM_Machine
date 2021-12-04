package atm.machine.atm.exeptions;

public class InvalidTokenError extends Exception {

    public InvalidTokenError() {
        super("The token provided is invalid.");
    }
}
