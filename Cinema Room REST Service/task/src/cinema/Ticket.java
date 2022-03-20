package cinema;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class Ticket {
    private UUID token;
    @JsonProperty(value="ticket")
    private Seat seat;

    public Ticket(Seat seat) {
        this.seat = seat;
        this.token = new Token().getToken();
    }

    public Seat getSeat() {
        return seat;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
    }

    public UUID getToken() {
        return token;
    }

    public static boolean ticketAvailable(Theater theater, Seat seat) {
        for (var availSeat : theater.getSeating().getAvailableSeats()) {
            if (availSeat.getRow() == seat.getRow() && availSeat.getColumn() == seat.getColumn() && availSeat.getPrice().equals(seat.getPrice())) {
                return true;
            }
        }
        return false;
    }


}
