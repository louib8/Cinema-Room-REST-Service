package cinema;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private ArrayList<Seat> availableSeatsAsList;
    @JsonIgnore
    private ConcurrentHashMap<Pair<Integer, Integer>, Seat> availableSeatsMap = new ConcurrentHashMap<>();


    public Seating(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        createSeating(rows, columns);
    }
    @JsonIgnore
    public ConcurrentHashMap<Pair<Integer, Integer>, Seat> getAvailableSeats() {
        return availableSeatsMap;
    }

    public int getColumns() {
        return columns;
    }

    public int getRows() {
        return rows;
    }

    public ArrayList<Seat> getAvailableSeatsAsList() {
        return availableSeatsAsList = new ArrayList<Seat>(this.getAvailableSeats().values());
    }

    public void setAvailableSeats(ConcurrentHashMap<Pair<Integer, Integer>, Seat> availableSeats) {
        this.availableSeatsMap = availableSeats;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    public void setRows(int rows) {
        this.rows = rows;
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

    private ConcurrentHashMap<Pair<Integer, Integer>, Seat> createSeating(int totalRows, int totalColumns) {
        // We add + 1 to row and column as row 0 col 0 doesn't make sense for a theater.
        for (int row = 1; row < totalRows + 1; row++) {
            for (int col = 1; col < totalColumns + 1; col++) {
                availableSeatsMap.put(new Pair<>(row, col), new Seat(row, col));
            }
        }
        return availableSeatsMap;
    }

    public boolean makeSeatUnavailable(Seat seat) {
        Pair<Integer, Integer> key = new Pair<>(seat.getRow(), seat.getColumn());
        if (this.getAvailableSeats().containsKey(key)) {
            this.getAvailableSeats().remove(key);
            return true;
        }
        return false;
    }

    public void addSeatToAvailableSeats(Seat seat) {
        this.availableSeatsMap.put(new Pair<>(seat.getRow(), seat.getColumn()), seat);
    }
}
