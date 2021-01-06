package com.rat6.chessonline.ChessLogic;

import com.badlogic.gdx.math.Vector2;
import com.rat6.chessonline.Board;

public class CastlingLogic {

    public Board board;

    public CastlingLogic(Board board){
        this.board = board;
    }

    public boolean isCastling(Figure king, int posRow, int posCol, int toRow, int toCol){
        int cd = castlingDir(king, posRow, posCol, toRow, toCol);
        if(cd==0)
            return false;
        return true;
    }

    public boolean isCastling(Figure king, Vector2 pos, Vector2 to){
        return isCastling(king, (int)pos.y, (int)pos.x, (int)to.y, (int)to.x);
    }

    //-1 - left
    //0 - нельзя сделать
    //1 - right
    public int castlingDir(Figure king, int posRow, int posCol, int toRow, int toCol){
        if(posRow!=toRow || Math.abs(posCol-toCol)!=2) //just the wrong position
            return 0;
        if(!king.isFirstMove) //if the king once walked
            return 0;

        PieceEnum team1 = board.get(toRow, toCol).team;
        if(team1 != PieceEnum.empty) //if this position is taken
            return 0;

        if(king.isPosUnderAttack(posRow, posCol)) //if check
            return 0;

        int res = 0;
        Figure rook;

        if(toCol<posCol) { //Left
            rook = king.team == PieceEnum.white ? board.get(0, 0) : board.get(7, 0);

            PieceEnum p = king.board.getChessPiece(toRow, toCol-1); //Позиция B1 при длинной рокировке, она должна быть пуста
            if(p!=PieceEnum.empty)
                return 0;

            res = -1;
        }
        else { //Right
            rook = king.team == PieceEnum.white ? king.board.get(0, 7) : king.board.get(7, 7);

            res = 1;
        }

        if (!rook.isFirstMove)
            return 0;
        else if ( ! (rook.piece == PieceEnum.rookB || rook.piece == PieceEnum.rookW))
            return 0;

        for (int i = 1; i <= 2; i++) {
            if (king.isPosUnderAttack(posRow, posCol + (res * i))
                    || king.isOwnUnderAttack(new Vector2(posCol + (res * i), posRow)))
                return 0;
        }


        return res;
    }

    public boolean ifCastling_SwapRook(Figure from, int row, int col, int rowTo, int colTo){
        if((from.piece == PieceEnum.kingB || from.piece == PieceEnum.kingW) && isCastling(from, row, col, rowTo, colTo)) {
            //Поставить ладью на одно смещение по горизонтали от бывшей позиции короля
            int dir = castlingDir(from, row, col, rowTo, colTo);

            int colFrom1 = dir == -1 ? 0 : 7;
            int row1 = from.team == PieceEnum.white ? 0 : 7;
            int colTo1 = dir == -1 ? 3 : 5;

            board.move(row1, colFrom1, row1, colTo1);

            return true;
        }
        return false;
    }

}
