package com.chess.engine.pieces;

import com.chess.engine.Alliance;
import com.chess.engine.board.*;
import com.chess.engine.board.Move.AttackMove;
import com.chess.engine.board.Move.MajorMove;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Rook extends Piece {
    private static final int[] CANDIDATE_MOVE_VECTOR_COORDINATES = {-8, -1, 1, 8};
    public Rook(final Alliance pieceAlliance,
                final int piecePosition) {
        super(PieceType.ROOK, piecePosition, pieceAlliance, true);
    }

    public Rook(final Alliance pieceAlliance,
                final int piecePosition,
                final boolean isFirstMove) {
        super(PieceType.ROOK, piecePosition, pieceAlliance, isFirstMove);
    }

    @Override
    public Collection<Move> calculateLegalMoves(Board board) {
        final List<Move> legalMoves = new ArrayList<>();
        if(BoardUtil.SECOND_COLUMN[piecePosition] || BoardUtil.SEVENTH_COLUMN[piecePosition] ){
            MoveTrajectory.knight(legalMoves, board, this.piecePosition, this.pieceAlliance, this);
        }else if(BoardUtil.THIRD_COLUMN[piecePosition] || BoardUtil.SIXTH_COLUMN[piecePosition] ){
            MoveTrajectory.bishop(legalMoves, board, this.piecePosition, this.pieceAlliance, this);
        }else {
            MoveTrajectory.rock(legalMoves, board, this.piecePosition, this.pieceAlliance, this);
        }
        return ImmutableList.copyOf(legalMoves);
    }

    @Override
    public Rook movePiece(final Move move) {
        return new Rook(move.getMovedPiece().getPieceAlliance(), move.getDestinationCoordinate());
    }

    @Override
    public String toString() {
        return PieceType.ROOK.toString();
    }
    private static boolean isFirstColumnExclusion(final int currentPosition, final int candidateOffSet){
        return BoardUtil.FIRST_COLUMN[currentPosition] && (candidateOffSet == -1);
    }
    private static boolean isEighthColumnExclusion(final int currentPosition, final int candidateOffSet){
        return BoardUtil.SEVENTH_COLUMN[currentPosition] && (candidateOffSet == 1);
    }
}
