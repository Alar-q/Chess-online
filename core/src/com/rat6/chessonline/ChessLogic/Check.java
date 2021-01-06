package com.rat6.chessonline.ChessLogic;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.rat6.chessonline.Board;

public class Check {

    private Board board;
    private boolean isCheckNow;
    private Figure king;
    private TextureRegion tr;

    public Check(Board board, Figure king, TextureRegion tr){
        this.board = board;
        isCheckNow = false;
        this.king = king;
        this.tr = tr;
    }

    public boolean isCheckNow(){
        isCheckNow = king.isPosUnderAttack();
        return isCheckNow;
    }

    public boolean isCheckAfterMove(int row, int col, int rowTo, int colTo){
        //Двигаем все, проверяем king.isPosUnderAttack(), если король все еще под ударом, двигаем все обратно и возвращаем true

        return false;
    }

    public void present(){
        if(isCheckNow){
            board.drawCharacter(king.position, tr);
        }
    }

    public boolean didntCorrectCheck(int row, int col, int rowTo, int colTo){
        if(isCheckNow()){
            if(isCheckAfterMove(row, col, rowTo, colTo))
                return true;
        }
        return false;
    }

}
