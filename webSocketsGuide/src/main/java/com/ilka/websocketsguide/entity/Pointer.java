package com.ilka.websocketsguide.entity;

public class Pointer {
    private int x;
    private int y;
    private int colorCode;

    public int getColorCode() {
        return colorCode;
    }

    public void setColorCode(int colorCode) {
        this.colorCode = colorCode;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Pointer(int x, int y, int colorCode) {
        this.x = x;
        this.y = y;
        this.colorCode = colorCode;
    }

    @Override
    public String toString() {
        return "Pointer{" +
                "x=" + x +
                ", y=" + y +
                ", colorCode=" + colorCode +
                '}';
    }
}
