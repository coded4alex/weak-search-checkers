package com.code4alex.checkers.services;

import org.springframework.stereotype.Service;

import com.code4alex.checkers.models.Board;
import com.code4alex.checkers.models.Piece;
import com.code4alex.checkers.models.Square;

import lombok.val;

@Service
public class BoardTransformerService {
    public Piece[][] sparseToDenseBoard(Board board) {
        Piece denseBoard[][] = new Piece[8][8];
        for (val square : board.getSquares()) {
            denseBoard[square.getVerticalPosition()][square.getHorizontalPosition()] = square.getPiece();
        }

        return denseBoard;
    }

    public Board denseToSparseBoard(Piece denseBoard[][]) {
        val sparseBoard = new Board();
        val sparseBoardArray = sparseBoard.getSquares();

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (denseBoard[i][j] != null) {
                    sparseBoardArray.add(Square.builder()
                            .horizontalPosition(i)
                            .verticalPosition(j)
                            .piece(denseBoard[i][j])
                            .build());
                }
            }
        }
        return sparseBoard;
    }

    public Piece[][] copySparseBoard(Piece sparseBoard[][]) {
        Piece tempBoard[][] = new Piece[8][8];
        for (int i = 0; i < 8; i++) {
            System.arraycopy(sparseBoard[i], 0, tempBoard[i], 0, 8);
        }

        return tempBoard;
    }
}
