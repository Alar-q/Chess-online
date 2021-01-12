package com.rat6.chessonline.chessLogic;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.rat6.chessonline.Board;

import java.util.ArrayList;
import java.util.List;

public class Check {

    private Board board;
    private boolean wasCheckBefore;
    private com.rat6.chessonline.chessLogic.Figure king;
    private TextureRegion tr;

    public Check(Board board, com.rat6.chessonline.chessLogic.Figure king, TextureRegion tr){
        this.board = board;
        wasCheckBefore = false;
        this.king = king;
        this.tr = tr;
    }

    public boolean fixCheck(){
        wasCheckBefore = king.isPosUnderAttack(); //Проходит по всем клеткам, находит фигуры вражеской команды и проверяет бьют ли они клетку
        return wasCheckBefore;
    }


    public void present(){
        if(wasCheckBefore)
            board.drawCharacter(king.position, tr);
    }


    public boolean isAfterMoveCheck(int row, int col, int rowTo, int colTo){
        com.rat6.chessonline.chessLogic.Figure f = board.get(row, col);
        com.rat6.chessonline.chessLogic.Figure f_clone = f.clone();
        com.rat6.chessonline.chessLogic.Figure f2 = board.get(rowTo, colTo);
        com.rat6.chessonline.chessLogic.Figure empty = board.createEmpty(row, col);

        board.set_unchanged(row, col, empty); //удаляем фигуру, которая стояла на старой позиции
        board.set_unchanged(rowTo, colTo, f_clone); //Поставили взятую рукой фигуру

        boolean isC; //Здесь получается именно король нашей команды
        if(f.piece == king.piece)
            isC = board.isPosUnderAttack(rowTo, colTo, king.team);
        else
            isC = king.isPosUnderAttack();

        //Вернули все на свои места
        board.set_unchanged(row, col, f);
        board.set_unchanged(rowTo, colTo, f2);

        return isC;
    }

    public boolean isAfterMoveCheck(Vector2 from, Vector2 to){
        return isAfterMoveCheck((int)from.y, (int)from.x, (int)to.y, (int)to.x);
    }

    public List<Vector2> remove_moves_after_which_check(List<Vector2> available, Vector2 from){
        List<Vector2> mbButCheckAfter = new ArrayList<Vector2>();

        for(int i=0; i<available.size(); i++) {
            Vector2 move = available.get(i);

            if (isAfterMoveCheck((int) from.y, (int) from.x, (int) move.y, (int) move.x)) {
                mbButCheckAfter.add(move);
            }
        }

        available.removeAll(mbButCheckAfter);

        return mbButCheckAfter;
    }

    public void setKing(Figure king){
        this.king = king;
    }
}
