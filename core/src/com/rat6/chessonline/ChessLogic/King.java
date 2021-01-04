package com.rat6.chessonline.ChessLogic;

import com.badlogic.gdx.math.Vector2;
import com.rat6.chessonline.Board;

public class King extends FigureLogic {
    public King(Board board) {
        super(board);
    }

    @Override
    public boolean canMove(PieceEnum piece, Vector2 position, Vector2 to) {
        int posRow = (int) position.y, posCol = (int) position.x;
        int toRow = (int) to.y, toCol = (int) to.x;

        boolean diagonally = Math.abs(posCol-toCol)==1 && Math.abs(posRow-toRow)==1;

        boolean horizontally = posRow==toRow && Math.abs(posCol-toCol)==1;

        boolean vertically = Math.abs(posRow-toRow)==1 && posCol==toCol;

        boolean canMove = vertically || horizontally || diagonally;

        return !isOursUnderAttack(piece, to)  && canMove;
    }


}
