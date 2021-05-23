package main.java.connect4;

import java.util.Arrays;

public class Board {
    int rowCount;
    int columnCount;
    Piece[][] grid;
    int[] counter;
    Status status;

    public Board(int rowCount, int columnCount) {
        this.rowCount = rowCount;
        this.columnCount = columnCount;
        this.grid = new Piece[rowCount][columnCount];
        this.counter = new int[columnCount];
        Arrays.fill(this.counter, rowCount);
        this.status = Status.IN_PROGRESS;
    }

    public void put(Piece piece, int column) {
        if (column < 0 || column >= columnCount) {
            System.out.println("Unacceptable column number!");
        }

        int row = counter[column] - 1;

        if (row < 0 || row >= rowCount) {
            System.out.println("Unacceptable row number!");
        }

        grid[row][column] = piece;
        counter[column]--;
        System.out.println("Put " + piece.color + " in column " + column + ".");

        update(row, column, piece);
    }

    public void showBoardStatus() {
        System.out.println();
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                if (grid[i][j] == null) System.out.print("X ");
                else if (grid[i][j].color == Color.RED) System.out.print("R ");
                else System.out.print("Y ");
            }
            System.out.println();
        }
        System.out.println();
    }

    private void update(int rowNumber, int columnNumber, Piece piece) {
        checkHorizontal(rowNumber, piece);
        checkVertical(columnNumber, piece);
        checkDiagnal(rowNumber, columnNumber, piece);
        checkBackDiagnal(rowNumber, columnNumber, piece);
        checkTie();
    }

    private void checkHorizontal(int rowNumber, Piece piece) {
        int counter = 0;
        Color oppositeColor = piece.color == Color.RED ? Color.YELLOW : Color.RED;
        for (int j = 0; j < columnCount; j++) {
            if (grid[rowNumber][j] == null || grid[rowNumber][j].color == oppositeColor) {
                counter = 0;
            } else {
                counter++;
            }

            if (counter == 4) {
                System.out.println("Color " + piece.color + " has won!");
                status = Status.COMPLETED;
                return;
            }
        }
    }

    private void checkVertical(int columnCount, Piece piece) {
        int counter = 0;
        Color oppositeColor = piece.color == Color.RED ? Color.YELLOW : Color.RED;
        for (int i = 0; i < rowCount; i++) {
            if (grid[i][columnCount] == null || grid[i][columnCount].color == oppositeColor) {
                counter = 0;
            } else {
                counter++;
            }

            if (counter == 4) {
                System.out.println("Color " + piece.color + " has won!");
                status = Status.COMPLETED;
                return;
            }
        }
    }

    public void checkDiagnal(int rowNumber, int columnNumber, Piece piece) {
        int counter = 0;
        int leftColumn = columnNumber;
        int leftRow = rowNumber;
        int rightColumn = columnNumber + 1;
        int rightRow = rowNumber - 1;

        while(inRange(leftRow, leftColumn) && grid[leftRow][leftColumn] != null && grid[leftRow][leftColumn].color == piece.color) {
            counter++;
            leftColumn--;
            leftRow++;
        }

        while(inRange(rightRow, rightColumn) && rightRow < rowCount && grid[rightRow][rightColumn] != null && grid[rightRow][rightColumn].color == piece.color) {
            counter++;
            rightColumn++;
            rightRow--;
        }

        if (counter >= 4) {
            System.out.println("Color " + piece.color + " has won!");
            status = Status.COMPLETED;
        }

    }

    public void checkBackDiagnal(int rowNumber, int columnNumber, Piece piece) {
        int counter = 0;
        int leftColumn = columnNumber;
        int leftRow = rowNumber;
        int rightColumn = columnNumber + 1;
        int rightRow = rowNumber + 1;

        while(inRange(leftRow, leftColumn) && grid[leftRow][leftColumn] != null && grid[leftRow][leftColumn].color == piece.color) {
            counter++;
            leftColumn--;
            leftRow--;
        }

        while(inRange(rightRow, rightColumn) && rightRow < rowCount && grid[rightRow][rightColumn] != null && grid[rightRow][rightColumn].color == piece.color) {
            counter++;
            rightColumn++;
            rightRow++;
        }

        if (counter >= 4) {
            System.out.println("Color " + piece.color + " has won!");
            status = Status.COMPLETED;
        }
    }

    private boolean checkTie() {
        for (int i = 0; i < counter.length; i++) {
            if (counter[i] != 0) return false;
        }

        System.out.println("The game is tied.");
        status = Status.COMPLETED;
        return true;
    }

    private boolean inRange(int row, int column) {
        if (row >= 0 && row < rowCount && column >= 0 && column < columnCount) return true;
        return false;
    }
}
