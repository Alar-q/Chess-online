package com.rat6.chessonline.ChessLogic;

import com.rat6.chessonline.Board;

import java.util.ArrayList;
import java.util.List;

public class History {

    private Board board;
    private List<String> history;

    public History(Board board){
        this.board = board;
        history = new ArrayList<String>();
    }

    // Просто воспроизводим всю игру
    public void roll_back(int n){
        board.clear();
        for(int i=0; i<history.size()-n; i++){

            String move = history.remove(0);
            String[] moves;
            moves = move.split("-");
            if(moves.length<2)
                moves = move.split(":");


            char[] from = moves[0].toCharArray();
            char[] to = moves[1].toCharArray();
            int col, row;
            if(from.length<3) {
                col = ABC2INT(String.valueOf(from[0]));
                row = Integer.parseInt(String.valueOf(from[1])) - 1;
            }else {
                col = ABC2INT(String.valueOf(from[1]));
                row = Integer.parseInt(String.valueOf(from[2])) - 1;
            }
            int colTo = ABC2INT(String.valueOf(to[0]));
            int rowTo = Integer.parseInt(String.valueOf(to[1])) - 1;

            board.move(row, col, rowTo, colTo);
        }
    }

    public void move(int row, int col, int rowTo, int colTo){
        String res = "";
        Figure f = board.get(row, col);

        Figure fTo = board.get(rowTo, colTo);
        String act = fTo.piece == PieceEnum.empty ? "-" : ":";// + getFigureNaming(fTo);

        res += getFigureNaming(f);

        res += INT2ABS(col);
        res += row+1; //+1 потому что осчет массива начинается с нуля

        res+=act;

        res += INT2ABS(colTo);
        res += rowTo+1;

        System.out.println(res);

        history.add(res);

    }


    // x
    // INT - в массиве
    public static int ABC2INT(String c){
        if(c.equals("a"))
            return 0;
        else if(c.equals("b"))
            return 1;
        else if(c.equals("c"))
            return 2;
        else if(c.equals("d"))
            return 3;
        else if(c.equals("e"))
            return 4;
        else if(c.equals("f"))
            return 5;
        else if(c.equals("g"))
            return 6;
        else if(c.equals("h"))
            return 7;
        else
            return -1;
    }

    public static String INT2ABS(int i){
        i++;
        switch (i){
            case 1: return "a";
            case 2: return "b";
            case 3: return "c";
            case 4: return "d";
            case 5: return "e";
            case 6: return "f";
            case 7: return "g";
            case 8: return "h";
        }
        return "";
    }

    public static String getFigureNaming(Figure f){
        PieceEnum piece = f.piece;

       if(piece==PieceEnum.knightW || piece==PieceEnum.knightB)
            return "n";
        else if(piece==PieceEnum.bishopW || piece==PieceEnum.bishopB)
            return "b";
        else if(piece==PieceEnum.rookW || piece==PieceEnum.rookB)
            return "r";
        else if(piece==PieceEnum.queenW || piece==PieceEnum.queenB)
            return "q";
        else if(piece==PieceEnum.kingW || piece==PieceEnum.kingB)
            return "k";
        //else if(piece==PieceEnum.pawnW || piece==PieceEnum.pawnB) return "";
        else
            return "";
    }

    // При расшифровке нужно,
    // а команду можно узнать так n%2==0 then white else black
    public static PieceEnum getPiece(String c, PieceEnum team) {
        if (team == PieceEnum.white) {
            if(c.equals("k"))
                return PieceEnum.kingW;
            else if(c.equals("q"))
                return PieceEnum.queenW;
            else if(c.equals("r"))
                return PieceEnum.rookW;
            else if(c.equals("n"))
                return PieceEnum.knightW;
            else if(c.equals("b"))
                return PieceEnum.bishopW;
            else
                return PieceEnum.pawnW;
        }
        else{
            if(c.equals("k"))
                return PieceEnum.kingB;
            else if(c.equals("q"))
                return PieceEnum.queenB;
            else if(c.equals("r"))
                return PieceEnum.rookB;
            else if(c.equals("n"))
                return PieceEnum.knightB;
            else if(c.equals("b"))
                return PieceEnum.bishopB;
            else
                return PieceEnum.pawnB;
        }
    }
}
