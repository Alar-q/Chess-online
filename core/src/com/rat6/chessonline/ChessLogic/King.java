package com.rat6.chessonline.ChessLogic;

import com.badlogic.gdx.math.Vector2;
import com.rat6.chessonline.Board;

import static com.rat6.chessonline.ChessLogic.Castling.castling;

public class King extends Figure {
    public King(Board board, PieceEnum pieceEnum, Vector2 position) {
        super(board, pieceEnum, position);
    }

    @Override
    public boolean canMove(Vector2 to) {
        int posRow = (int) position.y, posCol = (int) position.x;
        int toRow = (int) to.y, toCol = (int) to.x;

        boolean diagonally = Math.abs(posCol-toCol)==1 && Math.abs(posRow-toRow)==1;

        boolean horizontally = posRow==toRow && Math.abs(posCol-toCol)==1;

        boolean castling = castling(this, posRow, posCol, toRow, toCol);
        if(castling){
            System.out.println(toRow + " "  + toCol);
            System.out.println("Castling");
        }

        boolean vertically = Math.abs(posRow-toRow)==1 && posCol==toCol;

        boolean canMove = vertically || horizontally || diagonally || castling;

        return  !isOwnUnderAttack(to) && canMove;
    }

}
