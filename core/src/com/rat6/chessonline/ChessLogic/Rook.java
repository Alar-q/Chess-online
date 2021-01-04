package com.rat6.chessonline.ChessLogic;

import com.badlogic.gdx.math.Vector2;
import com.rat6.chessonline.Board;

public class Rook extends Figure {
    public Rook(Board board, PieceEnum piece, Vector2 position) {
        super(board, piece, position);
    }

    @Override
    public boolean canMove(Vector2 to) {
        int posRow = (int) position.y, posCol = (int) position.x;
        int toRow = (int) to.y, toCol = (int) to.x;

        if(posCol == toCol && posRow == toRow) return false; //Не подсвечивать свою жопу

        boolean horizontally = goodHorizontally(to);

        boolean vertically = goodVertically(to);

        return !isOwnUnderAttack(to)  && (vertically || horizontally);

    }



}
