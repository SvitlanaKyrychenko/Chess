package com.chess.engine.board;

import com.chess.engine.Alliance;
import com.chess.engine.pieces.Piece;

import java.util.List;

public class MoveTrajectory {
    private static final int[] CANDIDATE_MOVE_QUEEN_VECTOR_COORDINATES = {-9, -8, -7, -1, 1, 7, 8, 9};
    private static final int[] CANDIDATE_MOVE_KNIGHT_COORDINATES = {-17, -15, -10, -6, 6, 10, 15, 17};
    private static final int[] CANDIDATE_MOVE_BISHOP_VECTOR_COORDINATES = {-9, -7, 7, 9};
    private static final int[] CANDIDATE_MOVE_ROCK_VECTOR_COORDINATES = {-8, -1, 1, 8};

    public static List<Move> bishop(List<Move> legalMoves, Board board, int piecePosition, Alliance thisPieceAlliance, Piece piece) {
        for(final int candidateCoordinateOffSet: CANDIDATE_MOVE_BISHOP_VECTOR_COORDINATES){
            int candidateDestinationCoordinate = piecePosition;

            while (BoardUtil.isValidTileCoordinate(candidateDestinationCoordinate)){

                if(isFirstColumnExclusionBishop(candidateDestinationCoordinate, candidateCoordinateOffSet) ||
                        isEighthColumnExclusionBishop(candidateDestinationCoordinate, candidateCoordinateOffSet)){
                    break;
                }
                candidateDestinationCoordinate += candidateCoordinateOffSet;
                if (BoardUtil.isValidTileCoordinate(candidateDestinationCoordinate)){
                    final Tile candidateDestinationTile = board.getTile(candidateDestinationCoordinate);
                    if(!candidateDestinationTile.isTileOccupied()){
                        legalMoves.add(new Move.MajorMove(board, piece, candidateDestinationCoordinate));
                    } else {
                        final Piece pieceAtDestination = candidateDestinationTile.getPiece();
                        final Alliance pieceAlliance = pieceAtDestination.getPieceAlliance();
                        if(thisPieceAlliance != pieceAlliance){
                            legalMoves.add(new Move.AttackMove(board, piece, candidateDestinationCoordinate,
                                    pieceAtDestination));
                        }
                        break;
                    }
                }
            }
        }
        return legalMoves;
    }

    public static List<Move> knight(List<Move> legalMoves, Board board, int piecePosition, Alliance thisPieceAlliance, Piece piece) {
        for (final int currentCandidateOffSet : CANDIDATE_MOVE_KNIGHT_COORDINATES) {
            final int candidateDestinationCoordinate = piecePosition + currentCandidateOffSet;
            if (BoardUtil.isValidTileCoordinate(candidateDestinationCoordinate)) {
                if (isFirstColumnExclusionKnight(piecePosition, currentCandidateOffSet) ||
                        isSecondColumnExclusionKnight(piecePosition, currentCandidateOffSet) ||
                        isSeventhColumnExclusionKnight(piecePosition, currentCandidateOffSet) ||
                        isEighthColumnExclusionKnight(piecePosition, currentCandidateOffSet)) {
                    continue;
                }
                final Tile candidateDestinationTile = board.getTile(candidateDestinationCoordinate);
                if (!candidateDestinationTile.isTileOccupied()) {
                    legalMoves.add(new Move.MajorMove(board, piece, candidateDestinationCoordinate));
                } else {
                    final Piece pieceAtDestination = candidateDestinationTile.getPiece();
                    final Alliance pieceAlliance = pieceAtDestination.getPieceAlliance();
                    if (thisPieceAlliance != pieceAlliance) {
                        legalMoves.add(new Move.AttackMove(board, piece, candidateDestinationCoordinate,
                                pieceAtDestination));
                    }
                }
            }
        }
        return legalMoves;
    }

    public static List<Move> queen(List<Move> legalMoves, Board board, int piecePosition, Alliance thisPieceAlliance, Piece piece) {
        for (final int candidateCoordinateOffSet : CANDIDATE_MOVE_QUEEN_VECTOR_COORDINATES) {
            int candidateDestinationCoordinate = piecePosition;

            while (BoardUtil.isValidTileCoordinate(candidateDestinationCoordinate)) {
                if (isFirstColumnExclusionQueen(candidateDestinationCoordinate, candidateCoordinateOffSet) ||
                        isEighthColumnExclusionQueen(candidateDestinationCoordinate, candidateCoordinateOffSet)) {
                    break;
                }

                candidateDestinationCoordinate += candidateCoordinateOffSet;
                if (BoardUtil.isValidTileCoordinate(candidateDestinationCoordinate)) {
                    final Tile candidateDestinationTile = board.getTile(candidateDestinationCoordinate);
                    if (!candidateDestinationTile.isTileOccupied()) {
                        legalMoves.add(new Move.MajorMove(board, piece, candidateDestinationCoordinate));
                    } else {
                        final Piece pieceAtDestination = candidateDestinationTile.getPiece();
                        final Alliance pieceAlliance = pieceAtDestination.getPieceAlliance();
                        if (thisPieceAlliance != pieceAlliance) {
                            legalMoves.add(new Move.AttackMove(board, piece, candidateDestinationCoordinate,
                                    pieceAtDestination));
                        }
                        break;
                    }
                }
            }
        }
        return legalMoves;
    }

