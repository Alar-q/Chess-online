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
    Vector3 touchPoint;

    Board board;
    Gamepad gamepad;


    public MainMenuScreen(Main game){
        this.game = game;

        camera = new OrthographicCamera(game.WORLD_WIDTH, game.WORLD_HEIGHT);
        camera.position.set(game.WORLD_WIDTH/2, game.WORLD_HEIGHT/2, 0);
        touchPoint = new Vector3();

        board = new Board(game);
        gamepad = new Gamepad(game, board);
    }
    @Override
    public void render(float deltaTime){
        update(deltaTime);
        present(deltaTime);
    }

    private void update(float deltaTime){
        Input in = Gdx.input;

        if (in.isTouched()) {
            camera.unproject(touchPoint.set(in.getX(), in.getY(), 0));
            gamepad.update(touchPoint);
        }

        if (in.isKeyPressed(Input.Keys.LEFT)) { }
        if (in.isKeyPressed(Input.Keys.RIGHT)) { }
        if (in.isKeyPressed(Input.Keys.UP)) { }
        if (in.isKeyPressed(Input.Keys.DOWN)) { }
    }

    private void present(float deltaTime){
        GL20 gl = Gdx.gl;
        gl.glClearColor(1, 0, 0, 1);
        gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        game.batcher.setProjectionMatrix(camera.combined);

        game.batcher.disableBlending();
        game.batcher.begin();
        game.batcher.draw(game.assets.board, 0, board.boardLeftY, board.boardSize, board.boardSize);
        game.batcher.end();


        game.batcher.enableBlending();
        game.batcher.begin();

        board.present(deltaTime);

        game.batcher.end();
    }
}
