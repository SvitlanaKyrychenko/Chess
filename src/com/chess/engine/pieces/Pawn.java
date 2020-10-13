//TODO many things
package com.chess.engine.pieces;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtil;
import com.chess.engine.board.Move;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.chess.engine.board.Move.MajorMove;

public class Pawn extends Piece{

    private static final int[] CANDIDATE_MOVE_COORDINATES = {7, 8, 9, 16};

    public Pawn(final Alliance pieceAlliance,
                final int piecePosition) {
        super(PieceType.PAWN, piecePosition, pieceAlliance, true);
    }
    public Pawn(final Alliance pieceAlliance,
                  final int piecePosition,
                  final boolean isFirstMove) {
        super(PieceType.PAWN, piecePosition, pieceAlliance, isFirstMove);
    }
    @Override
    public Pawn movePiece(final Move move) {
        return new Pawn(move.getMovedPiece().getPieceAlliance(), move.getDestinationCoordinate());
    }

    @Override
    public Collection<Move> calculateLegalMoves(final Board board) {

        final List<Move> legalMoves = new ArrayList<>();

        for (final int currentCandidateOffSet: CANDIDATE_MOVE_COORDINATES){
            final int candidateDestinationCoordinate = this.piecePosition + (this.pieceAlliance.getDirection()*currentCandidateOffSet);

            if(!BoardUtil.isValidTileCoordinate(candidateDestinationCoordinate)){
                continue;
            }

            if(currentCandidateOffSet == 8 && !board.getTile(candidateDestinationCoordinate).isTileOccupied()){
                legalMoves.add(new MajorMove(board, this, candidateDestinationCoordinate));
            } else if(currentCandidateOffSet == 16 && this.isFirstMove && ((BoardUtil.SEVENTH_ROW[this.piecePosition] &&
                    this.getPieceAlliance().isBlack())|| (BoardUtil.SECOND_ROW[this.piecePosition] &&
                    this.getPieceAlliance().isWhite()))){
                final int behindCandidateDestinationPosition = this.piecePosition + (this.pieceAlliance.getDirection()*8);
                if(!board.getTile(behindCandidateDestinationPosition).isTileOccupied() &&
                   !board.getTile(candidateDestinationCoordinate).isTileOccupied()){
                    legalMoves.add(new Move.PawnJump(board, this, candidateDestinationCoordinate));
                }
            } else if(currentCandidateOffSet == 7 &&
                    !((BoardUtil.EIGHTH_COLUMN[this.piecePosition] && this.pieceAlliance.isWhite()) ||
                    (BoardUtil.FIRST_COLUMN[this.piecePosition]  && this.pieceAlliance.isBlack()))){

                if(board.getTile(candidateDestinationCoordinate).isTileOccupied()){
                    final Piece pieceOnCandidate = board.getTile(candidateDestinationCoordinate).getPiece();
                    if(this.pieceAlliance != pieceOnCandidate.getPieceAlliance()){
                        legalMoves.add(new Move.PawnAttackMove(board, this, candidateDestinationCoordinate, pieceOnCandidate));
                    }
                }

            } else if(currentCandidateOffSet == 9 &&
                    !((BoardUtil.EIGHTH_COLUMN[this.piecePosition] && this.pieceAlliance.isBlack()) ||
                    (BoardUtil.FIRST_COLUMN[this.piecePosition]  && this.pieceAlliance.isWhite()))){

                if(board.getTile(candidateDestinationCoordinate).isTileOccupied()){
                    final Piece pieceOnCandidate = board.getTile(candidateDestinationCoordinate).getPiece();
                    if(this.pieceAlliance != pieceOnCandidate.getPieceAlliance()){
                        legalMoves.add(new Move.PawnAttackMove(board, this, candidateDestinationCoordinate, pieceOnCandidate));
                    }
                }
            }
        }

        return ImmutableList.copyOf(legalMoves);
    }

    @Override
    public String toString() {
        return PieceType.PAWN.toString();
    }
}
