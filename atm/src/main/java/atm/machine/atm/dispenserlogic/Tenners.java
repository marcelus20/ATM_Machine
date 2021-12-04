package atm.machine.atm.dispenserlogic;

public class Tenners extends CashDispenser{

    /**
     * Each ATM machine will have only 1
     * Tenners dispenser, so make it a singleton
     * to guarantee instantiation of 1 Tenners
     */
    private static Tenners tenners;
    public static Tenners getInstance(ATMMachine atmMachine){
        if(tenners == null){
            tenners = new Tenners(atmMachine);
        }
        return tenners;
    }

    public static Tenners getInstance(){
        return getInstance(null);
    }

    private Tenners(ATMMachine atmMachine) {
        // There will be only 30 tenners in this ATM.
        this.numberOfNotes = 30;
        this.atmMachine = atmMachine;
    }

    @Override
    public void dispense(Cash cash) {
        if(cash != null){
            Integer amount = cash.getAmount();
            Integer remainder = amount;

            if(amount >= 10){
                Integer count = amount / 10;
                // Amount that will in fact be dispensed.
                Integer actualAmountToDispense = count > numberOfNotes ? numberOfNotes : count;

                this.atmMachine.setWithdraw(
                        this.atmMachine.getWithdraw().withTenners(actualAmountToDispense)
                );

                remainder = amount % 10 + (count - actualAmountToDispense) * 10;
                numberOfNotes = numberOfNotes - actualAmountToDispense;
            }

            if(remainder > 0 && this.nextCashDispenser!= null){
                this.nextCashDispenser.dispense(new Cash(remainder));
            }
        }
    }
}
