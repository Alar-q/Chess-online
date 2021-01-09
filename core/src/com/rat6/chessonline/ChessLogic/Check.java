package com.rat6.chessonline.ChessLogic;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.rat6.chessonline.Board;

public class Check {

    private Board board;
    private boolean wasCheckBefore;
    private Figure king;
    private TextureRegion tr;
    private History history;

    public Check(Board board, Figure king, TextureRegion tr, History history){
        this.board = board;
        wasCheckBefore = false;
        this.king = king;
        this.tr = tr;
        this.history = history;
    }

    public boolean updateCheck(){
        wasCheckBefore = king.isPosUnderAttack(); //Проходит по всем клеткам, находит фигуры вражеской команды и проверяет бьют ли они клетку
        return wasCheckBefore;
    }



    public void present(){
        if(wasCheckBefore)
            board.drawCharacter(king.position, tr);
    }

    /*
    public boolean didntCorrectCheck(){
        boolean wcb = wasCheckBefore;
        if(updateCheck() && wcb) return true;
        return false;
    }
     */



    public boolean isAfterMoveCheck(int row, int col, int rowTo, int colTo){
        board.move(row, col, rowTo, colTo); //Кажется лучше сделать
        boolean isC = king.isPosUnderAttack();
        history.roll_back(1);
        //updateCheck();
        //Figure f = board.get(row, col);
        //f.setVisible(false);
        return isC;
    }
}
