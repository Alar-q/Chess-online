package com.rat6.chessonline.ChessLogic;

import com.badlogic.gdx.math.Vector2;
import com.rat6.chessonline.Board;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends FigureLogic {

    public Pawn(Board board) {
        super(board);
    }

    @Override
    public boolean canMove(PieceEnum piece, Vector2 position, Vector2 to) {
        int posRow = (int) position.y, posCol = (int) position.x;
        int toRow = (int) to.y, toCol = (int) to.x;

        PieceEnum team = blackOrWhite(piece);

        PieceEnum underAttack = board.getChessPiece(to);
        PieceEnum team2 = blackOrWhite(underAttack);

        boolean diagonally = (team2 != PieceEnum.empty) && (team2 == PieceEnum.white ? team == PieceEnum.black  && toRow-posRow==-1 : team == PieceEnum.white  && toRow-posRow==1) && Math.abs(posCol-toCol)==1;

        boolean firstMove = position.y == 1 || position.y == 6;
        boolean correctDist = (team==PieceEnum.white ? posRow-toRow==-1 : posRow-toRow==1);
        if(!correctDist && firstMove) {
            correctDist = team == PieceEnum.white ? posRow - toRow == -2 : posRow - toRow == 2;
            if(correctDist) { //Фигура на пути
                int ceiling_wall_Y = team == PieceEnum.white ? (int) to.y - 1 : (int) to.y + 1;
                underAttack = board.getChessPiece(ceiling_wall_Y, (int) to.x);
                correctDist = underAttack == PieceEnum.empty ;
            }
        }

        boolean vertically = (team2 == PieceEnum.empty) && correctDist && posCol==toCol;

        return vertically || diagonally;
    }

}
