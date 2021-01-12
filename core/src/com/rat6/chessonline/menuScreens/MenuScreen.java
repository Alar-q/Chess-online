package com.rat6.chessonline.menuScreens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.rat6.chessonline.Main;
import com.rat6.chessonline.chessLogic.PieceEnum;

public class MenuScreen extends ScreenAdapter {

    private enum Enum { one_player, two_players, settings, help, exit }

    private final static int WIDTH = 10, HEIGHT = 15;

    private Main game;
    private OrthographicCamera camera;

    public MenuScreen(Main game){
        this.game = game;

        camera = new OrthographicCamera(game.WORLD_WIDTH, game.WORLD_HEIGHT);
        camera.position.set(game.WORLD_WIDTH/2, game.WORLD_HEIGHT/2, 0);


    }

    @Override
    public void render(float deltaTime){
        update();
        present();
    }

    private void update(){
        Input in = Gdx.input;



    }

    private void present(){
        GL20 gl = Gdx.gl;
        gl.glClearColor(0, 0, 0, 1);
        gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        game.batcher.setProjectionMatrix(camera.combined);

        game.batcher.disableBlending();
        game.batcher.begin();

        //game.batcher.draw(game.assets.board, 0, board.boardLeftY, board.boardSize, board.boardSize);

        game.batcher.end();

        game.batcher.enableBlending();
        game.batcher.begin();


        game.batcher.end();
    }
}
