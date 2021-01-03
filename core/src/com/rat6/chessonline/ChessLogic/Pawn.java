package com.rat6.chessonline.ChessLogic;

import com.badlogic.gdx.math.Vector2;
import com.rat6.chessonline.Board;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends FigureLogic {

    @Override
    public boolean canMove(Board board, PieceEnum piece, Vector2 position, Vector2 to) {
        int posRow = (int) position.y, posCol = (int) position.x;
        int toRow = (int) to.y, toCol = (int) to.x;

        PieceEnum team = blackOrWhite(piece);

        boolean canMove = ((Math.abs(posRow-toRow)==1 && posCol==toCol) || (posRow==toRow && Math.abs(posCol-toCol)==1));

        return !isOursUnderAttack(board, piece, to)  && canMove;
    }

    @Override
    public List<Vector2> getAvailableCells(Board board, PieceEnum piece, Vector2 position) {
        List<Vector2> available = new ArrayList<Vector2>();
        Vector2 v = new Vector2();
        for(int x=0; x<8; x++){
            for(int y=0; y<8; y++){
                v.set(x, y);
                if(canMove(board, piece, position, v))
                    available.add(new Vector2(v));
            }
        }

        return available;
    }
}
