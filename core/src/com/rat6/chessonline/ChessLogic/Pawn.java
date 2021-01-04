package com.rat6.chessonline.ChessLogic;

import com.badlogic.gdx.math.Vector2;
import com.rat6.chessonline.Board;

public class Pawn extends Figure {

    public Pawn(Board board, PieceEnum piece, Vector2 position) {
        super(board, piece, position);
    }

    @Override
    public boolean canMove(Vector2 to) {
        int posRow = (int) position.y, posCol = (int) position.x;
        int toRow = (int) to.y, toCol = (int) to.x;


        PieceEnum team2 = board.get(to).team;

        boolean diagonally = (team2 != PieceEnum.empty) && (team2 == PieceEnum.white ? team == PieceEnum.black  && toRow-posRow==-1 : team == PieceEnum.white  && toRow-posRow==1) && Math.abs(posCol-toCol)==1;

        boolean correctDist = (team==PieceEnum.white ? posRow-toRow==-1 : posRow-toRow==1);

        if(isFirstMove && !correctDist) {
            correctDist = team == PieceEnum.white ? posRow - toRow == -2 : posRow - toRow == 2;
            if(correctDist) { //Фигура на пути
                int ceiling_wall_Y = team == PieceEnum.white ? (int) to.y - 1 : (int) to.y + 1;
                PieceEnum u = board.getChessPiece(ceiling_wall_Y, (int) to.x);
                correctDist = (u==PieceEnum.empty);
            }
        }

        boolean vertically = (team2 == PieceEnum.empty) && correctDist && posCol==toCol;

        return vertically || diagonally;
    }

}
