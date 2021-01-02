package com.rat6.chessonline;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Board {
    public enum ChessPiece {
        empty,
        kingB, kingW,
        queenB, queenW,
        rookB, rookW,
        bishopB, bishopW,
        knightB, knightW,
        pawnB, pawnW,
    }

    private Main game;
    float boardSize, boardLeftY;
    private ChessPiece[][] board;


    public Board(Main game){
        this.game = game;

        boardSize = game.WORLD_WIDTH;
        boardLeftY = game.WORLD_HEIGHT/2-(boardSize/2);

        board = new ChessPiece[8][8];
        arrangePieces();
    }

    public void present(float deltaTime){
        for(int y=0; y<8; y++) {
            for (int x = 0; x < 8; x++) {
                drawCharacter(y, x);
            }
        }
    }

    public void arrangePieces(){
        for(int y=2; y<=6; y++) { //y-row, x-col
            for (int x = 0; x < 8; x++) {
                board[y][x] = ChessPiece.empty;
            }
        }
        //pawns
        for(int i=0; i<8; i++)
            board[1][i] = ChessPiece.pawnW;
        for(int i=0; i<8; i++)
            board[6][i] = ChessPiece.pawnB;

        //others
        for(int i=0; i<2; i++) {
            board[i * 7][0] = (i==0 ? ChessPiece.rookW : ChessPiece.rookB);
            board[i * 7][1] = (i==0 ? ChessPiece.knightW : ChessPiece.knightB);
            board[i * 7][2] = (i==0 ? ChessPiece.bishopW : ChessPiece.bishopB);
            board[i * 7][3] = (i==0 ? ChessPiece.kingW : ChessPiece.kingB);
            board[i * 7][4] = (i==0 ? ChessPiece.queenW : ChessPiece.queenB);
            board[i * 7][5] = (i==0 ? ChessPiece.bishopW : ChessPiece.bishopB);
            board[i * 7][6] = (i==0 ? ChessPiece.knightW : ChessPiece.knightB);
            board[i * 7][7] = (i==0 ? ChessPiece.rookW : ChessPiece.rookB);
        }
    }

    public void setPos(int row, int col, int newRow, int newCol){
        ChessPiece piece = board[row][col];
        board[newRow][newCol] = piece;
        board[row][col] = ChessPiece.empty;
    }
    public void setPos(int row, int col, ChessPiece chessPiece){
        board[row][col] = chessPiece;
    }

    private void drawCharacter(int y, int x){
        ChessPiece chessPiece = board[y][x];
        if(chessPiece == ChessPiece.empty) return;

        TextureRegion tr = getCharactersTextureR(chessPiece);

        float chx = game.padding + (game.cellSize * x) + x;
        float chy = boardLeftY + game.padding + (game.cellSize * y) + y;

        game.batcher.draw(tr, chx, chy, game.cellSize, game.cellSize);

    }

    public ChessPiece getChessPiece(int row, int col){
        return board[row][col];
    }

    public TextureRegion getCharactersTextureR(ChessPiece chessPiece){
        TextureRegion tr = null;
        switch (chessPiece) {
            case kingB:
                tr = game.assets.kingB;
                break;
            case kingW:
                tr = game.assets.kingW;
                break;
            case queenB:
                tr = game.assets.queenB;
                break;
            case queenW:
                tr = game.assets.queenW;
                break;
            case rookB:
                tr = game.assets.rookB;
                break;
            case rookW:
                tr = game.assets.rookW;
                break;
            case bishopB:
                tr = game.assets.bishopB;
                break;
            case bishopW:
                tr = game.assets.bishopW;
                break;
            case knightB:
                tr = game.assets.knightB;
                break;
            case knightW:
                tr = game.assets.knightW;
                break;
            case pawnB:
                tr = game.assets.pawnB;
                break;
            case pawnW:
                tr = game.assets.pawnW;
                break;
        }
        return tr;
    }


}
