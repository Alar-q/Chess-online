package com.rat6.chessonline.chessLogic;

import com.badlogic.gdx.math.Vector2;
import com.rat6.chessonline.Board;

public class PawnInterception {

    private Board board;
    private Vector2 interceptionPosition;
    public boolean wasOverrun;
    private PieceEnum team;


    public PawnInterception(Board board){
        this.board = board;
        interceptionPosition = new Vector2();
        wasOverrun = false;
    }


    //Если это пешка
    //Если пешка прыгает на два, то мы записываем перепрыгнутую позиция
    //Else удаляем точку
    public boolean fixPawn2Jump(int row, int col, int rowTo, int colTo, Figure fFrom) {
        if ((fFrom.piece == PieceEnum.pawnW || fFrom.piece == PieceEnum.pawnB) && col == colTo && Math.abs(row - rowTo) == 2) {
            wasOverrun = true;
            interceptionPosition.set(col, fFrom.team == PieceEnum.white ? row + 1 : row - 1);
            team = fFrom.team;
            return true;
        } else {
            wasOverrun = false;
            return false;
        }
    }


    public boolean fits(int row, int col, int rowTo, int colTo, Figure fFrom){
        //Нельзя сделать перехват если 1) двойного прыжка не было, 2) точка совсем не та, 3) команды совпадают, то есть нельзя съесть своего
        if(!wasOverrun ||  ( interceptionPosition.x!=colTo || interceptionPosition.y!=rowTo ) || fFrom.team == team )
            return false;
        //Можно если находится на диагонали
        else if( Math.abs(col-colTo)==1 && (fFrom.team == PieceEnum.white ? rowTo-row==1 : rowTo-row==-1) )
            return true;
        else
            return false;
    }


    public boolean ifInterception_removeEnemyPawn(int row, int col, int rowTo, int colTo, Figure figFrom){
        boolean fisting = fits(row, col, rowTo, colTo, figFrom);

        if((figFrom.piece == PieceEnum.pawnW || figFrom.piece == PieceEnum.pawnB) && fisting){
            int i = figFrom.team == PieceEnum.white ? -1 : 1;
            interceptionPosition.add(0, i);
            board.deleteCharacter(interceptionPosition);
            return true;
        }
        return false;
    }
    public boolean ifInterception_removeEnemyPawn(Figure figFrom){
        return ifInterception_removeEnemyPawn( (int)figFrom.lastPosition.y, (int)figFrom.lastPosition.x, (int)figFrom.position.y, (int)figFrom.position.x, figFrom);
    }


    public boolean isPosIsInterception(Figure figure, Vector2 v){
        if((figure.piece==PieceEnum.pawnW || figure.piece==PieceEnum.pawnB ) && wasOverrun && interceptionPosition.x==v.x && interceptionPosition.y==v.y) {
            return true;
        }
        return false;
    }
}
