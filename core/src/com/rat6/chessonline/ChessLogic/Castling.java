package com.rat6.chessonline.ChessLogic;

import com.badlogic.gdx.math.Vector2;

public class Castling {

    public static boolean castling(Figure king, int posRow, int posCol, int toRow, int toCol){
        int cd = castlingDir(king, posRow, posCol, toRow, toCol);
        if(cd==0)
            return false;
        return true;
    }

    //-1 - left
    //0 - нельзя сделать
    //1 - right
    public static int castlingDir(Figure king, int posRow, int posCol, int toRow, int toCol){
        if(posRow!=toRow || Math.abs(posCol-toCol)!=2) //just the wrong position
            return 0;

        if(!king.isFirstMove) //if the king once walked
            return 0;

        PieceEnum team1 = king.board.getChessPiece(toRow, toCol);
        if(team1 != PieceEnum.empty) //if this position is taken
            return 0;

        if(king.isPosUnderAttack(posRow, posCol)) //if check
            return 0;

        int res = 0;
        Figure rook;

        if(toCol<posCol) { //Left
            rook = king.team == PieceEnum.white ? king.board.get(0, 0) : king.board.get(7, 0);
            if (!rook.isFirstMove)
                return 0;
            else if (!(rook.piece == PieceEnum.rookB || rook.piece == PieceEnum.rookW))
                return 0;

            res = -1;
        }
        else { //Right
            rook = king.team == PieceEnum.white ? king.board.get(0, 7) : king.board.get(7, 7);
            if (!rook.isFirstMove)
                return 0;
            else if (!(rook.piece == PieceEnum.rookB || rook.piece == PieceEnum.rookW))
                return 0;
            PieceEnum p = king.board.getChessPiece(toRow, toCol+1); //Позиция G1 при длинной рокировке
            if(p!=PieceEnum.empty){return 0;}
            res = 1;
        }

        for (int i = 1; i <= 2; i++) {
            if (king.isPosUnderAttack(posRow, posCol + (res * i)) || king.isOwnUnderAttack(new Vector2(posCol + (res * i), posRow))) {
                return 0;
            }
        }

        return res;
    }

}
