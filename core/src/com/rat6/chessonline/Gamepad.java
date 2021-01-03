package com.rat6.chessonline;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class Gamepad {
    Main game;
    Board board;
    Vector2 capturedPoint;
    OrthographicCamera camera;
    Vector3 touchPoint;

    public Gamepad(Main game, OrthographicCamera camera, Board board){
        this.game = game;
        this.camera = camera;
        this.board = board;
        touchPoint = new Vector3();
        capturedPoint = new Vector2(-1, -1);//В координатах доски [0, 7]
    }
    public void update(Input in){
        if (in.isTouched()) {
            camera.unproject(touchPoint.set(in.getX(), in.getY(), 0));
            onTouch(touchPoint);
        }else{
            capturedPoint.set(-1, -1);
        }
    }

    TextureRegion textureRegion;
    public void onTouch(Vector3 touchPoint){
        if(!isCaptured()) {
            //Схватить то что под этими координатами
            int col = (int) Math.floor((touchPoint.x - game.padding) / game.cellSize);
            int row = (int) Math.floor((touchPoint.y - board.boardLeftY - game.padding) / game.cellSize);

            if (col>=0 && col<8 && row>=0 && row<8) {
                capturedPoint.set(row, col);
                Board.ChessPiece piece = board.getChessPiece(row, col);
                textureRegion = board.getCharactersTextureR(piece);
                board.setPos(row, col, Board.ChessPiece.empty);

            }
        }else{
            //game.batcher.draw(textureRegion, touchPoint.x, touchPoint.y, game.cellSize, game.cellSize);
            //
        }
    }
    private boolean isCaptured() {
        if (capturedPoint.x != -1) return true;
        return false;
    }
}
