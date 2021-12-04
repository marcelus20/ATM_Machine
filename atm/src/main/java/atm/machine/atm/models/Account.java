package atm.machine.atm.models;

public class Account {

    // Acccount Number
    private final Long accountNumber;
    // PIN
    private final Integer pin;
    // Opening Balance
    private final Double openingBalance;
    // Overdraft
    private final Double overdraft;


    public Account(Long accountNumber, Integer pin, Double openingBalance, Double overdraft) {
        this.accountNumber = accountNumber;
        this.pin = pin;
        this.openingBalance = openingBalance;
        this.overdraft = overdraft;
    }

    /**
     * Overloaded constructor to allow to insert Integers instead of Doubles.
     * @param accountNumber
     * @param pin
     * @param openingBalance
     * @param overdraft
     */
    public Account(Long accountNumber, Integer pin, Integer openingBalance, Integer overdraft) {
        this.accountNumber = accountNumber;
        this.pin = pin;
        this.openingBalance = Double.valueOf(openingBalance);
        this.overdraft = Double.valueOf(overdraft);
    }

    public Long getAccountNumber() {
        return accountNumber;
    }

    public Integer getPin() {
        return pin;
    }

    public Double getOpeningBalance() {
        return openingBalance;
    }

    public Double getOverdraft() {
        return overdraft;
    }
}
