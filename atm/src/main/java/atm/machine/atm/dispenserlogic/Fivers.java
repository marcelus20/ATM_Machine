package atm.machine.atm.dispenserlogic;

public class Fivers extends CashDispenser{

    /**
     * Each ATM machine will have only 1
     * Fivers dispenser, so make it a singleton
     * to guarantee instantiation of 1 Fivers
     */
    public static Fivers fivers;

    public static Fivers getInstance(ATMMachine atmMachine){
        if(fivers == null){
            fivers = new Fivers(atmMachine);
        }
        return fivers;
    }

    public static Fivers getInstance(){
        return getInstance(null);
    }

    private Fivers(ATMMachine atmMachine) {
        // There will be only 20 fivers in this ATM Machine.
        this.numberOfNotes = 20;
        this.atmMachine = atmMachine;
    }

    @Override
    public void dispense(Cash cash) {
        if(cash != null){
            Integer amount = cash.getAmount();
            Integer remainder = amount;

            if(amount >= 5){
                Integer count = amount / 5;
                // Amount that will in fact be dispensed.
                Integer actualAmountToDispense = count > numberOfNotes ? numberOfNotes : count;

                this.atmMachine.setWithdraw(
                        this.atmMachine.getWithdraw().withFivers(actualAmountToDispense)
                );

                remainder = amount % 5 + (count - actualAmountToDispense) * 5;
                numberOfNotes = numberOfNotes - actualAmountToDispense;
            }

            if(remainder > 0 && this.nextCashDispenser!= null){
                this.nextCashDispenser.dispense(new Cash(remainder));
            }
        }
    }
}
