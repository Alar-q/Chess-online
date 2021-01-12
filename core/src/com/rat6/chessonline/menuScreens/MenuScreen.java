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

        camera = new OrthographicCamera(WIDTH, HEIGHT);
        camera.position.set(WIDTH/2, HEIGHT/2, 0);


    }

    @Override
    public void render(float deltaTime){
        update();
        present();
    }

    private void update(){
        Input in = Gdx.input;



    }


    float width = 8, height = 1.5f, distance = 0.25f, startHeight = 2.5f;

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

        for(int i=0; i<5; i++) {
            game.batcher.draw(game.assets.blue, WIDTH - width*2, startHeight + (height * i) + (distance * i), width, height);
        }
        game.batcher.end();
    }

}
