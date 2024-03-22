package com.ilka.websocketsguide.entity.cells;

import com.ilka.websocketsguide.entity.Matrix;

public class Element {

    protected int innerValue = 0;
    protected int x;
    protected int y;

    protected int density;

    public int getDensity() {
        return density;
    }

    public boolean isFreeFalling() {
        return isFreeFalling;
    }

    public void setFreeFalling(boolean freeFalling) {
        isFreeFalling = freeFalling;
    }

    protected boolean isFreeFalling = false;

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


    public Element(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getInnerValue() {
        return innerValue;
    }

    public void setInnerValue(int innerValue) {
        this.innerValue = innerValue;
    }

    public void updateCell(){

    }

    public boolean canMoveDown(Cell[][] cells, int i, int j){
        return false;
    }


    @Override
    public String toString() {
        return "Element{" +
                " innerValue=" + innerValue +
                ", x=" + x +
                ", y=" + y +
                ", isFreeFalling=" + isFreeFalling +
                '}';
    }
}
