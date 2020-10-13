package com.chess.engine;

import com.chess.engine.player.BlackPlayer;
import com.chess.engine.player.Player;
import com.chess.engine.player.WhitePlayer;

public enum Alliance {
    WHITE{
        @Override
        public int getDirection() {
            return -1;
        }

        @Override
        public boolean isWhite() {
            return true;
        }

        @Override
        public boolean isBlack() {
            return false;
        }

        @Override
        public Player choosePlayer(final WhitePlayer whitePlayer,
                                   final BlackPlayer blackPlayer) {
            return whitePlayer;
        }
    },
    BLACK {
        @Override
        public int getDirection() {
            return 1;
        }

        @Override
        public boolean isWhite() {
            return false;
        }

        @Override
        public boolean isBlack() {
            return true;
        }

        @Override
        public Player choosePlayer(final WhitePlayer whitePlayer,
                                   final BlackPlayer blackPlayer) {
            return blackPlayer;
        }
    };
    public abstract int getDirection();//To divide direction of white and black pieces
    public abstract boolean isWhite(); //to make a white grope of pieces
    public abstract boolean isBlack();//to make a black grope of pieces
    public abstract Player choosePlayer(WhitePlayer whitePlayer, BlackPlayer blackPlayer);//to choose player
}
