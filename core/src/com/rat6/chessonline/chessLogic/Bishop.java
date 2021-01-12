package com.rat6.chessonline.chessLogic;

import com.badlogic.gdx.math.Vector2;
import com.rat6.chessonline.Board;

public class Bishop extends Figure {

    public Bishop(Board board, PieceEnum team, Vector2 position) {
        super(board, team, position);
        piece = team == PieceEnum.white ? PieceEnum.bishopW : PieceEnum.bishopB;
    }

    @Override
    public boolean canMove(Vector2 to) {
        int posRow = (int) position.y, posCol = (int) position.x;
        int toRow = (int) to.y, toCol = (int) to.x;

        if(posCol == toCol && posRow == toRow) return false; //Не подсвечивать свою жопу

        boolean diagonally = goodDiagonally(to);

        return !isOwnUnderAttack(to) && diagonally;
    }

    @Override
    public Figure clone() {
        Bishop clone = new Bishop(board, team, new Vector2(position));
        clone.piece = piece;
        clone.visible = visible;
        clone.lastPosition = new Vector2(lastPosition);
        clone.isFirstMove = isFirstMove;
        return clone;
    }
}
