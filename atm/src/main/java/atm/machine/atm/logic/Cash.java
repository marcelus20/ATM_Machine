package atm.machine.atm.logic;

public class Cash {

    protected int amount;

    public Cash(int amount){
        super();
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
