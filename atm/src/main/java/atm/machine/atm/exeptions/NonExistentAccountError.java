package atm.machine.atm.exeptions;

public class NonExistentAccountError extends Exception {
    public NonExistentAccountError() {
        super("The account provided doesn't exist.");
    }
}
