package com.rat6.chessonline.ChessLogic;
import com.rat6.chessonline.Board;
import com.rat6.chessonline.ChessLogic.PieceEnum;

public class Logic {

    private FigureLogic queen;
    private FigureLogic king;
    private FigureLogic knight;
    private FigureLogic rook;
    private FigureLogic bishop;
    private FigureLogic pawn;

    public Logic(Board board){
        queen = new Queen(board);
        king = new King(board);
        knight = new Knight(board);
        rook = new Rook(board);
        bishop = new Bishop(board);
        pawn = new Pawn(board);
    }

    public FigureLogic getFigureImpl(PieceEnum pieceEnum){
        if (pieceEnum == PieceEnum.kingB || pieceEnum==PieceEnum.kingW)
            return king;
        else if( pieceEnum == PieceEnum.queenB || pieceEnum ==PieceEnum.queenW)
            return queen;
        else if( pieceEnum ==PieceEnum.rookB || pieceEnum ==PieceEnum.rookW)
            return rook;
        else if( pieceEnum ==PieceEnum.bishopB || pieceEnum ==PieceEnum.bishopW)
            return bishop;
        else if( pieceEnum ==PieceEnum.knightB ||pieceEnum == PieceEnum.knightW)
            return knight;
        else if( pieceEnum ==PieceEnum.pawnB || pieceEnum ==PieceEnum.pawnW)
            return pawn;
        return null;
    }
}
