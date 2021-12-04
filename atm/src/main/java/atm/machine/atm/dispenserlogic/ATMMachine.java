package atm.machine.atm.dispenserlogic;

import atm.machine.atm.models.ATMStatus;

public class ATMMachine {

    public static ATMMachine atmMachine;

    public static ATMMachine getInstance(){
        if(atmMachine == null){
            atmMachine = new ATMMachine();
        }
        return atmMachine;
    }


    private CashDispenser fifties;
    private CashDispenser twenties;
    private CashDispenser tenners;
    protected CashDispenser fivers;
    protected CashDispenser chain;


    private ATMMachine(){
        fifties = Fifties.getInstance();
        twenties = Twenties.getInstance();
        tenners = Tenners.getInstance();
        fivers = Fivers.getInstance();
        setDispensers();
    }

    private void setDispensers() {
        fifties.setNextCashDispenser(twenties);
        twenties.setNextCashDispenser(tenners);
        tenners.setNextCashDispenser(fivers);
        chain = fifties;
    }

    public void withdraw(Cash cash){
        if(cash != null){
            chain.dispense(cash);
        }
    }

    // This method will return the ATMStatus model representation out of the data retrieved
    // from the dispensers attributes
    public ATMStatus serialise(){
        return new ATMStatus(
                fifties.getNumberOfNotes(),
                twenties.getNumberOfNotes(),
                tenners.getNumberOfNotes(),
                fivers.getNumberOfNotes()
        );
    }
}
