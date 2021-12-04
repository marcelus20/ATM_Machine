package atm.machine.atm.models;

public class AccountBalance {
    private final Long accountNumber;
    private final Double accountBalance;

    public AccountBalance(Long accountNumber, Double accountBalance) {
        this.accountNumber = accountNumber;
        this.accountBalance = accountBalance;
    }

    public Long getAccountNumber() {
        return accountNumber;
    }

    public Double getAccountBalance() {
        return accountBalance;
    }
}
