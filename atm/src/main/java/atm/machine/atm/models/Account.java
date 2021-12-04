package atm.machine.atm.models;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Account {

    // Acccount Number
    private final Long accountNumber;
    // PIN must be a string, because if the pin is 0001, the zeros will remain there
    private final String pin;
    // Opening Balance
    private final Double balance;
    // Overdraft
    private final Double overdraft;


    public Account(Long accountNumber, String pin, Double openingBalance, Double overdraft) {
        this.accountNumber = accountNumber;
        this.pin = pin;
        this.balance = openingBalance;
        this.overdraft = overdraft;
    }

    /**
     * Overloaded constructor to allow to insert Integers instead of Doubles.
     * @param accountNumber
     * @param pin
     * @param openingBalance
     * @param overdraft
     */
    public Account(Long accountNumber, String pin, Integer openingBalance, Integer overdraft) {
        this.accountNumber = accountNumber;
        this.pin = pin;
        this.balance = Double.valueOf(openingBalance);
        this.overdraft = Double.valueOf(overdraft);
    }

    public Account(Long accountId, String pin) {
        this.accountNumber = accountId;
        this.pin = pin;
        this.balance = 0.0;
        this.overdraft = 0.0;
    }

    public Account(Long accountNumber) {
        this.accountNumber = accountNumber;
        this.pin = "";
        this.balance = 0.0;
        this.overdraft = 0.0;
    }

    public Long getAccountNumber() {
        return accountNumber;
    }

    public String getPin() {
        return pin;
    }

    public Double getBalance() {
        return balance;
    }

    public Double getOverdraft() {
        return overdraft;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Account account = (Account) o;

        return new EqualsBuilder().append(accountNumber, account.accountNumber).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(accountNumber).toHashCode();
    }
}
