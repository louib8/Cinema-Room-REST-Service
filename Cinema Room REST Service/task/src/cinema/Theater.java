package cinema;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class Theater {
    private Seating seating;
    private ConcurrentHashMap<UUID, Ticket> tickets;

    public Theater(int totalRows, int totalColumns) {
        this.seating = new Seating(totalRows, totalColumns);
        this.tickets = new ConcurrentHashMap<>();
    }

    public Seating getSeating() {
        return seating;
    }

    public ConcurrentHashMap<UUID, Ticket> getTickets() {
        return tickets;
    }

    public void setSeating(Seating seating) {
        this.seating = seating;
    }

    public void setTickets(ConcurrentHashMap<UUID, Ticket> tickets) {
        this.tickets = tickets;
    }

    private void addTicket(Ticket ticket) {
        this.tickets.put(ticket.getToken(), ticket);
    }

    public ResponseEntity completeTicketPurchase(Seat seat, Cinema cinema) {
        if (!Seating.validSeat(this.getSeating(), seat)) {
            return new ResponseEntity(new ErrorResponse("The number of a row or a column is out of bounds!"), HttpStatus.BAD_REQUEST);
        }
        if (!Ticket.ticketAvailable(this, seat)) {
            return new ResponseEntity(new ErrorResponse("The ticket has been already purchased!"), HttpStatus.BAD_REQUEST);
        }
        if(!this.getSeating().makeSeatUnavailable(seat)) {
            return new ResponseEntity(new ErrorResponse("Issue reserving seat!"), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        Ticket ticket = new Ticket(seat);
        this.addTicket(ticket);

        Statistics stats = new Statistics(
                cinema.getCinemaStatistics().getCurrentIncome().add(ticket.getSeat().getPrice()),
                cinema.getTheater1().getSeating().getAvailableSeats().size(),
                tickets.size()
        );
        cinema.setCinemaStatistics(stats);

        return new ResponseEntity(ticket, HttpStatus.OK);
    }

    public ResponseEntity refundTicket(UUID token, Cinema cinema) {
        if (this.getTickets().containsKey(token)) {
            Ticket removedTicket = this.getTickets().remove(token);
            this.seating.addSeatToAvailableSeats(removedTicket.getSeat());

            Statistics stats = new Statistics(
                    cinema.getCinemaStatistics().getCurrentIncome().subtract(removedTicket.getSeat().getPrice()),
                    cinema.getTheater1().getSeating().getAvailableSeats().size(),
                    tickets.size()
            );

            cinema.setCinemaStatistics(stats);

            return new ResponseEntity(
                    new RefundedTicket(removedTicket.getSeat()),
                    HttpStatus.OK
            );
        }
        return new ResponseEntity(new ErrorResponse("Wrong token!"), HttpStatus.BAD_REQUEST);
    }
}
