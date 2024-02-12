package com.code4alex.checkers.utils;

import java.util.ArrayList;

import com.code4alex.checkers.models.BoardNode;
import com.code4alex.checkers.models.Piece;
import com.code4alex.checkers.models.Square;

import lombok.val;


public class BoardGenerator {
    BoardTransformer boardTransformer = new BoardTransformer();

    public ArrayList<BoardNode> generateFutureBoardNodes(BoardNode boardNode, Piece piece) {
        val board = boardNode.getBoard();
        ArrayList<BoardNode> returnList = new ArrayList<>();
        Piece sparseBoard[][] = boardTransformer.denseToSparseBoard(board);

        for (val square : board.getSquares()) {
            if(!square.getPiece().isOpposite(piece)) {
                returnList.addAll(movePiece(sparseBoard, square, false));
            }
        }

        for (val retVal : returnList) {
            retVal.setMoveNumber(boardNode.getMoveNumber() + 1);
            retVal.setNextPiece(piece.getOppositPiece());
        }

        return returnList;
    }

    private ArrayList<BoardNode> movePiece(Piece sparseBoard[][], Square square, boolean doubleJump) {
        ArrayList<BoardNode> returnList = new ArrayList<>();
        if (square.getPiece() != Piece.BLACK) {
            returnList.addAll(movePiece(sparseBoard, square, 1, -1, doubleJump));
            returnList.addAll(movePiece(sparseBoard, square, -1, -1, doubleJump));
        } else {
            returnList.addAll(movePiece(sparseBoard, square, 1, 1, doubleJump));
            returnList.addAll(movePiece(sparseBoard, square, -1, 1, doubleJump));
        }

        return returnList;
    }

    private ArrayList<BoardNode> movePiece(Piece sparseBoard[][], Square square, int horizontalMoveModifier,
            int verticalMoveModifier, boolean doubleJump) {
        ArrayList<BoardNode> returnList = new ArrayList<>();
        val hPos = square.getHorizontalPosition();
        val vPos = square.getVerticalPosition();

        if (checkRange(hPos, vPos)) {
            if (!doubleJump && checkIfSquareIsEmpty(sparseBoard, vPos + verticalMoveModifier, hPos + horizontalMoveModifier)) {
                Piece tempBoard[][] = boardTransformer.copySparseBoard(sparseBoard);
                tempBoard[vPos + verticalMoveModifier][hPos + horizontalMoveModifier] = square.getPiece();
                tempBoard[vPos][hPos] = null;
                createKings(tempBoard);

                val node = boardTransformer.sparseToDenseBoard(tempBoard);
                val boardNode = BoardNode.builder()
                        .board(node)
                        .pruned(false)
                        .treeNodes(new ArrayList<>())
                        .score(0)
                        .build();
                returnList.add(boardNode);

            } else if (checkIfSquareIsOpposite(sparseBoard, square.getPiece(), vPos + verticalMoveModifier, hPos + horizontalMoveModifier)
                    && checkIfSquareIsEmpty(sparseBoard, vPos + verticalMoveModifier * 2, hPos + horizontalMoveModifier * 2)) {
                Piece tempBoard[][] = boardTransformer.copySparseBoard(sparseBoard);
                tempBoard[vPos + verticalMoveModifier * 2][hPos + horizontalMoveModifier * 2] = square.getPiece();
                tempBoard[vPos + verticalMoveModifier][hPos + horizontalMoveModifier] = null;
                tempBoard[vPos][hPos] = null;
                createKings(tempBoard);

                val node = boardTransformer.sparseToDenseBoard(tempBoard);
                val boardNode = BoardNode.builder()
                        .board(node)
                        .pruned(false)
                        .treeNodes(new ArrayList<>())
                        .score(0)
                        .build();
                returnList.add(boardNode);

                if (!doubleJump) {
                    returnList.addAll(movePiece(sparseBoard, square, !doubleJump));
                }
            }
        }
        return returnList;
    }

    private void createKings(Piece sparseBoard[][]) {
        for(int i=0;i<8;i++) {
            if(sparseBoard[0][i] == Piece.WHITE) {
                sparseBoard[0][i] = Piece.WHITE_KING;
            }
            if(sparseBoard[7][i] == Piece.BLACK) {
                sparseBoard[7][i] = Piece.BLACK_KING;
            }
        }
    }

    private boolean checkIfSquareIsOpposite(Piece sparseBoard[][], Piece piece, int vpos, int hpos) {
        if(checkRange(vpos, hpos) && sparseBoard[vpos][hpos] != null && sparseBoard[vpos][hpos].isOpposite(piece)) {
            return true;
        } else {
            return false;
        }
    }

    private boolean checkIfSquareIsEmpty(Piece sparseBoard[][], int vpos, int hpos) {
        if(checkRange(vpos, hpos) && sparseBoard[vpos][hpos] == null) {
            return true;
        } else {
            return false;
        }
    }

    private boolean checkRange(int x, int y) {
        if(x<=7 && x>=0 && y<=7 && y>=0)
            return true;
        return false;
    }
}
