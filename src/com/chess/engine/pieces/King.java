package com.chess.engine.pieces;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtil;
import com.chess.engine.board.Move;
import com.chess.engine.board.Move.MajorMove;
import com.chess.engine.board.Tile;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.chess.engine.board.Move.AttackMove;

public class King extends Piece {

    private static final int[] CANDIDATE_MOVE_COORDINATES = {-9, -8, -7, -1, 1, 7, 8 , 9};

    public King(final Alliance pieceAlliance,
                final int piecePosition) {
        super(PieceType.KING, piecePosition, pieceAlliance, true);
    }
    public King(final Alliance pieceAlliance,
                  final int piecePosition,
                  final boolean isFirstMove) {
        super(PieceType.KING, piecePosition, pieceAlliance, isFirstMove);
    }

    @Override
    public Collection<Move> calculateLegalMoves(Board board) {

        final List<Move>legalMoves = new ArrayList<>();

        for (final int currentCandidateOffSet: CANDIDATE_MOVE_COORDINATES){
           final int candidateDestinationCoordinate = this.piecePosition + currentCandidateOffSet;

           if(isFirstColumnExclusion(this.piecePosition, currentCandidateOffSet) ||
              isEighthColumnExclusion(this.piecePosition, currentCandidateOffSet)){
               continue;
           }

           if(BoardUtil.isValidTileCoordinate(candidateDestinationCoordinate)){
               final Tile candidateDestinationTile = board.getTile(candidateDestinationCoordinate);//Tile in which a piece move
               if(!candidateDestinationTile.isTileOccupied()){
                   legalMoves.add(new MajorMove(board, this, candidateDestinationCoordinate));
               } else {
                   final Piece pieceAtDestination = candidateDestinationTile.getPiece();// to get piece that is on a destination tile
                   final Alliance pieceAlliance = pieceAtDestination.getPieceAlliance();//to understand white or black piece is on a tile
                   if(this.pieceAlliance != pieceAlliance){
                       legalMoves.add(new AttackMove(board, this, candidateDestinationCoordinate,
                               pieceAtDestination));
                   }
               }
           }

        }

        return ImmutableList.copyOf(legalMoves);
    }

    @Override
    public King movePiece(final Move move) {
        return new King(move.getMovedPiece().getPieceAlliance(), move.getDestinationCoordinate());
    }

    @Override
    public String toString() {
        return PieceType.KING.toString();
    }

    private static boolean isFirstColumnExclusion(final int currentPosition, final int candidateOffSet) {
        return BoardUtil.FIRST_COLUMN[currentPosition] && (candidateOffSet == -1 || candidateOffSet == -9 ||
                candidateOffSet == 7);
    }

    private static boolean isEighthColumnExclusion (final int currentPosition, final int candidateOffSet){
        return BoardUtil.SECOND_COLUMN[currentPosition] && (candidateOffSet == -7 || candidateOffSet == 1 ||
                candidateOffSet == 9);
    }
}
