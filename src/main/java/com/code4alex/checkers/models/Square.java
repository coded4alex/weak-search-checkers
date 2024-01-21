package com.code4alex.checkers.models;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @EqualsAndHashCode @Builder
public class Square {
    private int verticalPosition;
    private int horizontalPosition;
    private Piece piece;
}
