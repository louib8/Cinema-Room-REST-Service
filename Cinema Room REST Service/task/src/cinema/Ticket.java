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
        Pair<Integer, Integer> key = new Pair<>(seat.getRow(), seat.getColumn());
        if (theater.getSeating().getAvailableSeats().containsKey(key)) {
            return true;
        }
        return false;
    }
}
