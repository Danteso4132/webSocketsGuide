package com.ilka.websocketsguide.entity;

import com.ilka.websocketsguide.entity.cells.*;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
@EnableAsync
@EnableScheduling
public class Matrix {


    public static int MATRIX_SIZE = 50;

    private Cell[][] matrix = new Cell[MATRIX_SIZE][MATRIX_SIZE];


    public Matrix() {
        updateMatrixWithRandomField();
//        Random random = new Random();
//        for (int i = 0; i < matrixSize; i++) {
//            for (int j = 0; j < matrixSize; j++) {
//                if (random.nextInt(100) > 70) {
//                    Cell cell = new Cell(new SandElement(i, j));
//                    matrix[i][j] = cell;
//                }
//                else{
//                    Cell cell = new Cell();
//                    cell.setElement(new EmptyElement(i, j));
//                    matrix[i][j] = cell;
//                }
//            }
//        }
    }

    public void updateMatrixWithRandomField() {
        Random random = new Random();
        for (int i = 0; i < MATRIX_SIZE; i++) {
            for (int j = 0; j < MATRIX_SIZE; j++) {
                if (random.nextInt(100) > 70) {
                    Cell cell = new Cell();
                    int r = random.nextInt(100);

                    if (r > 0 && r < 30) {
                        cell.setElement(new SandElement(i, j));
                    }
                    else if (r > 30 && r < 70){
                        cell.setElement(new StoneElement(i, j));
                    }
                    else{
                        cell.setElement(new WaterElement(i, j));
                    }
                    matrix[i][j] = cell;
                }
                else{
                    Cell cell = new Cell();
                    cell.setElement(new EmptyElement(i, j));
                    matrix[i][j] = cell;
                }
            }
        }
        //System.out.println("Initial matrix = " + matrix);
    }


    public int[][] getPlainMatrix() {
        int[][] m = new int[MATRIX_SIZE][MATRIX_SIZE];
        for (int i = 0; i < MATRIX_SIZE; i++) {
            for (int j = 0; j < MATRIX_SIZE; j++) {
                m[i][j] = matrix[i][j].getElement().getInnerValue();
            }
        }
        return m;
    }

    public Cell[][] getMatrix() {
        return matrix;
    }

