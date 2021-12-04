package atm.machine.atm.models;

public class AccountBalance {
    private final Long accountNumber;
    private final Double accountBalance;
    private final Double maximumWithdrawAmount;

    public AccountBalance(Long accountNumber, Double accountBalance, Double maximunWithdrawAmount) {
        this.accountNumber = accountNumber;
        this.accountBalance = accountBalance;
        this.maximumWithdrawAmount = maximunWithdrawAmount;
    }

    public Long getAccountNumber() {
        return accountNumber;
    }

    public Double getAccountBalance() {
        return accountBalance;
    }

    public Double getMaximumWithdrawAmount() {
        return maximumWithdrawAmount;
    }
}
