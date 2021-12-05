package atm.machine.atm.dispenserlogic;

import atm.machine.atm.models.ATMStatus;
import atm.machine.atm.models.Withdraw;

public class ATMMachine {

    /**
     * Make it a singleton since there will be only one ATM machine in the system.
     */
    public static ATMMachine atmMachine;
    private Withdraw withdraw;

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
        withdraw = new Withdraw();
        fifties = Fifties.getInstance(this);
        twenties = Twenties.getInstance(this);
        tenners = Tenners.getInstance(this);
        fivers = Fivers.getInstance(this);
        assignChainedDispensers();
    }

    /**
     * Helper method to link the dispensers in chains. Starting from fifties and ending on fivers.
     */
    private void assignChainedDispensers() {
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

    public Withdraw getWithdraw() {
        return this.withdraw;
    }

    public void setWithdraw(Withdraw withdraw) {
        this.withdraw = withdraw;
    }
}
