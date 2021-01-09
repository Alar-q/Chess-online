package com.rat6.chessonline.ChessLogic;

import com.badlogic.gdx.math.Vector2;
import com.rat6.chessonline.Board;

import java.util.ArrayList;
import java.util.List;

public abstract class Figure {
    protected Board board;
    public boolean visible, isFirstMove;
    public PieceEnum piece, team;
    public Vector2 position, lastPosition;


    public Figure(Board board, PieceEnum team, Vector2 position){
        this.board = board;
        this.position = position;
        this.team = team;
        this.piece = PieceEnum.empty;
        lastPosition = new Vector2(-1, -1);
        visible = true;
        isFirstMove = true;
    }

    public abstract boolean canMove(Vector2 to);
    public abstract Figure clone();

    public List<Vector2> getAvailableCells(){
        List<Vector2> available = new ArrayList<Vector2>();
        Vector2 v = new Vector2();
        for(int x=0; x<8; x++){
            for(int y=0; y<8; y++){
                v.set(x, y);
                if(canMove(v))
                    available.add(new Vector2(v));
            }
        }

        return available;
    }

    public boolean goodHorizontally(int posRow, int posCol, int toRow, int toCol){
        boolean horizontally = posRow==toRow;//&& Math.abs(posCol-toCol)==1;
        if(horizontally){
            int sign = posCol<toCol?1:-1;//Проверить на препятствия
            for (int i = 1; i < Math.abs(posCol-toCol) ; i++) {
                if(isFigureOn(posRow, posCol+(sign*i))){
                    return false;
                }
            }
        }
        return horizontally;
    }
    public boolean goodHorizontally(Vector2 to){
        return goodHorizontally((int)position.y, (int)position.x, (int)to.y, (int)to.x);
    }

    public boolean goodVertically(int posRow, int posCol, int toRow, int toCol){
        boolean vertically = posCol==toCol; //&& Math.abs(posRow-toRow)==1 ;
        if(vertically){
            int sign = posRow<toRow?1:-1;//Проверить на препятствия
            for (int i = 1; i < Math.abs(posRow-toRow) ; i++) {
                if(isFigureOn(posRow+(sign*i), posCol)){
                    return false;
                }
            }
        }
        return vertically;
    }
    public boolean goodVertically(Vector2 to){
        return goodVertically((int)position.y, (int)position.x, (int)to.y, (int)to.x);
    }

    public boolean goodDiagonally(int posRow, int posCol, int toRow, int toCol){
        boolean diagonally = Math.abs(posCol-toCol) == Math.abs(posRow-toRow);
        if(diagonally){
            int signX = posCol>toCol ? -1 : 1;
            int signY = posRow>toRow ? -1 : 1;
            for(int i = 1; i < Math.abs(posRow-toRow) ; i++){
                if(isFigureOn(posRow+(signY*i), posCol+(signX*i))){
                    return false;
                }
            }
        }
        return diagonally;
    }
    public boolean goodDiagonally(Vector2 to){
        return goodDiagonally((int)position.y, (int)position.x, (int)to.y, (int)to.x);
    }

    public boolean isFigureOn(int row, int col){
        PieceEnum piece = board.getChessPiece(row, col);
        if(piece==PieceEnum.empty || piece==PieceEnum.can || piece==PieceEnum.cannot)
            return false;
        return true;
    }

    public boolean isOwnUnderAttack(int row, int col){
        PieceEnum team2 = board.get(row, col).team;
        return team == team2;
    }
    public boolean isOwnUnderAttack(Vector2 to){
        return isOwnUnderAttack((int)to.y, (int)to.x);
    }


    public boolean isPosUnderAttack(){
        return board.isPosUnderAttack(position, team);
    }


    public void setVisible(boolean b){
        visible = b;
    }
    public void setPosition(int row, int col){
        isFirstMove = false;
        lastPosition.set(position);
        position.set(col, row);
    }
}
