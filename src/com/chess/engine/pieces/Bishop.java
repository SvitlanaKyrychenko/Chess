package com.chess.engine.pieces;

import com.chess.engine.Alliance;
import com.chess.engine.board.*;
import com.chess.engine.board.Move.AttackMove;
import com.chess.engine.board.Move.MajorMove;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Bishop extends Piece {

    private static final int[] CANDIDATE_MOVE_VECTOR_COORDINATES = {-9, -7, 7, 9};

    public Bishop(final Alliance pieceAlliance,
                  final int piecePosition) {
        super(PieceType.BISHOP, piecePosition, pieceAlliance, true);
    }

    public Bishop(final Alliance pieceAlliance,
                  final int piecePosition,
                  final boolean isFirstMove) {
        super(PieceType.BISHOP, piecePosition, pieceAlliance, isFirstMove);
    }


    @Override
    public Collection<Move> calculateLegalMoves(Board board) {

        final List<Move> legalMoves = new ArrayList<>();

        if(BoardUtil.FIRST_COLUMN[piecePosition] || BoardUtil.EIGHTH_COLUMN[piecePosition] ){
            MoveTrajectory.rock(legalMoves, board, this.piecePosition, this.pieceAlliance, this);
        }else if(BoardUtil.SECOND_COLUMN[piecePosition] || BoardUtil.SEVENTH_COLUMN[piecePosition] ){
            MoveTrajectory.knight(legalMoves, board, this.piecePosition, this.pieceAlliance, this);
        }else {
            MoveTrajectory.bishop(legalMoves, board, this.piecePosition, this.pieceAlliance, this);
        }
        return ImmutableList.copyOf(legalMoves);
    }

    @Override
    public Bishop movePiece(final Move move) {
        return new Bishop(move.getMovedPiece().getPieceAlliance(), move.getDestinationCoordinate());
    }

    @Override
    public String toString() {
        return PieceType.BISHOP.toString();
    }
}
