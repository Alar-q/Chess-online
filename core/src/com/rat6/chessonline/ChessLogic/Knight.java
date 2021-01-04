package com.rat6.chessonline.ChessLogic;

import com.badlogic.gdx.math.Vector2;
import com.rat6.chessonline.Board;

public class Knight extends FigureLogic {

    public Knight(Board board) {
        super(board);
    }

    @Override
    public boolean canMove(PieceEnum piece, Vector2 position, Vector2 to) {
        int posRow = (int) position.y, posCol = (int) position.x;
        int toRow = (int) to.y, toCol = (int) to.x;

        boolean good = (Math.abs(posRow-toRow)==2 && Math.abs(posCol-toCol)==1) || (Math.abs(posRow-toRow)==1 && Math.abs(posCol-toCol)==2);

        return !isOursUnderAttack(piece, to)  && good;

    }

}
