package atm.machine.atm.dispenserlogic;

public abstract class CashDispenser {

    protected  CashDispenser nextCashDispenser;
    /**
     * The number of notes per assigments.
     *  Fifties will be initiated with 10
     *  Twenties will be Initiated with 30
     *  Tenners will be initiated with x 30
     *  Fivers will be initiated with x 20
     */
    protected Integer numberOfNotes;

    /**
     * Chain of responsibility Design Pattern. Set the next dispenser
     * To fulfill withdraw request.
     *
     * Fifties will be set next to Twenties.
     * Twenties will be set next to Tenners
     * Tenners Will be set next to Fivers.
     * @param nextCashDispenser
     */
    public void setNextCashDispenser(CashDispenser nextCashDispenser){
        this.nextCashDispenser = nextCashDispenser;
    }

    public abstract void dispense(Cash cash);
}
