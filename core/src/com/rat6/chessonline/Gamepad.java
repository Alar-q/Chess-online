package com.rat6.chessonline;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.rat6.chessonline.ChessLogic.*;

import java.util.ArrayList;
import java.util.List;

public class Gamepad {
    private Main game;
    private Board board;

    private PieceEnum team;

    private OrthographicCamera camera;
    private Vector3 touchPoint;

    private Vector2 capturedPos;//Чтобы можно было вернуть фигуру на место
    private Vector2 lastPos; //Это координаты того где именно сейчас находится взятая фигура

    private TextureRegion capturedTextureRegion;

    private Figure figure;

    private List<Vector2> available, mbButCheckAfter;

    private Castling castling;
    private PawnTransf pawnTransf;
    private PawnInterception pawnInterception;
    private Check check;

    public Gamepad(Main game, OrthographicCamera camera, Board board, PieceEnum team) {
        this.game = game;
        this.camera = camera;
        this.board = board;
        this.team = team;

        touchPoint = new Vector3();
        capturedPos = new Vector2(-1, -1);//В координатах доски [0, 7]
        lastPos = new Vector2(-1, -1);

        available = new ArrayList<Vector2>();
        mbButCheckAfter = new ArrayList<Vector2>();

        //Logic
        castling = board.getCastling();
        pawnTransf = board.getPawnTransformation();
        pawnInterception = board.getPawnInterceptionLogic();
        check = board.getCheck(team);
    }


    public void update(Input in){
        if (in.isTouched()) {
            camera.unproject(touchPoint.set(in.getX(), in.getY(), 0));

            //В координаты доски
            int col = (int) Math.floor((touchPoint.x - game.padding) / game.cellSize);
            int row = (int) Math.floor((touchPoint.y - board.boardLeftY - game.padding) / game.cellSize);

            if (pawnTransf.isTransNow)
                pawnTransf.onTouch(row, col);
            else
                onTouch(row, col);
        }
        else onTouchRelease();
    }

    public void onTouch(int row, int col) {
        lastPos.set(col, row);
        Figure f = board.get(row, col);
        PieceEnum lastPiece = f.piece;
        if(f.team!=team) return; //Ходить только за свою команду
        if (!isCaptured() && lastPiece != PieceEnum.empty && Board.iS_WITHIN_BOARD(row, col))
            capturePiece(lastPiece, lastPos);

    }

    private void onTouchRelease(){
        if(isCaptured()) {
            if(Board.iS_WITHIN_BOARD(lastPos) && figure.canMove(lastPos) && !check.isAfterMoveCheck(capturedPos, lastPos)) {
                board.move(capturedPos, lastPos);
            }
            figure.setVisible(true);
            available.clear();
            capturedPos.set(-1, -1);
        }
    }

    public void capturePiece(PieceEnum lastPiece, Vector2 lastPos){
        //Схватить то что под этими координатами
        capturedPos.set(lastPos);
        capturedTextureRegion = game.assets.getCharactersTextureR(lastPiece);
        figure = board.get(lastPos);
        figure.setVisible(false);
        available.addAll(figure.getAvailableCells());

        mbButCheckAfter.clear();
        mbButCheckAfter = check.remove_moves_after_which_check(available, lastPos);
    }

    public void present(){
        if(isCaptured()){
            game.batcher.draw(capturedTextureRegion, touchPoint.x-game.cellSize/2, touchPoint.y-game.cellSize/2, game.cellSize, game.cellSize);
        }
        if(pawnTransf.isTransNow) {
            pawnTransf.present();
        }
    }

    public void highlight_available(){
        if(isCaptured()) {
            //Подсвечивать места куда можно ходить
            TextureRegion tr = null;
            for (Vector2 vTo : available) {
                tr = game.assets.green;
                if(((figure.piece == PieceEnum.kingB || figure.piece == PieceEnum.kingW) && castling.isCastling(figure, capturedPos, vTo))
                        || (board.get(vTo).piece==PieceEnum.empty && pawnInterception.isPosIsInterception(figure, vTo)))
                    tr = game.assets.blue;
                board.drawCharacter((int) vTo.y, (int) vTo.x, tr);
            }
            for (Vector2 vTo : mbButCheckAfter){
                board.drawCharacter((int) vTo.y, (int) vTo.x, game.assets.red);
            }
        }
    }



    private boolean isCaptured() {
        if (capturedPos.x != -1) return true;
        return false;
    }
}
