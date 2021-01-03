package com.rat6.chessonline.ChessLogic;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.rat6.chessonline.Board;

import java.util.List;

public abstract class FigureLogic {

    public abstract boolean canMove(Board board, PieceEnum piece, Vector2 position, Vector2 to);

    public abstract List<Vector2> getAvailableCells(Board board, PieceEnum piece, Vector2 position);

    public boolean isOursUnderAttack(Board board, PieceEnum piece, Vector2 to){
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
