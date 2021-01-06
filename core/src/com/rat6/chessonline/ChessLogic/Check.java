package com.rat6.chessonline.ChessLogic;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.rat6.chessonline.Board;

public class Check {

    private Board board;
    private boolean wasCheckBefore;
    private Figure king;
    private TextureRegion tr;

    public Check(Board board, Figure king, TextureRegion tr){
        this.board = board;
        wasCheckBefore = false;
        this.king = king;
        this.tr = tr;
    }

    public boolean isCheckNow(){
        wasCheckBefore = king.isPosUnderAttack();
        return wasCheckBefore;
    }



    public void present(){
        if(wasCheckBefore)
            board.drawCharacter(king.position, tr);
    }

    public boolean didntCorrectCheck(int row, int col, int rowTo, int colTo){


        boolean wcb = wasCheckBefore;
        if(isCheckNow() && wcb){
            return true;
        }
        return false;
    }

}
