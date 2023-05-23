package common.collectionClasses;

import java.io.Serializable;

public class Coordinates implements Serializable {
    private Double x; //поле не может быть null
    private int y;

    public Coordinates() {};

    public Coordinates(Double x, int y) {
        if (x == null) throw new IllegalArgumentException("Invalid coordinates");
        this.x = x;
        this.y = y;
    }

    public Coordinates(Double x) {
        this.x = x;
    }

    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "X: " + x + ", Y: " + y;
    }
}
