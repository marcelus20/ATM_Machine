package atm.machine.atm.dispenserlogic;

public class Fifties extends CashDispenser{


    /**
     * Each ATM machine will have only 1
     * Fifities dispenser, so make it a singleton
     * to guarantee instantiation of 1 Fifties
     */
    private static Fifties fifties;

    public static Fifties getInstance(){
        if(fifties == null){
            fifties = new Fifties();
        }
        return fifties;
    }

    /**
     * Constructor, initialise number of notes to 10
     */
    private Fifties(){
        // The assignment states that there will be 10 fifties.
        this.numberOfNotes = 10;
    }

    @Override
    public void dispense(Cash cash) {
        if(cash != null){
            Integer amount = cash.getAmount();
            Integer remainder = amount;

            if(amount >= 50){
                Integer count = amount / 50;
                remainder = amount % 50;
                numberOfNotes = numberOfNotes - count;
            }

            if(remainder > 0 && this.nextCashDispenser!= null){
                this.nextCashDispenser.dispense(new Cash(remainder));
            }
        }
    }
}
