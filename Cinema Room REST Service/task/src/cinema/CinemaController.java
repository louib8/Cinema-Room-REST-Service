package cinema;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CinemaController {
    Cinema cinema;

    public CinemaController() {
        this.cinema = new Cinema(9, 9);
    }

    @GetMapping("/seats")
    public Seating getSeats() {
        return cinema.getTheater1().getSeating();
    }

    @PostMapping("/purchase")
    @ResponseBody
    public ResponseEntity purchaseTicket(@RequestBody Seat seat) {
        return this.cinema.getTheater1().completeTicketPurchase(seat, cinema);
    }

    @PostMapping("/return")
    @ResponseBody
    public ResponseEntity returnTicket(@RequestBody Token token) {
        return this.cinema.getTheater1().refundTicket(token.getToken(), cinema);
    }

    @PostMapping("/stats")
    @ResponseBody
    public ResponseEntity returnMovieStatistics(@RequestParam(required = false) String password) {
        if (password == null) {
            return new ResponseEntity(new ErrorResponse("The password is wrong!"), HttpStatus.UNAUTHORIZED);
        }
        return this.cinema.getStatistics(password);
    }

}
