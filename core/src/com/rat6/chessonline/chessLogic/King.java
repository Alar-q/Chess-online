package com.rat6.chessonline.chessLogic;

import com.badlogic.gdx.math.Vector2;
import com.rat6.chessonline.Board;


public class King extends Figure {

    private Castling castling;

    public King(Board board, PieceEnum team, Vector2 position) {
        super(board, team, position);
        piece = team == PieceEnum.white ? PieceEnum.kingW : PieceEnum.kingB;
        this.castling = board.getCastling();
    }

    @Override
    public boolean canMove(Vector2 to) {
        int posRow = (int) position.y, posCol = (int) position.x;
        int toRow = (int) to.y, toCol = (int) to.x;

        boolean diagonally = Math.abs(posCol-toCol)==1 && Math.abs(posRow-toRow)==1;

        boolean horizontally = posRow==toRow && Math.abs(posCol-toCol)==1;

        boolean isCastling = castling.isCastling(this, position, to);

        boolean vertically = Math.abs(posRow-toRow)==1 && posCol==toCol;

        boolean canMove = vertically || horizontally || diagonally || isCastling;

        return  !isOwnUnderAttack(to) && canMove;
    }

    @Override
    public Figure clone() {
        King clone = new King(board, team, new Vector2(position));
        clone.piece = piece;
        clone.visible = visible;
        clone.lastPosition = new Vector2(lastPosition);
        clone.isFirstMove = isFirstMove;
        return clone;
    }
}
