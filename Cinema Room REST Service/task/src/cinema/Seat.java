package cinema;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.ArrayList;

public class Seat {
    private int row;
    private int column;
    private BigDecimal price;

@JsonCreator
    public Seat(
            @JsonProperty("row") int row,
            @JsonProperty("column") int column) {
        this.row = row;
        this.column = column;
        this.price = row <= 4 ? new BigDecimal(10) : new BigDecimal(8);
    }

    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
