package com.code4alex.checkers.utils;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.code4alex.checkers.models.Board;
import com.code4alex.checkers.models.Piece;
import com.code4alex.checkers.models.Square;

import junit.framework.Assert;
import lombok.val;

@SpringBootTest
public class BoardTransformerTest {
    @Test
    public void testSparseToDenseBoard() {
        val boardTransformer = new BoardTransformer();
        val board = new Board();

        board.getSquares().add(Square.builder()
            .horizontalPosition(0)
            .verticalPosition(0)
            .piece(Piece.BLACK)
            .build());

        board.getSquares().add(Square.builder()
            .horizontalPosition(2)
            .verticalPosition(2)
            .piece(Piece.WHITE)
            .build());

        Piece sparseBoard[][] = boardTransformer.denseToSparseBoard(board);
        Assert.assertEquals(sparseBoard[0][0], Piece.BLACK);
        Assert.assertEquals(sparseBoard[2][2], Piece.WHITE);
    }

    @Test
    public void testDenseToSparseBoard() {
        Piece sparseBoard[][] = new Piece[8][8];
        sparseBoard[0][0] = Piece.BLACK;
        sparseBoard[2][2] = Piece.WHITE;

        val boardTransformer = new BoardTransformer();
        val board = boardTransformer.sparseToDenseBoard(sparseBoard);

        Assert.assertEquals(Square.builder()
            .horizontalPosition(0)
            .verticalPosition(0)
            .piece(Piece.BLACK)
            .build(), 
            board.getSquares().get(0));

        Assert.assertEquals(Square.builder()
            .horizontalPosition(2)
            .verticalPosition(2)
            .piece(Piece.WHITE)
            .build(), 
            board.getSquares().get(1));
    }
}
