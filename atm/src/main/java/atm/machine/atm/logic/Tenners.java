package atm.machine.atm.logic;

public class Tenners extends CashDispenser{

    /**
     * Each ATM machine will have only 1
     * Tenners dispenser, so make it a singleton
     * to guarantee instantiation of 1 Tenners
     */
    private static Tenners tenners;
    public static Tenners getInstance(){
        if(tenners == null){
            tenners = new Tenners();
        }
        return tenners;
    }

    private Tenners() {
        // There will be only 30 tenners in this ATM.
        this.numberOfNotes = 30;
    }

    @Override
    public void dispense(Cash cash) {
        if(cash != null){
            Integer amount = cash.getAmount();
            Integer remainder = amount;

            if(amount >= 10){
                Integer count = amount / 10;
                remainder = amount % 10;
                numberOfNotes = numberOfNotes - count;
            }

            if(remainder > 0 && this.nextCashDispenser!= null){
                this.nextCashDispenser.dispense(new Cash(remainder));
            }
        }
    }
}
