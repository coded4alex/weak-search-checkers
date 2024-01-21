package com.code4alex.checkers.models;

public enum Piece {
    WHITE, BLACK, WHITE_KING, BLACK_KING;

    public boolean isOpposite(Piece piece) {
        if ((this == WHITE || this == WHITE_KING) && (piece == BLACK || piece == BLACK_KING))
            return true;
        else if ((piece == WHITE || piece == WHITE_KING) && (this == BLACK || this == BLACK_KING))
            return true;
        return false;
    }
}