    @Scheduled(fixedRate = 100)
    private void updateMatrix() {
        for (int i = MATRIX_SIZE - 2; i >= 0; i--) {
            for (int j = 0; j < MATRIX_SIZE; j++) {
                if (matrix[i][j].getElement() instanceof SandElement){
                    SandElement sand = (SandElement) matrix[i][j].getElement();
                    if (sand.canMoveDown(matrix, i, j)) {
                        swapTwoCells(matrix[i][j], matrix[i + 1][j]);
                        List<Cell> cellsAround = cellsWindow(matrix[i][j], 1);
                        for (Cell cell : cellsAround) {
                            if (cell.getElement() instanceof SandElement && new Random().nextInt(100) > 60){
                                (cell.getElement()).setFreeFalling(true);
                            }
                        }
                    }
                    else if (sand.canMoveDiagonallyLeft(matrix, i, j)){
                        swapTwoCells(matrix[i + 1][j + 1], matrix[i][j]);
                    } else if (sand.canMoveDiagonallyRight(matrix, i, j)) {
                        swapTwoCells(matrix[i + 1][j - 1], matrix[i][j]);
                    }
                }
                else if (matrix[i][j].getElement() instanceof WaterElement){
                    WaterElement water = (WaterElement) matrix[i][j].getElement();
                    Random r = new Random();
                    if (water.canMoveDown(matrix, i, j)){
                        swapTwoCells(matrix[i][j], matrix[i + 1][j]);
                    }
                    else if (r.nextInt(100) > 50 && water.canMoveRight(matrix, i, j)){
                        swapTwoCells(matrix[i][j], matrix[i][j + 1]);
                    }
                    else if (water.canMoveLeft(matrix, i, j)){
                        swapTwoCells(matrix[i][j], matrix[i][j - 1]);
                    }
                }
            }
        }
    }

//    @Scheduled(fixedRate = 100)
//    private void updateMatrix() {
//        fall();
//        fallDiagonally(1);
//    }
//
//    private void fall() {
//        for (int i = matrixSize - 2; i >= 0; i--) {
//            for (int j = 0; j < matrixSize; j++) {
//                if (matrix[i][j].getInnerValue() == 1 && matrix[i + 1][j].getInnerValue() == 0) {
//                    swapTwoCells(matrix[i][j], matrix[i + 1][j]);
//                }
//            }
//        }
//    }
//
//    private void fallDiagonally(int intensity) {
//        Random r = new Random();
//        try {
//            for (int i = 1; i < matrixSize - 1; i++) {
//                //for (int i = matrixSize - 2; i >= 0; i--){
//                for (int j = 1; j < matrixSize - 1; j++) {
//                    if (matrix[i][j].getInnerValue() == 1
//                            && i + 1 < matrixSize
//                            && matrix[i + 1][j].getInnerValue() == 1
//                            && matrix[i + 2][j].getInnerValue() == 1
//                            && matrix[i + 3][j].getInnerValue() == 1) {
//                        int fallLeftChance = r.nextInt(100);
//                        int fallRightChance = r.nextInt(100);
//                        if (j - 1 > 0 && matrix[i + 1][j - 1].getInnerValue() == 0 && fallLeftChance > 70) {
//                            swapTwoCells(matrix[i][j], matrix[i + 1][j - 1]);
//                        }
//                        if (j + 1 < matrixSize && matrix[i + 1][j + 1].getInnerValue() == 0 && fallRightChance > 70) {
//                            swapTwoCells(matrix[i][j], matrix[i + 1][j + 1]);
//                        }
//                    }
//                }
//            }
//        } catch (IndexOutOfBoundsException e) {
//
//        }
//    }

    public void swapTwoCells(Cell a, Cell b) {
        int aX = a.getElement().getX();
        int aY = a.getElement().getY();

        int bX = b.getElement().getX();
        int bY = b.getElement().getY();

        Element element = a.getElement();
        a.setElement(b.getElement());
        a.getElement().setX(bX);
        a.getElement().setY(bY);

        b.setElement(element);
        b.getElement().setX(aX);
        b.getElement().setY(aY);

        matrix[aX][aY].getElement().setX(aX);
        matrix[aX][aY].getElement().setY(aY);


        matrix[bX][bY].getElement().setX(bX);
        matrix[bX][bY].getElement().setY(bY);
    }
//

    private List<Cell> cellsWindow(Cell cell, int range){
        List<Cell> cellsWindow = new ArrayList<>();
//        System.out.println("CurrentCell x=" + cell.getElement().getX() + " y = " + cell.getElement().getY());
//        try {
//            System.out.println("cell on top = " + matrix[cell.getElement().getX() + 1][cell.getElement().getY()]);
//        }
//        catch (Exception e){
//            return cellsWindow;
//        }
//        if (1 == 1) {return cellsWindow; }

        for (int i = cell.getElement().getX() - range; i < cell.getElement().getX() + range + 1; i++){
            for (int j = cell.getElement().getY() - range; j < cell.getElement().getY() + range + 1; j++){
                if (i > 0 && j > 0 && i < MATRIX_SIZE && j < MATRIX_SIZE){
                    cellsWindow.add(matrix[i][j]);

                    if (matrix[i][j].getElement().getX() != i || matrix[i][j].getElement().getY() != j){
                        System.out.println(matrix[i][j] + " i = " + i + " j = " + j);
                    }
                }
            }
        }
//        System.out.println("Cells = " + cellsWindow);
//        System.out.println("windowSize = " + cellsWindow.size());
        return cellsWindow;
    }
}