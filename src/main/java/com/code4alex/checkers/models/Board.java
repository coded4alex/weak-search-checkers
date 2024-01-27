package com.code4alex.checkers.models;

import java.util.ArrayList;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode
public class Board {

    @Getter @Setter
    private ArrayList<Square> squares = new ArrayList<>();

    public static Board newBoard() {
        Board board = new Board();

        board.fillLine(Piece.BLACK, 0, 0);
        board.fillLine(Piece.BLACK, 1, 1);
        board.fillLine(Piece.BLACK, 2, 0);
        board.fillLine(Piece.WHITE, 5, 1);
        board.fillLine(Piece.WHITE, 6, 0);
        board.fillLine(Piece.WHITE, 7, 1);

        return board;
    }

    private void fillLine(Piece piece, int verticalPosition, int offset) {
        for (int i = 0; i < 4; i++) {
            squares.add(new Square(verticalPosition, i * 2 + offset, piece));
        }
    }
}
