package atm.machine.atm.dispenserlogic;

public class Fifties extends CashDispenser{


    /**
     * Each ATM machine will have only 1
     * Fifities dispenser, so make it a singleton
     * to guarantee instantiation of 1 Fifties
     */
    private static Fifties fifties;

    public static Fifties getInstance(ATMMachine atmMachine){
        if(fifties == null){
            fifties = new Fifties(atmMachine);
        }
        return fifties;
    }

    public static Fifties getInstance(){
        return getInstance(null);
    }

    /**
     * Constructor, initialise number of notes to 10
     */
    private Fifties(ATMMachine atmMachine){
        // The assignment states that there will be 10 fifties.
        this.numberOfNotes = 10;
        this.atmMachine = atmMachine;
    }

    @Override
    public void dispense(Cash cash) {
        if(cash != null){
            Integer amount = cash.getAmount();
            Integer remainder = amount;

            if(amount >= 50){
                Integer count = amount / 50;
                // Amount that will in fact be dispensed.
                Integer actualAmountToDispense = count > numberOfNotes ? numberOfNotes : count;

                this.atmMachine.setWithdraw(
                        this.atmMachine.getWithdraw().withFifties(actualAmountToDispense)
                );

                remainder = amount % 50 + (count - actualAmountToDispense) * 50;
                numberOfNotes = numberOfNotes - actualAmountToDispense;
            }

            if(remainder > 0 && this.nextCashDispenser!= null){
                this.nextCashDispenser.dispense(new Cash(remainder));
            }
        }
    }
}
