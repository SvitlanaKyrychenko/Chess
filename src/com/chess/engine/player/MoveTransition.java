package com.chess.engine.player;

import com.chess.engine.board.Board;
import com.chess.engine.board.Move;

public class MoveTransition {

    private final Board transitionBoard;
    private final Move  move;
    private final MoveStatus moveStatus; //whether we can o can not make a move

    //to declare all features of transition
    public MoveTransition(final Board transitionBoard,
                          final Move move,
                          final MoveStatus moveStatus){
        this.transitionBoard = transitionBoard;
        this.move = move;
        this.moveStatus = moveStatus;
    }

    //To state a move status
    public MoveStatus getMoveStatus() {
        return this.moveStatus;
    }
    public Board getTransitionBoard(){
        return this.transitionBoard;
    }
}
