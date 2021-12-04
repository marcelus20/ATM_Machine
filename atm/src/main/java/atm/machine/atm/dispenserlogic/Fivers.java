package atm.machine.atm.dispenserlogic;

public class Fivers extends CashDispenser{

    /**
     * Each ATM machine will have only 1
     * Fivers dispenser, so make it a singleton
     * to guarantee instantiation of 1 Fivers
     */
    public static Fivers fivers;

    public static Fivers getInstance(){
        if(fivers == null){
            fivers = new Fivers();
        }
        return fivers;
    }

    private Fivers() {
        // There will be only 20 fivers in this ATM Machine.
        this.numberOfNotes = 20;
    }

    @Override
    public void dispense(Cash cash) {
        if(cash != null){
            Integer amount = cash.getAmount();
            Integer remainder = amount;

            if(amount >= 5){
                Integer count = amount / 5;
                remainder = amount % 5;
                // Update the perRequire
                numberOfNotes = numberOfNotes - count;
            }

            if(remainder > 0 && this.nextCashDispenser!= null){
                this.nextCashDispenser.dispense(new Cash(remainder));
            }
        }
    }
}
