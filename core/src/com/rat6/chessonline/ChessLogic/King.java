package com.rat6.chessonline.ChessLogic;

import com.badlogic.gdx.math.Vector2;
import com.rat6.chessonline.Board;


public class King extends Figure {

    private CastlingLogic castlingLogic;

    public King(Board board, PieceEnum team, Vector2 position) {
        super(board, team, position);
        piece = team == PieceEnum.white ? PieceEnum.kingW : PieceEnum.kingB;
        this.castlingLogic = board.getCastling();
    }

    @Override
    public boolean canMove(Vector2 to) {
        int posRow = (int) position.y, posCol = (int) position.x;
        int toRow = (int) to.y, toCol = (int) to.x;

        boolean diagonally = Math.abs(posCol-toCol)==1 && Math.abs(posRow-toRow)==1;

        boolean horizontally = posRow==toRow && Math.abs(posCol-toCol)==1;

        boolean isCastling = castlingLogic.isCastling(this, position, to);

        boolean vertically = Math.abs(posRow-toRow)==1 && posCol==toCol;

        boolean canMove = vertically || horizontally || diagonally || isCastling;

        return  !isOwnUnderAttack(to) && canMove;
    }

}
