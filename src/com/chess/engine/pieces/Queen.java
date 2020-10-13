package com.chess.engine.pieces;

import com.chess.engine.Alliance;
import com.chess.engine.board.*;
import com.chess.engine.board.Move.AttackMove;
import com.chess.engine.board.Move.MajorMove;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Queen extends Piece {

    public Queen(final Alliance pieceAlliance,
                 final int piecePosition) {
        super(PieceType.QUEEN, piecePosition, pieceAlliance, true);
    }
    public Queen(final Alliance pieceAlliance,
                  final int piecePosition,
                  final boolean isFirstMove) {
        super(PieceType.QUEEN, piecePosition, pieceAlliance, isFirstMove);
    }

    @Override
    public Collection<Move> calculateLegalMoves(Board board) {

        final List<Move> legalMoves = new ArrayList<>();

        if (BoardUtil.THIRD_COLUMN[piecePosition] || (BoardUtil.SIXTH_COLUMN[piecePosition])) {
            MoveTrajectory.bishop(legalMoves, board, this.piecePosition, this.pieceAlliance, this);
        }else if(BoardUtil.SECOND_COLUMN[piecePosition] || BoardUtil.SEVENTH_COLUMN[piecePosition]){
            MoveTrajectory.knight(legalMoves, board, this.piecePosition, this.pieceAlliance, this);

        }else {
            MoveTrajectory.queen(legalMoves, board, this.piecePosition, this.pieceAlliance, this);
        }
        return ImmutableList.copyOf(legalMoves);
    }

    @Override
    public Queen movePiece(final Move move) {
        return new Queen(move.getMovedPiece().getPieceAlliance(), move.getDestinationCoordinate());
    }

    @Override
    public String toString() {
        return PieceType.QUEEN.toString();
    }
}
