package com.chess.engine.player;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.pieces.King;
import com.chess.engine.pieces.Piece;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class Player {

    //To keep track
    protected final Board board;
    protected final King playerKing;
    protected final Collection<Move> legalMoves;
    private final boolean isInCheck;

    //To declare all features of player
    Player(final Board board,
           final Collection<Move> legalMoves,
           final Collection<Move> opponentMoves){
        this.board = board;
        this.playerKing = establishKing();
        this.legalMoves = ImmutableList.copyOf(Iterables.concat(legalMoves, calculateKingCastles(legalMoves, opponentMoves)));
        this.isInCheck = !Player.calculateAttacksOnTile(this.playerKing.getPiecePosition(), opponentMoves).isEmpty();
    }

    //To get player's king
    public King getPlayerKing() {
        return this.playerKing;
    }

    public Collection<Move> getLegalMoves(){
        return this.legalMoves;
    }

    //To identify if king is checked(if this list is not empty, then king is in check)
    protected static Collection<Move> calculateAttacksOnTile(int piecePosition, Collection<Move> moves) {
        final List<Move> attackMoves = new ArrayList<>();
        for(final Move move : moves){
            if(piecePosition == move.getDestinationCoordinate()){
                attackMoves.add(move);
            }
        }
        return ImmutableList.copyOf(attackMoves);
    }

    //To track a player king
    private King establishKing() throws RuntimeException {
        for(final Piece piece: getActivePieces()){
            if(piece.getPieceType().isKing()){
                return (King) piece;
            }
        }
        throw new RuntimeException("Should not reach here! Not a valid board");
    }

    //to check is move legal or not
    public boolean isMoveLegal(final Move move){
        return this.legalMoves.contains(move);
    }

    //to check is king under Check or not
    public boolean isInCheck() {
        return this.isInCheck;
    }

    //to check is king under CheckMate or not
    public boolean isInCheckMate() {
        return this.isInCheck && !hasEscapedMoves();
    }

    //to check is king under StaleMate or not
    public boolean isInStaleMate() {
        return !this.isInCheck && !hasEscapedMoves();
    }

    //TODO ?
    //To check if king has at least one move
    protected boolean hasEscapedMoves() {
        for (final Move move : this.legalMoves) {
            final MoveTransition transition = makeMove(move);
            if (transition.getMoveStatus().isDone()) {
                return true;
            }
        }
        return false;
    }

    //to check is king castled or not
    public boolean isCastled(){
        return false;
    }

    //To transit moves from one board to another
    public MoveTransition makeMove(final Move move){

        if(!isMoveLegal(move)){
            return new MoveTransition(this.board, move, MoveStatus.ILLEGAL_MOVE);
        }

        final Board transitionBoard = move.execute();

        final Collection<Move> kingAttacks = Player.calculateAttacksOnTile(transitionBoard.currentPlayer().getOpponent().getPlayerKing().getPiecePosition(),
                                                    transitionBoard.currentPlayer().getLegalMoves());
        if(!kingAttacks.isEmpty()){
            return new MoveTransition(this.board, move, MoveStatus.LEAVES_PLAYER_IN_CHECK);
        }
        return new MoveTransition(transitionBoard, move, MoveStatus.DONE);
    }

    public abstract Collection<Piece> getActivePieces();
    public abstract Alliance getAlliance();
    public abstract Player getOpponent();
    protected abstract Collection<Move> calculateKingCastles(Collection<Move>playerLegals, Collection<Move> opponentsLegals);
}
