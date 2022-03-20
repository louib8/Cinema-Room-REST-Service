package cinema;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

@JsonPropertyOrder({"total_rows", "total_columns", "available_seats"})
public class Seating {
    @JsonProperty("total_rows")
    private int rows;
    @JsonProperty("total_columns")
    private int columns;
    @JsonProperty("available_seats")
    private ArrayList<Seat> availableSeats = new ArrayList<>();
    private ConcurrentHashMap<Pair<Integer, Integer>, Seat> availableSeatsMap = new ConcurrentHashMap<>();

    public Seating(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        createSeating(rows, columns);
    }

    public static boolean validSeat(Seating seating, Seat seat) {
        if (seat.getRow() > seating.getRows() || seat.getRow() < 1) {
            return false;
        }
        if (seat.getColumn() > seating.getColumns() || seat.getColumn() < 1) {
            return false;
        }
        return true;
    }

    private ArrayList<Seat> createSeating(int totalRows, int totalColumns) {
        // We add + 1 to row and column as row 0 col 0 doesn't make sense for a theater.
        for (int row = 1; row < totalRows + 1; row++) {
            for (int col = 1; col < totalColumns + 1; col++) {
                availableSeats.add(new Seat(row, col));
                availableSeatsMap.put(new Pair<Integer, Integer>(row, col), new Seat(row, col));
            }
        }
        return availableSeats;
    }

    public ArrayList<Seat> getAvailableSeats() {
        return availableSeats;
    }

    public int getColumns() {
        return columns;
    }

    public int getRows() {
        return rows;
    }

    public void setAvailableSeats(ArrayList<Seat> availableSeats) {
        this.availableSeats = availableSeats;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public boolean makeSeatUnavailable(Seat seat) {
        for (var availSeat : this.getAvailableSeats()) {
            if (availSeat.getRow() == seat.getRow() && availSeat.getColumn() == seat.getColumn() && availSeat.getPrice().equals(seat.getPrice())) {
                this.getAvailableSeats().remove(availSeat);
                return true;
            }
        }
        return false;
    }

    public void addSeatToAvailableSeats(Seat seat) {
        this.availableSeats.add(seat);
    }
}
