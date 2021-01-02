package com.rat6.chessonline;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class Gamepad {
    Main game;
    Board board;
    Vector2 capturedPoint;

    public Gamepad(Main game, Board board){
        this.game = game;
        this.board = board;
        capturedPoint = new Vector2(-1, -1);
    }
    public void update(Vector3 touchPoint){
        if(!isCaptured()) {
            if (touchPoint.x > game.padding && touchPoint.y < (board.boardSize - game.padding)) {
                //Схватить то что под этими координатами
                int x = (int) Math.floor((touchPoint.x- game.padding) / game.cellSize);
                int y = (int) Math.floor((touchPoint.y - board.boardLeftY - game.padding) / game.cellSize);
                Board.ChessPiece piece = board.getChessPiece(y, x);
                TextureRegion textureRegion = board.getCharactersTextureR(piece);
                board.setPos(x, y, Board.ChessPiece.empty);
                System.out.println(x + " " + y);
            }
        }else{
            //вернуть на место если сюда нельзя ходить (своя же фигура)
        }
    }
    private boolean isCaptured() {
        if (capturedPoint.x == -1)
            return false;
        else
            return true;
    }
}
