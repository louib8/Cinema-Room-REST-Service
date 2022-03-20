package cinema;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RefundedTicket {
    @JsonIgnore
    private Seat seatInfo;

    public RefundedTicket(Seat seat) {
        this.seatInfo = seat;
    }

    @JsonProperty("returned_ticket")
    public Seat getRefundedTicket() {
        return seatInfo;
    }
}
