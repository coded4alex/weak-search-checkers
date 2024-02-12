package com.code4alex.checkers.models;

import java.util.ArrayList;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder @Getter @Setter
public class BoardNode {
    private Board board;
    private int score;
    private int moveNumber;
    private boolean pruned;
    @Builder.Default
    private Piece nextPiece = Piece.WHITE;
    private ArrayList<BoardNode> treeNodes;

    synchronized public void addNodes(ArrayList<BoardNode> boardNodes) {
        treeNodes.addAll(boardNodes);
    }
}
