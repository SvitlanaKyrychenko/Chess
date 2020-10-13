package com.chess.engine.pieces;

import com.chess.engine.Alliance;
import com.chess.engine.board.*;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.chess.engine.board.Move.AttackMove;
import static com.chess.engine.board.Move.MajorMove;

public class Knight extends Piece {

    private final static int[] CANDIDATE_MOVE_COORDINATES = {-17, -15, -10, -6, 6, 10, 15, 17};
    //to declare all needed information about a Knight
    public Knight(final Alliance pieceAlliance,
                  final int piecePosition) {
        super(PieceType.KNIGHT, piecePosition, pieceAlliance, true);
    }

    public Knight(final Alliance pieceAlliance,
                  final int piecePosition,
                  final boolean isFirstMove) {
        super(PieceType.KNIGHT, piecePosition, pieceAlliance, isFirstMove);
    }

    @Override
    public Collection<Move>calculateLegalMoves(final Board board) {
        final List<Move> legalMoves = new ArrayList<>();

        if(BoardUtil.FIRST_COLUMN[piecePosition] || BoardUtil.EIGHTH_COLUMN[piecePosition] ){
            MoveTrajectory.rock(legalMoves, board, this.piecePosition, this.pieceAlliance, this);
        }else if(BoardUtil.THIRD_COLUMN[piecePosition] || BoardUtil.SIXTH_COLUMN[piecePosition] ){
            MoveTrajectory.bishop(legalMoves, board, this.piecePosition, this.pieceAlliance, this);
        }else {
            MoveTrajectory.knight(legalMoves, board, this.piecePosition, this.pieceAlliance, this);
        }
        return ImmutableList.copyOf(legalMoves);
    }

    @Override
    public Knight movePiece(final Move move) {
        return new Knight(move.getMovedPiece().getPieceAlliance(), move.getDestinationCoordinate());
    }

    @Override
    public String toString() {
        return PieceType.KNIGHT.toString();
    }

}
