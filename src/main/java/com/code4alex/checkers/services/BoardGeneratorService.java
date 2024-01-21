package com.code4alex.checkers.services;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.code4alex.checkers.models.BoardNode;
import com.code4alex.checkers.models.Piece;
import com.code4alex.checkers.models.Square;

import lombok.val;

@Service
public class BoardGeneratorService {
    @Autowired
    BoardTransformerService boardTransformerService;

    public ArrayList<BoardNode> generateFutureBoardNodes(BoardNode boardNode, Piece movePiece) {
        ArrayList<BoardNode> returnList = new ArrayList<>();
        if (boardNode.getExhaustion() <= 0)
            return returnList;

        val board = boardNode.getBoard();
        Piece sparseBoard[][] = boardTransformerService.sparseToDenseBoard(board);

        for (val square : board.getSquares()) {
            if (square.getPiece() == Piece.WHITE) {
                returnList.addAll(movePiece(sparseBoard, square, 1, -1, false));
                returnList.addAll(movePiece(sparseBoard, square, -1, -1, false));
            } else if (square.getPiece() == Piece.BLACK) {
                returnList.addAll(movePiece(sparseBoard, square, 1, 1, false));
                returnList.addAll(movePiece(sparseBoard, square, -1, 1, false));
            } else {
                returnList.addAll(movePiece(sparseBoard, square, 1, -1, false));
                returnList.addAll(movePiece(sparseBoard, square, -1, -1, false));
                returnList.addAll(movePiece(sparseBoard, square, 1, 1, false));
                returnList.addAll(movePiece(sparseBoard, square, -1, 1, false));
            }
        }

        for (val retVal : returnList) {
            retVal.setExhaustion(boardNode.getExhaustion() - 1);
        }

        return returnList;
    }

    private ArrayList<BoardNode> movePiece(Piece sparseBoard[][], Square square, int horizontalMoveModifier,
            int verticalMoveModifier, boolean doubleJump) {
        ArrayList<BoardNode> returnList = new ArrayList<>();
        val hPos = square.getHorizontalPosition();
        val vPos = square.getVerticalPosition();

        if (vPos < 8 && vPos >= 0 && hPos < 8 && hPos >= 0) {
            if (!doubleJump && sparseBoard[vPos + verticalMoveModifier][hPos + horizontalMoveModifier] == null) {
                Piece tempBoard[][] = boardTransformerService.copySparseBoard(sparseBoard);
                tempBoard[vPos + verticalMoveModifier][hPos + horizontalMoveModifier] = square.getPiece();
                tempBoard[vPos][hPos] = null;

                val node = boardTransformerService.denseToSparseBoard(tempBoard);
                val boardNode = BoardNode.builder()
                        .board(node)
                        .pruned(false)
                        .treeNodes(new ArrayList<>())
                        .score(0)
                        .build();
                returnList.add(boardNode);

            } else if (sparseBoard[vPos + verticalMoveModifier][hPos + horizontalMoveModifier]
                    .isOpposite(square.getPiece())
                    && sparseBoard[vPos + verticalMoveModifier * 2][hPos + horizontalMoveModifier * 2] == null) {
                Piece tempBoard[][] = boardTransformerService.copySparseBoard(sparseBoard);
                tempBoard[vPos + verticalMoveModifier * 2][hPos + horizontalMoveModifier * 2] = square.getPiece();
                tempBoard[vPos + verticalMoveModifier][hPos + horizontalMoveModifier] = null;
                tempBoard[vPos][hPos] = null;

                val node = boardTransformerService.denseToSparseBoard(tempBoard);
                val boardNode = BoardNode.builder()
                        .board(node)
                        .pruned(false)
                        .treeNodes(new ArrayList<>())
                        .score(0)
                        .build();
                returnList.add(boardNode);

                if (sparseBoard[vPos + verticalMoveModifier * 3][hPos + horizontalMoveModifier * 3]
                        .isOpposite(square.getPiece())
                        && !doubleJump) {
                    returnList.addAll(
                            movePiece(sparseBoard, square, horizontalMoveModifier, verticalMoveModifier, !doubleJump));
                }
            }
        }
        return returnList;
    }
}
