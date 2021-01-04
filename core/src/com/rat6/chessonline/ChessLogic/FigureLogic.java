package com.rat6.chessonline.ChessLogic;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.rat6.chessonline.Board;

import java.util.ArrayList;
import java.util.List;

public abstract class FigureLogic {
    protected Board board;

    public FigureLogic(Board board){
        this.board = board;
    }

    public abstract boolean canMove(PieceEnum piece, Vector2 position, Vector2 to);

    public List<Vector2> getAvailableCells(PieceEnum piece, Vector2 position){
        List<Vector2> available = new ArrayList<Vector2>();
        Vector2 v = new Vector2();
        for(int x=0; x<8; x++){
            for(int y=0; y<8; y++){
                v.set(x, y);
                if(canMove(piece, position, v))
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

    public boolean isFigureOn(int row, int col){
        PieceEnum piece = board.getChessPiece(row, col);
        if(piece==PieceEnum.empty || piece==PieceEnum.can || piece==PieceEnum.cannot)
            return false;
        return true;
    }

    public boolean isOursUnderAttack(PieceEnum piece, Vector2 to){
        PieceEnum team1 = blackOrWhite(piece);
        PieceEnum underAttack = board.getChessPiece(to);
        PieceEnum team2 = blackOrWhite(underAttack);
        return team1 == team2;
    }

    public PieceEnum blackOrWhite(PieceEnum piece){
       if(piece==PieceEnum.bishopB || piece==PieceEnum.kingB || piece==PieceEnum.knightB || piece==PieceEnum.pawnB || piece==PieceEnum.queenB || piece==PieceEnum.rookB)
            return PieceEnum.black;
       else if(piece==PieceEnum.bishopW || piece==PieceEnum.kingW || piece==PieceEnum.knightW || piece==PieceEnum.pawnW || piece==PieceEnum.queenW || piece==PieceEnum.rookW)
            return PieceEnum.white;
       return PieceEnum.empty;
    }
}
