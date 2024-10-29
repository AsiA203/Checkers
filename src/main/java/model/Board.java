package model;

import Enums.Color;

public class Board {
    private Square [][] board;

    public Board() {
        this.board = new Square[8][8];
        initializeBoard();
    }

    public Square[][] getBoard(){
        return board;
    }

    public void initializeBoard() {
        //initialize board squares
        for (int r = 0; r < 8; r++){
            for (int c = 0; c < 8; c++){
                board[r][c] = new Square();
            }
        }

        // Initialize black pieces
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 8; c++) {
                if ((r + c) % 2 != 0) {
                    board[r][c].setStone(new Stone(Color.Black));
                }
            }
        }

        // Initialize white pieces
        for (int r = 5; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                if ((r + c) % 2 != 0) {
                    board[r][c].setStone(new Stone(Color.White));
                }
            }
        }
    }

    public void placeStone(int row, int column, Color color) throws Exception {
        if ((row < 8 && row >= 0) && (column < 8 && column >= 0)){
            board[row][column].setStone(new Stone(color));
        } else throw new Exception("Invalid coordinates for new stone placement");
    }

    public void removeStone(int row, int column) throws Exception{
        if ((row < 8 && row >= 0) && (column < 8 && column >= 0)){
            board[row][column].removeStone();
        } else throw new Exception("Invalid coordinates of a stone to for removal");
    }

    public void makeStoneKing(int row, int column) throws Exception{
            board[row][column].getSquare().reachedTheEnd();
    }

    public Stone geStone(int row, int column) throws Exception{
        if ((row < 8 && row >= 0) && (column < 8 && column >= 0)){
            return board[row][column].getSquare();
        } else throw new Exception("Invalid coordinates of a stone to for removal");
    }
    public void printBoard() {
        System.out.println("  A B C D E F G H ");
        for (int row = 0; row < 8; row++) {
            System.out.print(8 - row + " "); // Row label

            for (int col = 0; col < 8; col++) {
                if (board[row][col].getSquare() == null) {
                    System.out.print("  "); // Empty space
                } else if (board[row][col].getSquare().getColor() == Color.Black) {
                    System.out.print("B "); // Black piece
                } else if (board[row][col].getSquare().getColor() == Color.White) {
                    System.out.print("W "); // White piece
                }
            }
            System.out.println(" " + (8 - row)); // Row label on the right side
        }
        System.out.println("  A B C D E F G H "); // Column labels at the bottom
    }
}