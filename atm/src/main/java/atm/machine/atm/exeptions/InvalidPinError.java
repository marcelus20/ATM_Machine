package atm.machine.atm.exeptions;

public class InvalidPinError extends Exception {
    public InvalidPinError() {
        super("The PIN provided is incorrect.");
    }
}