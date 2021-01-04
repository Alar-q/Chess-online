package com.rat6.chessonline.ChessLogic;

import com.badlogic.gdx.math.Vector2;
import com.rat6.chessonline.Board;

public class Bishop extends FigureLogic {

    public Bishop(Board board) {
        super(board);
    }

    @Override
    public boolean canMove(PieceEnum piece, Vector2 position, Vector2 to) {
        int posRow = (int) position.y, posCol = (int) position.x;
        int toRow = (int) to.y, toCol = (int) to.x;

        if(posCol == toCol && posRow == toRow) return false; //Не подсвечивать свою жопу

        boolean diagonally = goodDiagonally(posRow, posCol, toRow, toCol);

        return !isOursUnderAttack(piece, to) && diagonally;
    }
}
