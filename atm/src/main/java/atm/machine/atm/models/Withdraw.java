package atm.machine.atm.models;

public class Withdraw {
    private final Integer fifties;
    private final Integer twenties;
    private final Integer tenners;
    private final Integer fivers;
    private final int total;

    public Withdraw(Integer fifties, Integer twenties, Integer tenners, Integer fivers) {
        this.fifties = fifties;
        this.twenties = twenties;
        this.tenners = tenners;
        this.fivers = fivers;
        this.total = fifties * 50 + twenties * 20 + tenners * 10 + fivers * 5;
    }

    public Withdraw(){
        this.fifties = 0;
        this.twenties = 0;
        this.tenners = 0;
        this.fivers = 0;
        this.total = fifties * 50 + twenties * 20 + tenners * 10 + fivers * 5;
    }

    public Integer getFifties() {
        return fifties;
    }

    public Integer getTwenties() {
        return twenties;
    }

    public Integer getTenners() {
        return tenners;
    }

    public Integer getFivers() {
        return fivers;
    }

    public int getTotal() {
        return total;
    }

    public Withdraw withFifties(Integer amountOfFifties) {
        return new Withdraw(amountOfFifties, twenties, tenners, fivers);
    }

    public Withdraw withTwenties(Integer amountOfTwenties) {
        return new Withdraw(fifties, amountOfTwenties, tenners, fivers);
    }

    public Withdraw withTenners(Integer amountOfTenners) {
        return new Withdraw(fifties, twenties, amountOfTenners, fivers);
    }

    public Withdraw withFivers(Integer amountOfFivers) {
        return new Withdraw(fifties, twenties, tenners, amountOfFivers);
    }
}
