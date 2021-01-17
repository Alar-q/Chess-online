package com.rat6.chessonline;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.rat6.chessonline.chessLogic.PieceEnum;

public class MainGameScreen extends ScreenAdapter {
    private Main game;
    private OrthographicCamera camera;

    private Board board;
    private Gamepad gamepadW;
    private Gamepad gamepadB;

    public MainGameScreen(Main game){
        this.game = game;

        camera = new OrthographicCamera(game.WORLD_WIDTH, game.WORLD_HEIGHT);
        camera.position.set(game.WORLD_WIDTH/2f, game.WORLD_HEIGHT/2f, 0);

        board = new Board(game);
        gamepadW = new Gamepad(game, camera, board, PieceEnum.white);
        gamepadB = new Gamepad(game, camera, board, PieceEnum.black);
    }

    @Override
    public void render(float deltaTime){
        update();
        present();
    }

    private void update(){
        Input in = Gdx.input;
        if(board.turn==PieceEnum.white)
            gamepadW.update(in);
        else
            gamepadB.update(in);

    }

    private void present(){
        GL20 gl = Gdx.gl;
        gl.glClearColor(0, 0, 0, 1);
        gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        game.batcher.setProjectionMatrix(camera.combined);

        //Рисуем шахматную доску как не требующее прозрачности картинку
        game.batcher.disableBlending();
        game.batcher.begin();
        game.batcher.draw(game.assets.board, 0, board.boardLeftY, board.boardSize, board.boardSize);
        game.batcher.end();


        game.batcher.enableBlending();
        game.batcher.begin();

        drawGamepad_Highlight();

        board.highlight_check();

        board.present(); //draw figures

        drawGamepad();

        game.batcher.end();
    }

    //То есть по умолчанию играют 2 игрока на одном устройстве
    //Для того чтобы сделать онлайн надо будет за extend-иться от этого класса и просто перезаписать эти методы
    public void drawGamepad(){
        gamepadW.present(); //one captured figure under finger
        gamepadB.present(); //one captured figure under finger
    }

    public void drawGamepad_Highlight(){
        gamepadW.highlight_available();
        gamepadB.highlight_available();
    }
}
