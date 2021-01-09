package com.rat6.chessonline.ChessLogic;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.rat6.chessonline.Board;

import java.util.ArrayList;
import java.util.List;

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
    public boolean isAfterMoveCheck(int row, int col, int rowTo, int colTo){
        Figure f = board.get(row, col);
        Figure f_clone = f.clone();
        Figure f2 = board.get(rowTo, colTo);
        Figure f2_clone = f2.clone();

        board.set_unchanged(row, col, f_clone);
        board.set_unchanged(rowTo, colTo, f2_clone);

        board.move(row, col, rowTo, colTo, false); //Кажется лучше сделать мув здесь
        boolean isC = king.isPosUnderAttack(); //Здесь получается именно король нашей команды

        board.set_unchanged(row, col, f);
        board.set_unchanged(rowTo, colTo, f2);

        return isC;
    }

    public List<Vector2> remove_moves_after_which_check(List<Vector2> available, Vector2 from){
        List<Vector2> mbButCheckAfter = new ArrayList<Vector2>();

        for(int i=0; i<available.size(); i++) {
            Vector2 move = available.get(i);

            if (isAfterMoveCheck((int) from.y, (int) from.x, (int) move.y, (int) move.x)) {
                System.out.println("Check after this move");
                mbButCheckAfter.add(available.remove(i));
            }
        }

        return mbButCheckAfter;
    }

     */
}
