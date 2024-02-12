package com.code4alex.checkers.models;

import java.util.ArrayList;

import com.code4alex.checkers.utils.BoardTransformer;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.val;

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


    @Override
    public String toString() {
        val boardTransformer = new BoardTransformer();
        Piece sparseBoard[][] = boardTransformer.denseToSparseBoard(this);
        char emptySquare = ' ';
        char whiteSquare = 'W';
        char blackSquare = 'B';
        char whiteKingSquare = 'U';
        char blackKingSquare = 'V';

        StringBuilder retString = new StringBuilder();
        for(Piece i[]: sparseBoard) {
            for(val j: i) {
                retString.append(switch(j) {
                    case Piece.BLACK -> blackSquare;
                    case Piece.WHITE -> whiteSquare;
                    case Piece.BLACK_KING -> blackKingSquare;
                    case Piece.WHITE_KING -> whiteKingSquare;
                    case null -> emptySquare;
                });
            }
            retString.append('\n');
        }

        return retString.toString();
    }
}
