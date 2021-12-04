package atm.machine.atm.models;

public class PostWithdrawAccount {
    private final Long accountNumber;
    private final Double balance;
    private final Withdraw withdraw;

    public PostWithdrawAccount(Long accountNumber, Double balance, Withdraw withdraw) {
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.withdraw = withdraw;
    }

    public Long getAccountNumber() {
        return accountNumber;
    }

    public Double getBalance() {
        return balance;
    }

    public Withdraw getWithdraw() {
        return withdraw;
    }
}
