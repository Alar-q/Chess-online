package com.rat6.chessonline.ChessLogic;

import com.badlogic.gdx.math.Vector2;
import com.rat6.chessonline.Board;

public class Queen extends Figure {

    public Queen(Board board, PieceEnum team, Vector2 position) {
        super(board, team, position);
        piece = team == PieceEnum.white ? PieceEnum.queenW : PieceEnum.queenB;
    }

    @Override
    public boolean canMove(Vector2 to) {
        int posRow = (int) position.y, posCol = (int) position.x;
        int toRow = (int) to.y, toCol = (int) to.x;

        if(posCol == toCol && posRow == toRow) return false; //Не подсвечивать свою жопу

        boolean diagonally = goodDiagonally(to);

        boolean horizontally = goodHorizontally(to);

        boolean vertically = goodVertically(to);

        return !isOwnUnderAttack(to)  && (vertically || horizontally || diagonally);

    }

}
