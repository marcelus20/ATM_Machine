package atm.machine.atm.models;

/**
 * Model class to serialise as json when the /status endpoint is called
 */
public class ATMStatus {

    private final Integer fifties;
    private final Integer twenties;
    private final Integer tenners;
    private final Integer fivers;
    // This is a derived attribute and it's value will be calculated on the fly.
    private final Double total;

    public ATMStatus(Integer fifties, Integer twenties, Integer tenners, Integer fivers) {
        this.fifties = fifties;
        this.twenties = twenties;
        this.tenners = tenners;
        this.fivers = fivers;
        // Calculate the value on the fly.
        this.total = Double.valueOf(50 * fifties + 20 * twenties + 10 * tenners + 5 * fivers);
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

    public Double getTotal() {
        return total;
    }
}
