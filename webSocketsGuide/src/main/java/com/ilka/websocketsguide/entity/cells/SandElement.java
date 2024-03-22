package com.ilka.websocketsguide.entity.cells;

import com.ilka.websocketsguide.entity.Matrix;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Random;


public class SandElement extends Element{



    private boolean canMoveDiagonally = false;


    public void setCanMoveDiagonally(boolean canMoveDiagonally) {
        this.canMoveDiagonally = canMoveDiagonally;
    }

    public SandElement(int x, int y){
        super(x, y);
        this.isFreeFalling = true;
        innerValue = 1;
        density = 10;
    }

    @Override
    public boolean canMoveDown(Cell[][] cells, int i, int j){
        if (i + 1 < 50 && cells[i + 1][j].getElement().getDensity() < density){
            return true;
        }
        return false;
    }

    private boolean canMoveDiagonally(Cell[][] matrix, int i, int j, int directionVector){
        if (i + 1 < Matrix.MATRIX_SIZE && j + directionVector < Matrix.MATRIX_SIZE && j + directionVector > 0
                && !(matrix[i + 1][j].getElement() instanceof EmptyElement)
                && matrix[i][j + directionVector].getElement().density < density
                && matrix[i + 1][j + directionVector].getElement().density < density){
//            System.out.println("Can move diagonally");
            return true;
        }
        return false;
    }

    public boolean canMoveDiagonallyLeft(Cell[][] matrix, int i, int j) {
        return canMoveDiagonally(matrix, i, j, 1);
    }

    public boolean canMoveDiagonallyRight(Cell[][] matrix, int i, int j) {
        return canMoveDiagonally(matrix, i, j, -1);
    }

//    public boolean canMoveDiagonallyLeft(Cell[][] matrix, int i, int j) {
//        return false;
//    }
//
//    public boolean canMoveDiagonallyRight(Cell[][] matrix, int i, int j) {
//        return new Random().nextInt(100 >)
//    }


//    @Override
//    public void updateCell(){
//        Cell[][] cells = matrix.getMatrix();
//        if (x + 1 < matrix.getMatrixSize() && cells[x + 1][y].getInnerValue() == 0){
//            //matrix.swapTwoCells(cells[x][y], cells[x + 1][y]);
//            Cell underSand = cells[x + 1][y];
//            cells[x + 1][y] = cells[x][y];
//            cells[x][y] = new Cell(x, y, matrix);
//        }
//    }


    @Override
    public String toString() {
        return "SandElement{" +
                "innerValue=" + innerValue +
                ", x=" + x +
                ", y=" + y +
                ", isFreeFalling=" + isFreeFalling +
                ", canMoveDiagonally=" + canMoveDiagonally +
                '}';
    }
}
