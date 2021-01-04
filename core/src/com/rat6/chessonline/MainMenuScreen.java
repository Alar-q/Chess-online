package com.rat6.chessonline;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class MainMenuScreen extends ScreenAdapter {
    Main game;
    OrthographicCamera camera;

    Board board;
    Gamepad gamepad;

    public MainMenuScreen(Main game){
        this.game = game;

        camera = new OrthographicCamera(game.WORLD_WIDTH, game.WORLD_HEIGHT);
        camera.position.set(game.WORLD_WIDTH/2, game.WORLD_HEIGHT/2, 0);

        board = new Board(game);
        gamepad = new Gamepad(game, camera, board);
    }
    @Override
    public void render(float deltaTime){
        update(deltaTime);
        present(deltaTime);
    }

    private void update(float deltaTime){
        Input in = Gdx.input;
        gamepad.update(in);

    }

    private void present(float deltaTime){
        GL20 gl = Gdx.gl;
        gl.glClearColor(0, 0, 0, 1);
        gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        game.batcher.setProjectionMatrix(camera.combined);

        game.batcher.disableBlending();
        game.batcher.begin();
        game.batcher.draw(game.assets.board, 0, board.boardLeftY, board.boardSize, board.boardSize);
        game.batcher.end();


        game.batcher.enableBlending();
        game.batcher.begin();

        gamepad.highlight();
        board.present();
        gamepad.present();

        game.batcher.end();
    }
}
