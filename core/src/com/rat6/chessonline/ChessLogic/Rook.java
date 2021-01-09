package com.rat6.chessonline.ChessLogic;

import com.badlogic.gdx.math.Vector2;
import com.rat6.chessonline.Board;

public class Rook extends Figure {
    public Rook(Board board, PieceEnum team, Vector2 position) {
        super(board, team, position);
        piece = team == PieceEnum.white ? PieceEnum.rookW : PieceEnum.rookB;
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

    @Override
    public Figure clone() {
        Rook clone = new Rook(board, team, new Vector2(position));
        clone.piece = piece;
        clone.visible = visible;
        clone.lastPosition = new Vector2(lastPosition);
        clone.isFirstMove = isFirstMove;
        return clone;
    }

}
