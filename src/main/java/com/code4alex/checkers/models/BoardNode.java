package com.code4alex.checkers.models;

import java.util.ArrayList;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder @Getter @Setter
public class BoardNode {
    public Board board;
    public int score;
    public int exhaustion;
    public boolean pruned;
    public ArrayList<BoardNode> treeNodes;

    synchronized public void addNode(BoardNode boardNode) {
        treeNodes.add(boardNode);
    }
}
