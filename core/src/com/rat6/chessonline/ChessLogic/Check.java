package com.rat6.chessonline.ChessLogic;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.rat6.chessonline.Board;

public class Check {
    private Board board;
    public boolean isCheckNow;
    private Figure king;
    private TextureRegion tr;

    public Check(Board board, Figure king, TextureRegion tr){
        this.board = board;
        isCheckNow = false;
        this.king = king;
        this.tr = tr;
    }

    public void update(){
        isCheckNow = king.isPosUnderAttack();
    }
    public void present(){
        if(isCheckNow){
            board.drawCharacter(king.position, tr);
        }
    }
}
