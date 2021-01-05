package com.rat6.chessonline.ChessLogic;

import com.badlogic.gdx.math.Vector2;
import com.rat6.chessonline.Board;

public class PawnInterceptionLogic {
    private Board board;
    private Vector2 interceptionPosition;
    public boolean wasOverrun;

    public PawnInterceptionLogic(Board board){
        this.board = board;
        interceptionPosition = new Vector2();
        wasOverrun = false;
    }


    //Если это пешка
    //Если пешка прыгает на два, то мы записываем перепрыгнутую позиция
    //Else удаляем точку
    public boolean fixPawnJump(int row, int col, int rowTo, int colTo) {
        Figure f = board.get(row, col);
        if ((f.piece == PieceEnum.pawnW || f.piece == PieceEnum.pawnB) && col == colTo && Math.abs(row - rowTo) == 2) {
            wasOverrun = true;
            interceptionPosition.set(col, f.team == PieceEnum.white ? row + 1 : row - 1);
            System.out.println(interceptionPosition.x + " " + interceptionPosition.y);
            return true;
        } else {
            wasOverrun = false;
            return false;
        }
    }

    public boolean fits(int row, int col, int rowTo, int colTo){
        if(!wasOverrun || interceptionPosition.x!=colTo || interceptionPosition.y!=rowTo)
            return false;
        Figure f = board.get(row, col);
        Figure toF = board.get(rowTo, colTo);


        if(Math.abs(col-colTo)==1 && toF.piece == PieceEnum.empty && (f.team == PieceEnum.white ? rowTo-row==1 : rowTo-row==-1) ){
            return true;
        }
        return false;
    }

    public boolean ifInterception_removeEnemyPawn(int row, int col, int rowTo, int colTo){
        Figure f = board.get(row, col);

        if((f.piece == PieceEnum.pawnW || f.piece == PieceEnum.pawnB) && fits(row, col, rowTo, colTo)){
            System.out.println("Kill pawn");
            int i = f.team == PieceEnum.white ? -1 : 1;
            interceptionPosition.add(0, i);
            board.deleteCharacter(interceptionPosition);
        }
        return false;
    }

    public boolean isPosIsInterception(Vector2 v){
        if(wasOverrun && interceptionPosition.x==v.x && interceptionPosition.y==v.y){
            return true;
        }
        return false;
    }
}
