package com.rat6.chessonline.ChessLogic;

import com.badlogic.gdx.math.Vector2;
import com.rat6.chessonline.Board;

public class Bishop extends Figure {

    public Bishop(Board board, PieceEnum pieceEnum, Vector2 position) {
        super(board, pieceEnum, position);
    }

    @Override
    public boolean canMove(Vector2 to) {
        int posRow = (int) position.y, posCol = (int) position.x;
        int toRow = (int) to.y, toCol = (int) to.x;

        if(posCol == toCol && posRow == toRow) return false; //Не подсвечивать свою жопу

        boolean diagonally = goodDiagonally(to);

        return !isOwnUnderAttack(to) && diagonally;
    }
}
