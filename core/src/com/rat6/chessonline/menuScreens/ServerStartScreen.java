package com.rat6.chessonline.menuScreens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.rat6.chessonline.Main;
import com.rat6.chessonline.enternet.simple_client.ClientGameScreen;
import com.rat6.chessonline.enternet.simple_client.SimpleClient;
import com.rat6.chessonline.enternet.simple_server.SimpleServer;
import com.rat6.chessonline.utils.Keyboard;

/**
 * Нужно запустить сервер и вывести на экран IP
 */

public class ServerStartScreen extends ScreenAdapter {

    private Main game;
    private final float MENU_WIDTH = 10, MENU_HEIGHT = 15;
    private OrthographicCamera camera;

    private final float glyphWidth = 0.6f, glyphHeight = 0.6f;

    private SimpleServer server;

    Rectangle windowRec ;

    public ServerStartScreen(Main game){
        this.game = game;
        camera = new OrthographicCamera(MENU_WIDTH, MENU_HEIGHT);
        camera.position.set(MENU_WIDTH / 2f, MENU_HEIGHT / 2f, 0);

        server = new SimpleServer();

        windowRec = new Rectangle(0, 0, MENU_WIDTH, MENU_HEIGHT);
    }

    @Override
    public void render(float deltaTime){
        update();
        present();
    }


    private boolean connected = false;

    private void update() {
        if(server.available){
            server.update();
        }
        else if (server.connected) {
            System.out.println("QQ");
            server.connect();
        }
    }


    private void present(){
        GL20 gl = Gdx.gl;
        gl.glClearColor(1, 1, 1, 1);
        gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        game.batcher.setProjectionMatrix(camera.combined);

        game.batcher.enableBlending();
        game.batcher.begin();

        game.font.drawText(server.getIp(), windowRec, glyphWidth, glyphHeight);

        game.batcher.end();

    }

    @Override
    public void pause(){
        server.release();
    }
    @Override
    public void dispose(){
        server.release();
    }
}
