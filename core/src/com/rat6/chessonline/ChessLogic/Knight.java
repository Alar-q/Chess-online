package com.rat6.chessonline.ChessLogic;

import com.badlogic.gdx.math.Vector2;
import com.rat6.chessonline.Board;

public class Knight extends Figure {

    public Knight(Board board, PieceEnum team, Vector2 position) {
        super(board, team, position);
        piece = team == PieceEnum.white ? PieceEnum.knightW : PieceEnum.knightB;
    }

    @Override
    public boolean canMove(Vector2 to) {
        int posRow = (int) position.y, posCol = (int) position.x;
        int toRow = (int) to.y, toCol = (int) to.x;

        boolean good = (Math.abs(posRow-toRow)==2 && Math.abs(posCol-toCol)==1) || (Math.abs(posRow-toRow)==1 && Math.abs(posCol-toCol)==2);

        return !isOwnUnderAttack(to)  && good;

    }

}
