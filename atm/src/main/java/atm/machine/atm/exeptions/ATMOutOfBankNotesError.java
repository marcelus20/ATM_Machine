package atm.machine.atm.exeptions;

public class ATMOutOfBankNotesError extends Exception {

    public ATMOutOfBankNotesError() {
        super("This request cannot be fulfilled because the ATM doesn't have enough cash.");
    }
}
