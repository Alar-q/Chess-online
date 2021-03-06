package com.rat6.chessonline.chessLogic;

import com.badlogic.gdx.math.Vector2;
import com.rat6.chessonline.Board;

public class Pawn extends Figure {
    private PawnInterception pawnInterception;

    public Pawn(Board board, PieceEnum team, Vector2 position) {
        super(board, team, position);
        piece = team == PieceEnum.white ? PieceEnum.pawnW : PieceEnum.pawnB;
        pawnInterception = board.getPawnInterceptionLogic();
    }

    @Override
    public boolean canMove(Vector2 to) {
        int posRow = (int) position.y, posCol = (int) position.x;
        int toRow = (int) to.y, toCol = (int) to.x;


        PieceEnum team2 = board.get(to).team;

        boolean diagonally = (team2 != PieceEnum.empty) && (team2 == PieceEnum.white ? team == PieceEnum.black  && toRow-posRow==-1 : team == PieceEnum.white  && toRow-posRow==1) && Math.abs(posCol-toCol)==1;

        //Просто проверяем находится ли эта позиция to на диагонали и эта точка - это прошлое перепрыгнутая точка. Кстати, эта точка должна удаляться каждый ход
        boolean pawnInterception = this.pawnInterception.fits(posRow, posCol, toRow, toCol, this);

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

        return vertically || diagonally || pawnInterception;
    }

    @Override
    public Figure clone() {
        Pawn clone = new Pawn(board, team, new Vector2(position));
        clone.piece = piece;
        clone.visible = visible;
        clone.lastPosition = new Vector2(lastPosition);
        clone.isFirstMove = isFirstMove;
        return clone;
    }
}
