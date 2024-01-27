package com.code4alex.checkers.models;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PieceTest {
    @Test
    public void testIsOpposite() {
        assert Piece.BLACK.isOpposite(Piece.WHITE);
        assert Piece.BLACK.isOpposite(Piece.WHITE_KING);
        assert Piece.WHITE.isOpposite(Piece.BLACK);
        assert Piece.WHITE.isOpposite(Piece.BLACK_KING);

        assert Piece.BLACK_KING.isOpposite(Piece.WHITE) ;
        assert Piece.BLACK_KING.isOpposite(Piece.WHITE_KING);
        assert Piece.WHITE_KING.isOpposite(Piece.BLACK);
        assert Piece.WHITE_KING.isOpposite(Piece.BLACK_KING);

        assert !Piece.BLACK.isOpposite(Piece.BLACK);
        assert !Piece.BLACK.isOpposite(Piece.BLACK_KING);
        assert !Piece.WHITE.isOpposite(Piece.WHITE);
        assert !Piece.WHITE.isOpposite(Piece.WHITE_KING);
    }
}
