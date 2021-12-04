package atm.machine.atm.logic;

import atm.machine.atm.models.ATMStatus;

public class ATMMachine {
    protected static CashDispenser fifties = Fifties.getInstance();
    protected static CashDispenser twenties = Twenties.getInstance();
    protected static CashDispenser tenners = Tenners.getInstance();
    protected static CashDispenser fivers = Fivers.getInstance();
    protected static CashDispenser chain;


    static{
        fifties.setNextCashDispenser(twenties);
        twenties.setNextCashDispenser(tenners);
        tenners.setNextCashDispenser(fivers);
        chain = fifties;
    }

    public static void withdraw(Cash cash){
        if(cash != null){
            chain.dispense(cash);
        }
    }

    // This method will return the ATMStatus model representation out of the data retrieved
    // from the dispensers attributes
    public static ATMStatus serialise(){
        return new ATMStatus(
                fifties.numberOfNotes,
                twenties.numberOfNotes,
                tenners.numberOfNotes,
                fivers.numberOfNotes
        );
    }
}
