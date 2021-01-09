package com.rat6.chessonline.ChessLogic;

import com.badlogic.gdx.math.Vector2;
import com.rat6.chessonline.Board;

public class Castling {

    public Board board;

    public Castling(Board board){
        this.board = board;
    }

    public boolean isCastling(Figure king, int posRow, int posCol, int toRow, int toCol){
        if(!king.isFirstMove)  //if the king once walked
            return false;

        Figure fTo = board.get(toRow, toCol);
        int cd = castlingDir(king, fTo, posRow, posCol, toRow, toCol);
        if(cd==0)
            return false;
        else
            return true;
    }
    public boolean isCastling(Figure king, Vector2 pos, Vector2 to){
        return isCastling(king, (int)pos.y, (int)pos.x, (int)to.y, (int)to.x);
    }

    //-1 - left
    //0 - нельзя сделать
    //1 - right
    public int castlingDir(Figure king, Figure fTo, int row, int col, int toRow, int toCol){
        if(! (king.piece == PieceEnum.kingW || king.piece == PieceEnum.kingB))
            return 0;

        if(row!=toRow || Math.abs(col-toCol)!=2) //just the wrong position
            return 0;

        if(fTo.piece != PieceEnum.empty)  //if this position is taken
            return 0;


        if(board.isPosUnderAttack(row, col, king.team)) //if check
            return 0;

        int res = 0;
        Figure rook;

        if(toCol<col) { //Left
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
            int OffsetCol = col + (res * i);
            // Это происходит только при swapRook.
            // Так как нельзя использовать isFirstMove, boardMove мы уже ходили и король перемещен на две клетки по x.
            if(king.position.x==OffsetCol) continue;

            if (board.isPosUnderAttack(row, OffsetCol, king.team) || king.isOwnUnderAttack(new Vector2(OffsetCol, row)))
                return 0;

        }

        return res;
    }

    public boolean ifCastling_SwapRook(Figure from, Figure fTo, int row, int col, int rowTo, int colTo) {
        int dir = castlingDir(from, fTo, row, col, rowTo, colTo);
        if (dir != 0) {
            //Поставить ладью на одно смещение по горизонтали от бывшей позиции короля
            int colFrom1 = dir == -1 ? 0 : 7;
            int row1 = from.team == PieceEnum.white ? 0 : 7;
            int colTo1 = dir == -1 ? 3 : 5;

            Figure fFrom = board.get(row1, colFrom1);
            Figure f2 = board.get(row1, colTo1); //Это пустая клетка

            board.deleteCharacter(row1, colFrom1, f2); //удаляем фигуру, которая стояла на старой позиции
            board.set(row1, colTo1, fFrom); //Поставили взятую рукой фигуру

            //board.move(row1, colFrom1, row1, colTo1);

            return true;
        } else
            return false;

    }

}
