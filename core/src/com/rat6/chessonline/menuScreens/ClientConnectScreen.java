package com.rat6.chessonline.menuScreens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.rat6.chessonline.Main;
import com.rat6.chessonline.utils.Keyboard;


/**
 * Нужно реализовать легкий ввод с клавиатуры IP адреса
 * и подключение к серверу
 */

public class ClientConnectScreen extends ScreenAdapter {

    private Main game;
    private final int MENU_WIDTH = 10, MENU_HEIGHT = 15;
    private OrthographicCamera camera;
    private Keyboard keyboard;

    public ClientConnectScreen(Main game){
        this.game = game;
        camera = new OrthographicCamera(MENU_WIDTH, MENU_HEIGHT);
        camera.position.set(MENU_WIDTH /2, MENU_HEIGHT /2, 0);
        keyboard = new Keyboard(game, MENU_WIDTH, MENU_HEIGHT);
    }

    @Override
    public void render(float deltaTime){
        update();
        present();
    }


    private void update(){
        keyboard.update(Gdx.input);
    }


    private void present(){
        GL20 gl = Gdx.gl;
        gl.glClearColor(1, 1, 1, 1);
        gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        game.batcher.setProjectionMatrix(camera.combined);

        game.batcher.enableBlending();
        game.batcher.begin();

        keyboard.present();

        game.batcher.end();
    }
}
