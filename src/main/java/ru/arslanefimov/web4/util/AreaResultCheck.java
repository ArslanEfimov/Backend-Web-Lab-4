package ru.arslanefimov.web4.util;

public class AreaResultCheck {

    public static boolean checkResult(double x, double y, double r){
        return (checkSquare(x, y, r) || checkTriangle(x, y, r) || checkCircle(x, y, r));
    }

    private static boolean checkSquare(double x, double y, double r){
        return ( (x>=0 && y<=0) && (x<=r) && (y>=(-r/2)) );
    }

    private static boolean checkTriangle(double x, double y, double r){
        return ((x<=0 && y>=0) && r>=Math.abs(x) + Math.abs(y));
    }

    private static boolean checkCircle(double x, double y, double r){
        return ((x<=0 && y<=0) && (Math.pow(x,2) + Math.pow(y, 2) <= Math.pow(r/2, 2)));
    }
}
