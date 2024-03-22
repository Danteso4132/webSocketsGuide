package com.ilka.websocketsguide.entity.cells;

import com.ilka.websocketsguide.entity.Matrix;

import java.util.Random;

public class WaterElement extends Element{
    public WaterElement(int x, int y) {
        super(x, y);
        density = 5;
        innerValue = 3;
    }



    @Override
    public boolean canMoveDown(Cell[][] cells, int i, int j){
        if (i + 1 < 50 && cells[i + 1][j].getElement().getDensity() < density){
            return true;
        }
        return false;
    }

    private boolean canMoveHorizontally(Cell[][] cells, int i, int j, int directionVector){
        if (j + directionVector < Matrix.MATRIX_SIZE && j + directionVector > 0
                && cells[i][j + directionVector].getElement().getDensity() < density){
            return true;
        }
        return false;
    }

    public boolean canMoveLeft(Cell[][] cells, int i, int j){
        return canMoveHorizontally(cells, i, j, -1);
    }
    public boolean canMoveRight(Cell[][] cells, int i, int j){
        return canMoveHorizontally(cells, i, j, 1);
    }
}
