package atm.machine.atm.exeptions;

public class NoEnoughCashError extends Exception {

    public NoEnoughCashError() {
        super("Insufficient Funds.");
    }
}
