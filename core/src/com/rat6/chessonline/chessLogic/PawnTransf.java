package com.rat6.chessonline.chessLogic;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.rat6.chessonline.Board;
import com.rat6.chessonline.Main;

public class PawnTransf {

    private Main game;
    private Board board;

    public boolean isTransNow;
    public Vector2 transPos;
    public com.rat6.chessonline.chessLogic.PieceEnum team;

    public PawnTransf(Main game, Board board){
        this.game = game;
        this.board = board;
        isTransNow = false;
        transPos = new Vector2();
    }

    public boolean fixPawn_Reached_The_End(int row, int col, com.rat6.chessonline.chessLogic.Figure f) {
        com.rat6.chessonline.chessLogic.PieceEnum p = f.piece;

        if ((p == com.rat6.chessonline.chessLogic.PieceEnum.pawnB && row == 0) || (p == com.rat6.chessonline.chessLogic.PieceEnum.pawnW && row == 7)) {
            transPos.set(col, row);
            team = f.team;
            isTransNow = true;
            return true;
        } else {
            isTransNow = false;
            return false;
        }
    }

    public void present(){
        TextureRegion tr = game.assets.green;
        for(int y=3; y<5; y++){
            for(int x=0; x<8; x++){
                board.drawCharacter(y, x, tr);
            }
        }
        TextureRegion[] trArr;
        if(team== PieceEnum.white)
            trArr = new TextureRegion[]{game.assets.knightW, game.assets.bishopW, game.assets.rookW, game.assets.queenW};
        else
            trArr = new TextureRegion[]{game.assets.knightB, game.assets.bishopB, game.assets.rookB, game.assets.queenB};

        float chy = board.boardLeftY + game.padding + (game.cellSize * 3) + 3;
        for(int x=0; x<4; x++){
            float chx = game.padding + (game.cellSize * (x*2) + (x*2)); //+col - это промежутки
            game.batcher.draw(trArr[x], chx, chy, game.cellSize*2, game.cellSize*2);
        }
    }

    public void onTouch(int row, int col){
        Figure f = null;

        //Можно добавить кнопку при нажатии которой игрок отказывается менять пешку
        if(!Board.iS_WITHIN_BOARD(row, col) || !(row>2 && row<5)){
            return;
        }
        else if(col>=0 && col<2){
            f = new Knight(board, team, transPos);
        }else if(col>=2 && col<4){
            f = new Bishop(board, team, transPos);
        }else if(col>=4 && col<6){
            f = new Rook(board, team, transPos);
        }else if(col>=6 && col<8){
            f = new Queen(board, team, transPos);
        }

        board.set(transPos, f);


        isTransNow = false;
    }
}
