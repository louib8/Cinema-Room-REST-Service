package cinema;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

public class Cinema {
    private Theater theater1;
    private String managerPassword = "super_secret";
    private Statistics cinemaStatistics;

    public Cinema(int totalRows, int totalColumns) {
        this.theater1 = new Theater(totalRows, totalColumns);
        this.cinemaStatistics = new Statistics(new BigDecimal(0), theater1.getSeating().getAvailableSeats().size(), 0);
    }

    public Theater getTheater1() {
        return theater1;
    }

    public Statistics getCinemaStatistics() {
        return cinemaStatistics;
    }

    public void setCinemaStatistics(Statistics cinemaStatistics) {
        this.cinemaStatistics = cinemaStatistics;
    }

    public ResponseEntity getStatistics(String password) {
        if (!this.authenticateManager(password)) {
            return new ResponseEntity(new ErrorResponse("The password is wrong!"), HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity(this.cinemaStatistics, HttpStatus.OK);
    }

    private boolean authenticateManager(String password) {
        if (password.equals(managerPassword)) {
            return true;
        }
        return false;
    }
}
