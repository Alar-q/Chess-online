package com.rat6.chessonline;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.rat6.chessonline.ChessLogic.PieceEnum;

public class Board {


    private Main game;
    float boardSize, boardLeftY;
    private PieceEnum[][] board;


    public Board(Main game){
        this.game = game;

        boardSize = game.WORLD_WIDTH;
        boardLeftY = game.WORLD_HEIGHT/2-(boardSize/2);

        board = new PieceEnum[8][8];
        arrangePieces();
    }

    public void present(){
        for(int y=0; y<8; y++) {//y-row, x-col
            for (int x = 0; x < 8; x++) {
                PieceEnum piece = board[y][x];
                if(piece == PieceEnum.empty) continue;
                TextureRegion tr = game.assets.getCharactersTextureR(piece);
                drawCharacter(y, x, tr);
            }
        }
    }

    public void arrangePieces(){
        for(int y=2; y<=6; y++) { //y-row, x-col
            for (int x = 0; x < 8; x++) {
                board[y][x] = PieceEnum.empty;
            }
        }
        //pawns
        for(int i=0; i<8; i++)
            board[1][i] = PieceEnum.pawnW;
        for(int i=0; i<8; i++)
            board[6][i] = PieceEnum.pawnB;

        //others
        for(int i=0; i<2; i++) {
            board[i * 7][0] = (i==0 ? PieceEnum.rookW : PieceEnum.rookB);
            board[i * 7][1] = (i==0 ? PieceEnum.knightW : PieceEnum.knightB);
            board[i * 7][2] = (i==0 ? PieceEnum.bishopW : PieceEnum.bishopB);
            board[i * 7][3] = (i==0 ? PieceEnum.kingW : PieceEnum.kingB);
            board[i * 7][4] = (i==0 ? PieceEnum.queenW : PieceEnum.queenB);
            board[i * 7][5] = (i==0 ? PieceEnum.bishopW : PieceEnum.bishopB);
            board[i * 7][6] = (i==0 ? PieceEnum.knightW : PieceEnum.knightB);
            board[i * 7][7] = (i==0 ? PieceEnum.rookW : PieceEnum.rookB);
        }
    }

    public void drawCharacter(int row, int col, TextureRegion tr){
        float chx = game.padding + (game.cellSize * col) + col; //+col - это промежутки
        float chy = boardLeftY + game.padding + (game.cellSize * row) + row;

        game.batcher.draw(tr, chx, chy, game.cellSize, game.cellSize);
    }

    public void setPos(int row, int col, PieceEnum pieceEnum){
        board[row][col] = pieceEnum;
    }
    public void setPos(Vector2 newPos, PieceEnum pieceEnum){
        setPos((int)newPos.y, (int)newPos.x, pieceEnum);
    }

    public PieceEnum getChessPiece(int row, int col){
        if(isWithinBoard(row, col))
            return board[row][col];
        return PieceEnum.empty;
    }
    public PieceEnum getChessPiece(Vector2 pos){
        return getChessPiece((int)pos.y, (int)pos.x);
    }

    public void deleteCharacter(int row, int col){
        if(isWithinBoard(row, col))
            board[row][col] = PieceEnum.empty;
    }
    public void deleteCharacter(Vector2 v){
        deleteCharacter((int)v.y, (int)v.x);
    }

    public static boolean isWithinBoard(int row, int col){
        if(row>=0 && row<8 && col>=0 && col<8)
            return true;
        return false;
    }
    public static boolean isWithinBoard(Vector2 pos){
        return isWithinBoard((int)pos.y, (int)pos.x);
    }
}
