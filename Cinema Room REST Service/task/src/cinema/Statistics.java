package cinema;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class Statistics {
    private BigDecimal currentIncome;
    private int availableSeats;
    private int purchasedTicketCount;

    public Statistics(BigDecimal currentIncome, int availableSeats, int purchasedTicketCount) {
        this.currentIncome = currentIncome;
        this.availableSeats = availableSeats;
        this.purchasedTicketCount = purchasedTicketCount;
    }

    @JsonProperty("current_income")
    public BigDecimal getCurrentIncome() {
        return currentIncome;
    }

    @JsonProperty("number_of_available_seats")
    public int getAvailableSeats() {
        return availableSeats;
    }

    @JsonProperty("number_of_purchased_tickets")
    public int getPurchasedTicketCount() {
        return purchasedTicketCount;
    }
}
