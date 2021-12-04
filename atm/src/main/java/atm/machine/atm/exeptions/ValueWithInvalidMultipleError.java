package atm.machine.atm.exeptions;

public class ValueWithInvalidMultipleError extends Throwable {

    public ValueWithInvalidMultipleError() {
        super("The value parameter must be a multiple of 50 or 20 or 10 or 5.");
    }
}