    public static List<Move> rock(List<Move> legalMoves, Board board, int piecePosition, Alliance thisPieceAlliance, Piece piece) {
        for(final int candidateCoordinateOffSet: CANDIDATE_MOVE_ROCK_VECTOR_COORDINATES){
            int candidateDestinationCoordinate = piecePosition;
            while (BoardUtil.isValidTileCoordinate(candidateDestinationCoordinate)){
                if(isFirstColumnExclusionRock(candidateDestinationCoordinate, candidateCoordinateOffSet) ||
                        isEighthColumnExclusionRock(candidateDestinationCoordinate, candidateCoordinateOffSet)){
                    break;
                }
                candidateDestinationCoordinate += candidateCoordinateOffSet;
                if (BoardUtil.isValidTileCoordinate(candidateDestinationCoordinate)){
                    final Tile candidateDestinationTile = board.getTile(candidateDestinationCoordinate);
                    if(!candidateDestinationTile.isTileOccupied()){
                        legalMoves.add(new Move.MajorMove(board, piece, candidateDestinationCoordinate));
                    } else {
                        final Piece pieceAtDestination = candidateDestinationTile.getPiece();
                        final Alliance pieceAlliance = pieceAtDestination.getPieceAlliance();
                        if(thisPieceAlliance != pieceAlliance){
                            legalMoves.add(new Move.AttackMove(board, piece, candidateDestinationCoordinate,
                                    pieceAtDestination));
                        }
                        break;
                    }
                }
            }
        }
        return legalMoves;
    }

    private static boolean isFirstColumnExclusionQueen(final int currentPosition, final int candidateOffSet) {
        return BoardUtil.FIRST_COLUMN[currentPosition] && (candidateOffSet == -9 || candidateOffSet == 7 ||
                candidateOffSet == -1 || candidateOffSet == -7 || candidateOffSet == 9);
    }

    private static boolean isEighthColumnExclusionQueen(final int currentPosition, final int candidateOffSet) {
        return BoardUtil.EIGHTH_COLUMN[currentPosition] && (candidateOffSet == -9 || candidateOffSet == 7 ||
                candidateOffSet == 1 || candidateOffSet == -7 || candidateOffSet == 9);
    }

    private static boolean isFirstColumnExclusionBishop(final int currentPosition, final int candidateOffSet) {
        return BoardUtil.FIRST_COLUMN[currentPosition] && (candidateOffSet == -9 || candidateOffSet == 7);
    }

    private static boolean isEighthColumnExclusionBishop(final int currentPosition, final int candidateOffSet) {
        return BoardUtil.EIGHTH_COLUMN[currentPosition] && (candidateOffSet == 9 || candidateOffSet == -7);
    }


    private static boolean isFirstColumnExclusionKnight(final int currentPosition, final int candidateOffSet) {
        return BoardUtil.FIRST_COLUMN[currentPosition] && (candidateOffSet == -17 || candidateOffSet == -10 ||
                candidateOffSet == 6 || candidateOffSet == 15);
    }

    private static boolean isSecondColumnExclusionKnight(final int currentPosition, final int candidateOffSet) {
        return BoardUtil.SECOND_COLUMN[currentPosition] && (candidateOffSet == -10 || candidateOffSet == 6);
    }

    private static boolean isSeventhColumnExclusionKnight(final int currentPosition, final int candidateOffSet) {
        return BoardUtil.SEVENTH_COLUMN[currentPosition] && (candidateOffSet == 10 || candidateOffSet == -6);
    }

    private static boolean isEighthColumnExclusionKnight(final int currentPosition, final int candidateOffSet) {
        return BoardUtil.EIGHTH_COLUMN[currentPosition] && (candidateOffSet == 17 || candidateOffSet == 10 ||
                candidateOffSet == -6 || candidateOffSet == -15);
    }

    private static boolean isFirstColumnExclusionRock(final int currentPosition, final int candidateOffSet){
        return BoardUtil.FIRST_COLUMN[currentPosition] && (candidateOffSet == -1);
    }
    private static boolean isEighthColumnExclusionRock(final int currentPosition, final int candidateOffSet){
        return BoardUtil.EIGHTH_COLUMN[currentPosition] && (candidateOffSet == 1);
    }
}
