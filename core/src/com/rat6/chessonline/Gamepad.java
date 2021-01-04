package com.rat6.chessonline;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.rat6.chessonline.ChessLogic.Figure;
import com.rat6.chessonline.ChessLogic.PieceEnum;

import java.util.ArrayList;
import java.util.List;

public class Gamepad {
    private Main game;
    private Board board;
    private OrthographicCamera camera;
    private Vector3 touchPoint;

    private Vector2 capturedPos;//Чтобы можно было вернуть фигуру на место
    private Vector2 lastPos; //Это координаты того где именно сейчас находится взятая фигура

    private TextureRegion capturedTextureRegion;

    private Figure figure;

    private List<Vector2> available;

    public Gamepad(Main game, OrthographicCamera camera, Board board){
        this.game = game;
        this.camera = camera;
        this.board = board;
        touchPoint = new Vector3();
        capturedPos = new Vector2(-1, -1);//В координатах доски [0, 7]
        lastPos = new Vector2(-1, -1);
        available = new ArrayList<Vector2>();
    }
    public void update(Input in){
        if (in.isTouched()) {
            camera.unproject(touchPoint.set(in.getX(), in.getY(), 0));

            //В координаты доски
            int col = (int) Math.floor((touchPoint.x - game.padding) / game.cellSize);
            int row = (int) Math.floor((touchPoint.y - board.boardLeftY - game.padding) / game.cellSize);

            lastPos.set(col, row);
            PieceEnum lastPiece = board.getChessPiece(row, col);

            if(!isCaptured() && lastPiece != PieceEnum.empty && Board.isWithinBoard(lastPos)) {
                capturePiece(lastPiece, lastPos);
            }
        }else {
            if(isCaptured()) {
                if(Board.isWithinBoard(lastPos) && figure.canMove(lastPos)) {
                    board.move(capturedPos, lastPos);
                }
                pawn_Reached_The_End();
                //else board.setPos(capturedPos, capturedPiece);
                figure.setVisible(true);
                available.clear();
                capturedPos.set(-1, -1);
            }
        }
    }

    public void capturePiece(PieceEnum lastPiece, Vector2 lastPos){
        //Схватить то что под этими координатами
        capturedPos.set(lastPos);
        capturedTextureRegion = game.assets.getCharactersTextureR(lastPiece);
        figure = board.get(lastPos);
        figure.setVisible(false);//board.deleteCharacter(lastPos);
        available.addAll(figure.getAvailableCells());

    }

    public void present(){
        if(isCaptured()){
            game.batcher.draw(capturedTextureRegion, touchPoint.x-game.cellSize/2, touchPoint.y-game.cellSize/2, game.cellSize, game.cellSize);
        }
    }

    public void highlight(){
        if(isCaptured()) {
            //Подсвечивать места куда можно ходить
            for (Vector2 v : available) {
                board.drawCharacter((int) v.y, (int) v.x, game.assets.can);
            }
        }
    }

    public Vector2 pawn_Reached_The_End(){
        for(int i=0; i<2; i++){
            for(int x=0; x<8; x++){
                PieceEnum p = board.getChessPiece(7*i, x);
                if(p==PieceEnum.pawnB || p==PieceEnum.pawnW) {
                    System.out.println(x + " " + 7*i);
                    return new Vector2(x, 7*i);
                }
            }
        }
        return new Vector2(-1, -1);
    }

    private boolean isCaptured() {
        if (capturedPos.x != -1) return true;
        return false;
    }
}
