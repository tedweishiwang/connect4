package main.java.connect4;

import java.util.Random;
import java.util.Scanner;

public class ConnectFour {

    public static void startGame(Board board, Color firstPlayer) {
        Color currentPlayer = firstPlayer;

        while (board.status == Status.IN_PROGRESS) {
            System.out.println(currentPlayer + " turn to play, please input column number (0 - " + (board.columnCount - 1) + "):");
            Scanner scannedObj = new Scanner(System.in);
            int column = scannedObj.nextInt();
            board.put(new Piece(currentPlayer), column);
            board.showBoardStatus();
            currentPlayer = currentPlayer == Color.RED ? Color.YELLOW : Color.RED;
        }
    }

    public static void playWithDumbAI(Board board, Color firstPlayer) {
        Color opponentColor = firstPlayer == Color.RED ? Color.YELLOW : Color.RED;
        Random random = new Random();
        while (board.status == Status.IN_PROGRESS) {
            System.out.println(firstPlayer + " turn to play, please input column number (0 - " + (board.columnCount - 1) + "):");

            Scanner scannedObj = new Scanner(System.in);
            int column = scannedObj.nextInt();

            board.put(new Piece(firstPlayer), column);
            board.showBoardStatus();

            if (board.status == Status.COMPLETED) break;

            board.put(new Piece(opponentColor), random.nextInt(board.columnCount));
            board.showBoardStatus();
        }
    }

    public static void main(String[] args) {
        Board board = new Board(6, 7);
        board.showBoardStatus();

        startGame(board, Color.RED);
    }

}

