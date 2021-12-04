package atm.machine.atm.dispenserlogic;

public class Twenties extends CashDispenser{

    /**
     * Each ATM machine will have only 1
     * Twenties dispenser, so make it a singleton
     * to guarantee instantiation of 1 Twenties
     */
    private static Twenties twenties;

    public static Twenties getInstance(){
        if(twenties == null){
            twenties = new Twenties();
        }
        return twenties;
    }

    private Twenties() {
        // Per Assignment requirement, there will be only 30 twenties
        this.numberOfNotes = 30;
    }

    @Override
    public void dispense(Cash cash) {
        if(cash != null){
            Integer amount = cash.getAmount();
            Integer remainder = amount;

            if(amount >= 20){
                Integer count = amount / 20;
                // Amount that will in fact be dispensed.
                Integer actualAmountToDispense = count > numberOfNotes ? numberOfNotes : count;

                remainder = amount % 20 + (count - actualAmountToDispense) * 20;
                numberOfNotes = numberOfNotes - actualAmountToDispense;
            }



            if(remainder > 0 && this.nextCashDispenser!= null){
                this.nextCashDispenser.dispense(new Cash(remainder));
            }
        }
    }
}
