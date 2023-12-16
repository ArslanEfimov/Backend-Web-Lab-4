package ru.arslanefimov.web4.dto.requests;

import java.util.Objects;

public class PointResultRequestDTO {

    private double x;
    private double y;
    private double r;

    public PointResultRequestDTO(){}

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getR() {
        return r;
    }

    public void setR(double r) {
        this.r = r;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PointResultRequestDTO that = (PointResultRequestDTO) o;
        return Double.compare(x, that.x) == 0 && Double.compare(y, that.y) == 0 && Double.compare(r, that.r) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, r);
    }
}
